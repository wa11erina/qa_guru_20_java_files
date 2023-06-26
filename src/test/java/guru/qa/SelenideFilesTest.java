package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFilesTest {

    @Test
    void downloadTxtFileTest() throws IOException {

        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloaded = $("#raw-url").download();
        try (InputStream is = new FileInputStream(downloaded)) {
            byte[] bytes = is.readAllBytes();
            String content = new String(bytes, StandardCharsets.UTF_8);
            Assertions.assertTrue(content.contains("This repository is the home of _JUnit 5_."));
        }
    }

    @Test
    void uploadFileTest() throws Exception {

        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("the_light_road.jpg");
        $(".qq-file-name").shouldHave(text("the_light_road.jpg"));

    }

    @Test
    void downloadPdfFileTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloaded = $("a[href*='junit-user-guide-5.9.3.pdf']").download();
        PDF pdf = new PDF(downloaded);
        Assertions.assertEquals("JUnit 5 User Guide", pdf.title);
    }

    @Test
    void downloadXlsFileTest() throws Exception {
        open("https://excelvba.ru/programmes/Teachers?ysclid=lfcu77j9j9951587711");
        File downloaded = $("a[href*='teachers.xls']").download();
        XLS xls = new XLS(downloaded);

        Assertions.assertEquals("1. Общие положения",
                xls.excel.getSheetAt(0).
                        getRow(1)
                        .getCell(4)
                        .getStringCellValue());
    }
}
