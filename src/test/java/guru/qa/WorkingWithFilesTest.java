package guru.qa;


import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorkingWithFilesTest {
    @Test
    void zipTest() throws Exception {

        try (ZipFile zf = new ZipFile(new File("src/test/resources/test_archive.7z"))) {

            ZipEntry entryXlsx = zf.getEntry("all_pairs.xlsx");
            assertNotNull(entryXlsx);
            try (InputStream inputStream = zf.getInputStream(entryXlsx)) {
                XLS xls = new XLS(inputStream);

                Assertions.assertEquals("Linux",
                        xls.excel.getSheetAt(0).
                                getRow(3)
                                .getCell(4)
                                .getStringCellValue());
            }

        }
    }
}
