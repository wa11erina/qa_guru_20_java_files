package guru.qa;

import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.model.GlossaryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WorkingWithFilesTest {

    ClassLoader cl = FileParsingTest.class.getClassLoader();
    Gson gson = new Gson();

    @Test
    void zipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("test_archive.7z");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entryPdf;
            while ((entryPdf = zis.getNextEntry()) != null) {
                final String name = entryPdf.getName();
                Assertions.assertTrue(name.contains("All_Theory.pdf"));
            }

            ZipEntry entryXlsx;
            while ((entryXlsx = zis.getNextEntry()) != null) {
                final String name = entryXlsx.getName();
                Assertions.assertTrue(name.contains("all_pairs.xlsx"));

                XLS xls = new XLS(zis);

                Assertions.assertEquals("Linux",
                        xls.excel.getSheetAt(0).
                                getRow(3)
                                .getCell(4)
                                .getStringCellValue());
            }

            ZipEntry entryCsv;
            while ((entryCsv = zis.getNextEntry()) != null) {
                final String name = entryCsv.getName();
                Assertions.assertTrue(name.contains("countries.csv"));

                Reader reader = new InputStreamReader(zis);
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> content = csvReader.readAll();

                Assertions.assertEquals(4, content.size());

                final String[] firstRow = content.get(0);
                final String[] secondRow = content.get(1);
                final String[] thirdRow = content.get(2);
                final String[] fourthRow = content.get(3);

                Assertions.assertArrayEquals(new String[] {"Country", "Capital", "Language"}, firstRow);
                Assertions.assertArrayEquals(new String[] {"Russia", "Moscow", "Russian"}, secondRow);
                Assertions.assertArrayEquals(new String[] {"Ukraine", "Kiev", "Ukrainian"}, thirdRow);
                Assertions.assertArrayEquals(new String[] {"Belarus", "Minsk", "Belarusian"}, fourthRow);
            }
        }

    }
}

