package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ✅ Kiểm thử chức năng ĐĂNG KÝ NGƯỜI DÙNG
 * Gồm:
 * - Đăng ký thành công với thông tin hợp lệ
 * - Đăng ký với username đã tồn tại
 * - Đăng ký với password quá ngắn
 * - Đăng ký với password không khớp
 * - Đăng ký khi bỏ trống các trường bắt buộc
 */
public class RegisterTest extends BaseSeleniumTest {

    /** 🔹 Hàm gõ chậm như người thật */
    private void typeSlowly(WebElement element, String text) {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            sleep(1); // 1 giây giữa mỗi ký tự
        }
    }

    /** 🧩 Đăng ký thành công với thông tin hợp lệ */
    @Test
    public void testSuccessfulRegistration() {
        printTestInfo("🔹 Testing successful user registration");

        navigateToRegisterPage();
        sleep(2); // xem trang

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        String uniqueUsername = "user_" + System.currentTimeMillis();

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement confirm = driver.findElement(By.id("confirmPassword"));

        typeSlowly(username, uniqueUsername);
        sleep(1);
        typeSlowly(password, "123456");
        sleep(1);
        typeSlowly(confirm, "123456");

        printTestInfo("Filled all fields, submitting...");
        sleep(1);

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        printTestInfo("Clicked submit button");
        sleep(3);

        wait.until(ExpectedConditions.urlContains("/login"));
        sleep(2);

        boolean onLogin = driver.getCurrentUrl().contains("/login");
        boolean msg = driver.getPageSource().toLowerCase().contains("thành công");

        printTestResult("✅ Successful Registration Test", onLogin);
        assertTrue(onLogin, "Should redirect to login after successful registration");
    }

    /** 🧩 Username đã tồn tại */
    @Test
    public void testRegistrationWithExistingUsername() {
        printTestInfo("🔹 Testing registration with existing username");

        navigateToRegisterPage();
        sleep(2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        typeSlowly(driver.findElement(By.id("username")), "admin");
        typeSlowly(driver.findElement(By.id("password")), "123456");
        typeSlowly(driver.findElement(By.id("confirmPassword")), "123456");

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        printTestInfo("Clicked submit for existing username");
        sleep(3);

        boolean stillOnRegister = driver.getCurrentUrl().contains("/register");
        boolean errorShown = driver.getPageSource().toLowerCase().contains("tồn tại") ||
                driver.findElements(By.className("alert-danger")).size() > 0;

        printTestResult("⚠️ Existing Username Test", stillOnRegister && errorShown);
        assertTrue(stillOnRegister && errorShown,
                "Should stay on register page and show username exists error");
    }

    /** 🧩 Mật khẩu quá ngắn */
    @Test
    public void testRegistrationWithShortPassword() {
        printTestInfo("🔹 Testing registration with short password");

        navigateToRegisterPage();
        sleep(2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        String username = "short_" + System.currentTimeMillis();
        typeSlowly(driver.findElement(By.id("username")), username);
        typeSlowly(driver.findElement(By.id("password")), "123");
        typeSlowly(driver.findElement(By.id("confirmPassword")), "123");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        printTestInfo("Clicked submit with short password");
        sleep(2);

        boolean stillOnRegister = driver.getCurrentUrl().contains("/register");
        printTestResult("⚠️ Short Password Test", stillOnRegister);
        assertTrue(stillOnRegister, "Should remain on register page for short password");
    }

    /** 🧩 Mật khẩu không khớp */
    @Test
    public void testRegistrationWithUnmatchedPasswords() {
        printTestInfo("🔹 Testing registration with unmatched passwords");

        navigateToRegisterPage();
        sleep(2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        String username = "nomatch_" + System.currentTimeMillis();
        typeSlowly(driver.findElement(By.id("username")), username);
        typeSlowly(driver.findElement(By.id("password")), "123456");
        typeSlowly(driver.findElement(By.id("confirmPassword")), "654321");

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        printTestInfo("Clicked submit with mismatched passwords");
        sleep(3);

        boolean stillOnRegister = driver.getCurrentUrl().contains("/register");
        boolean errorShown = driver.getPageSource().toLowerCase().contains("khớp")
                || driver.findElements(By.className("alert-danger")).size() > 0;

        printTestResult("⚠️ Unmatched Password Test", stillOnRegister && errorShown);
        assertTrue(stillOnRegister && errorShown, "Should stay on register page and show mismatch error");
    }

    /** 🧩 Bỏ trống các trường */
    @Test
    public void testRegistrationWithEmptyFields() {
        printTestInfo("🔹 Testing registration with empty fields");

        navigateToRegisterPage();
        sleep(2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form")));

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        printTestInfo("Clicked submit without filling fields");
        sleep(2);

        boolean stillOnRegister = driver.getCurrentUrl().contains("/register");
        printTestResult("⚠️ Empty Fields Test", stillOnRegister);
        assertTrue(stillOnRegister, "Should stay on register page when fields are empty");
    }
}
