package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class WorkingWithFilesTest {

    ClassLoader cl = WorkingWithFilesTest.class.getClassLoader();


    String archiveName = "test_archive.zip";


    private ZipInputStream getStreamFromArchive(String archiveName, String filename) throws Exception {

        // Open streams
        ZipEntry entry;
        ZipInputStream zis;
        InputStream is = cl.getResourceAsStream(archiveName);
        zis = new ZipInputStream(is);

        // Search for the file
        while ((entry = zis.getNextEntry()) != null) {

            // If file is found, file stream returns
            if (entry.getName().endsWith(filename)) return zis;

        }
        // If nothing suitable is found, stream closes and new Exception appears
        is.close();
        zis.close();
        throw new InvalidArgumentException("ERROR: File " + filename + " was not found in the archive" + archiveName + "\n");
    }


    @Test
    void zipStreamPdfTest() throws Exception {


        InputStream inputStream = getStreamFromArchive(archiveName, "All_Theory.pdf");

        PDF pdf = new PDF(inputStream);

        System.out.println(pdf.numberOfPages);

        Assertions.assertEquals(
                113,
                pdf.numberOfPages
        );

        inputStream.close();
    }


    @Test
    void zipStreamXlsTest() throws Exception {

        InputStream inputStream = getStreamFromArchive(archiveName, "teachers.xls");

        XLS xls = new XLS(inputStream);

        String value = xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue();


        Assertions.assertEquals("1. Суммарное количество часов планируемое на штатную по всем разделам плана  должно \n" +
                        "составлять примерно 1500 час в год.  ",
                xls.excel.getSheetAt(0).
                        getRow(3)
                        .getCell(2)
                        .getStringCellValue());

        System.out.println(value);

        inputStream.close();
    }


    @Test
    void zipStreamCSVTest() throws Exception {

        InputStream inputStream = getStreamFromArchive(archiveName, "countries.csv");
        InputStreamReader isr = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(isr);
        List<String[]> content = csvReader.readAll();

        Assertions.assertEquals(4, content.size());

        final String[] firstRow = content.get(0);
        final String[] secondRow = content.get(1);
        final String[] thirdRow = content.get(2);
        final String[] fourthRow = content.get(3);

        Assertions.assertArrayEquals(new String[]{"Country", "Capital", "Language"}, firstRow);
        Assertions.assertArrayEquals(new String[]{"Russia", "Moscow", "Russian"}, secondRow);
        Assertions.assertArrayEquals(new String[]{"Ukraine", "Kiev", "Ukrainian"}, thirdRow);
        Assertions.assertArrayEquals(new String[]{"Belarus", "Minsk", "Belarusian"}, fourthRow);

        System.out.println(content.get(1)[0] + " " + "is" + " " + "the best country in the world");

        inputStream.close();
    }


}