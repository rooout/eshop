package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SeleniumJupiter.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct(ChromeDriver driver) {
        // 1. Buka halaman daftar produk
        driver.get(baseUrl + "/product/list");

        // 2. Klik tombol "Create Product"
        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        createButton.click();

        // 3. Isi formulir
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        String testProductName = "Test Product";
        String testProductQuantity = "10";

        nameInput.sendKeys(testProductName);
        quantityInput.sendKeys(testProductQuantity);
        submitButton.click();

        // 4. Verifikasi bahwa produk baru muncul di daftar
        driver.get(baseUrl + "/product/list");
        WebElement productTable = driver.findElement(By.tagName("table"));
        assertTrue(productTable.getText().contains(testProductName));
    }
}
