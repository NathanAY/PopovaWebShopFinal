package selenium;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(DataProviderRunner.class)
public class SeleniumTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @DataProvider
    public static Object[][] categoryDataProvider() {
        Object[][] data = new Object[][]{
                {true, "New category"},
                {false, "Washes"}
        };
        return data;
    }

    @DataProvider
    public static Object[][] newProductDataProvider() {
        Object[][] data = new Object[][]{
                {true, "Selenium", "New description", "Washes", "3299 $"},
                {false, "", "New description", "Washes", "3299 $"},
                {false, "Selenium", "", "Washes", "3299 $"},
                {false, "Selenium", "New description", "", "3299 $"},
                {false, "Selenium", "New description", "Washes", ""},
                {false, "longlonglonglonglonglonglonglonglong", "New description", "Washes", "3299 $"},
                {false, "name", "longlonglonglonglonglonglonglonglong", "Washes", "3299 $"},
                {false, "name", "New description", "longlonglonglonglonglonglonglonglong", "3299 $"},
        };
        return data;
    }

    @DataProvider
    public static Object[][] loginDataProvider() {
        Object[][] data = new Object[][]{
                {true, "1", "1"},
                {false, "1", ""},
                {false, "", "1"},
                {false, "-123", "-123"},
        };
        return data;
    }

    @DataProvider
    public static Object[][] registrationDataProvider() {
        Object[][] data = new Object[][]{
                {true, "usename", "usermail", "name", "375295525555", "12345678", "12345678"},
                {false, "", "usermail", "name", "375295525555", "12345678", "12345678"},
                {false, "usename", "", "name", "375295525555", "12345678", "12345678"},
                {false, "usename", "usermail", "", "375295525555", "12345678", "12345678"},
                {false, "usename", "usermail", "name", "", "12345678", "12345678"},
                {false, "usename", "usermail", "name", "375295525555", "", "12345678"},
                {false, "usename", "usermail", "name", "375295525555", "12345678", ""},
                {false, "usenameandalotofothersymbolsblablab", "usermail", "name", "375295525555", "12345678", "12345678"},
                {false, "usename", "usenameandalotofothersymbolsblablab", "name", "375295525555", "12345678", "12345678"},
                {false, "usename", "usermail", "usenameandalotofothersymbolsblablab", "375295525555", "12345678", "12345678"},
                {false, "usename", "usermail", "name", "usenameandalotofothersymbolsblablab", "12345678", "12345678"},
                {false, "usename", "usermail", "name", "375295525555", "usenameandalotofothersymbolsblablab", "usenameandalotofothersymbolsblablab"},
                {false, "1", "1", "1", "375295525555", "12345678", "12345679"},
        };
        return data;
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.katalon.com/"; //TODO localhost
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    @UseDataProvider("loginDataProvider")
    public void testLogin(boolean b, String login, String password) throws Exception {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.xpath("//div")).click();
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//div")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log in'])[1]/following::button[1]")).click();
        try {
            if (b) {
                assertThat(driver.findElement(By.xpath("(.//*[normalize-space(text()) " +
                                "and normalize-space(.)='Log out'])[1]/following::h2[1]")).getText(),
                        is("Products"));
            } else {
                assertEquals("Username or password is incorrect.", driver.findElement(
                        By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log in'])[1]/following::span[2]")).getText());
            }
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    @UseDataProvider("registrationDataProvider")
    public void testRegistration(boolean b, String username, String mail, String name, String phone,
                                 String pas, String pasConf) {
        driver.get("http://localhost:8080/registration");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("usermail")).clear();
        driver.findElement(By.id("usermail")).sendKeys(mail);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(pas);
        driver.findElement(By.id("confirmPassword")).clear();
        driver.findElement(By.id("confirmPassword")).sendKeys(pasConf);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create your account'])[1]/following::button[1]")).click();
        if (b) {
            assertEquals("Products", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log out'])[1]/following::h2[1]")).getText());
        } else {
            assertEquals(0, driver.findElements(
                    By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log out'])[1]/" +
                            "following::h2[1]")).size());
        }
    }

    @Test
    public void testAddToBasketAndRemove() throws Exception {
        login();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Id 1404'])[1]/following::a[1]")).click();
        assertEquals("'Dual Power Fan' added to your basket.", closeAlertAndGetItsText());
        driver.findElement(By.linkText("Basket")).click();
        try {
            assertEquals("1404 Dual Power Fan", driver.findElement(By.linkText("1404 Dual Power Fan")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Other'])[1]/following::a[1]")).click();
        assertEquals("Product removed from basket.", closeAlertAndGetItsText());
    }

    @Test
    @UseDataProvider("categoryDataProvider")
    public void testCreateCategory(boolean b, String catagory) throws Exception {
        login();
        driver.findElement(By.linkText("Categories")).click();
        driver.findElement(By.id("exampleInputName2")).click();
        driver.findElement(By.id("exampleInputName2")).clear();
        driver.findElement(By.id("exampleInputName2")).sendKeys(catagory);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Name'])" +
                "[1]/following::button[1]")).click();
        assertEquals("Category created successfully.", closeAlertAndGetItsText());
        waitDriver();
        driver.get("http://localhost:8080/categories");
        try {
            assertEquals(catagory, driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                    "normalize-space(.)='X'])[7]/following::td[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='"
                + catagory + "'])[1]/following::button[1]")).click();
        assertEquals("Category removed successfully.", closeAlertAndGetItsText());
    }

    @Test
    @UseDataProvider("newProductDataProvider")
    public void testCreateNewProduct(boolean b, String name, String description, String category,
                                     String price) throws Exception {
        login();
        driver.get("http://localhost:8080/productList#");
        driver.findElement(By.linkText("New Product")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(description);
        driver.findElement(By.id("categoryName")).clear();
        driver.findElement(By.id("categoryName")).sendKeys(category);
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys(price);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create" +
                " your product'])[1]/following::button[1]")).click();
        driver.get("http://localhost:8080/productList#");
        try {
            if (b) {
                assertEquals(name, driver.findElement(By.linkText(name)).getText());
            } else {
                assertEquals(0, driver.findElements(By.linkText(name)).size());
            }
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @Test
    public void testDeleteProduct() throws Exception {
        login();
        driver.get("http://localhost:8080/productList");
        driver.findElement(By.linkText("Delete Product")).click();
        try {
            assertEquals("Selenium", driver.findElement(By.xpath("(.//*[normalize-space(" +
                    "text()) and normalize-space(.)='X'])[4]/following::td[2]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Washes" +
                "'])[3]/following::td[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Washes'" +
                "])[3]/following::button[1]")).click();
        assertEquals("Product removed successfully.", closeAlertAndGetItsText());
    }

    @Test
    public void testCreateOrder() {
        login();
        driver.get("http://localhost:8080/productList");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Id 1405'])[1]/following::a[1]")).click();
        assertEquals("'Iron Braun TexStyle 7' added to your basket.", closeAlertAndGetItsText());
        driver.findElement(By.linkText("Basket")).click();
        try {
            assertEquals("1405 Iron Braun TexStyle 7", driver.findElement(By.linkText("1405 Iron Braun TexStyle 7")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Rating:'])[1]/following::button[1]")).click();
        assertEquals("Order successfully created.", closeAlertAndGetItsText());
        driver.findElement(By.linkText("Products")).click();
        waitDriver();
        driver.findElement(By.linkText("Orders")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='X'])[5]/following::td[4]")).click();
        try {
            assertEquals("Iron Braun TexStyle 7", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='X'])[5]/following::td[4]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Iron Braun TexStyle 7'])[1]/following::button[1]")).click();
        assertEquals("Order removed successfully.", closeAlertAndGetItsText());
    }


    private void login() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("1");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("1");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log in'])[1]/following::button[1]")).click();
    }

    private void waitDriver() {
        driver.findElements(By.id("No such id")); //Wait few sec
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
