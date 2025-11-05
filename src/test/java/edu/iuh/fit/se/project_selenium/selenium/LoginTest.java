package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class cho chức năng đăng nhập người dùng
 * 
 * Chỉ chạy 4 trường hợp:
 * 1. Admin PASS (admin/123456)
 * 2. Admin FAIL (admin/wrongpassword)
 * 3. User  PASS (user/123456)
 * 4. User  FAIL (user/wrongpassword)
 */
public class LoginTest extends BaseSeleniumTest {

    @Test
    public void testSuccessfulLogin() {
        printTestInfo("Testing login: ĐÚNG username - ĐÚNG password");
        
        // Navigate to login page
        navigateToLoginPage();
        showTestBanner("Testing login with: admin / 123456");
        sleep(3); // Đợi 3 giây để xem trang login
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        // Gõ chậm để GV xem
        typeSlowly(usernameField, "admin", 700);
        printTestInfo("✅ Entered CORRECT username: admin");
        sleep(2); // Đợi thêm 2 giây để xem kết quả
        
        // Find and fill password field
        WebElement passwordField = driver.findElement(By.name("password"));
        
        // Gõ password chậm
        typeSlowly(passwordField, "123456", 700);
        printTestInfo("✅ Entered CORRECT password: 123456");
        sleep(2); // Đợi thêm 2 giây
        
        // Click login button using JavaScript (more reliable)
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        sleep(1); // Đợi trước khi click
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        printTestInfo("Clicked login button");
        sleep(3); // Đợi 3 giây để xem redirect
        
        // Wait for redirect and check if we're on home page
        wait.until(ExpectedConditions.urlContains("/"));
        
        // Give page time to fully load
        sleep(2);
        
        // In ra thông báo để demo
        System.out.println("✨ LOGIN THÀNH CÔNG! Đang hiển thị kết quả...");
        
        // Verify we're on the home page
        String currentUrl = driver.getCurrentUrl();
        boolean isOnHomePage = currentUrl.equals(baseUrl + "/") || currentUrl.equals(baseUrl);
        printTestInfo("Current URL: " + currentUrl);
        
        // Check if user is logged in by looking for logout button or any navigation element
        boolean logoutButtonExists = driver.findElements(By.xpath("//a[contains(text(), 'Đăng xuất')]")).size() > 0 ||
                                    driver.findElements(By.linkText("Đăng xuất")).size() > 0;
        
        // Also check for authenticated content
        boolean isLoggedIn = driver.getPageSource().contains("Xin chào") || logoutButtonExists;
        
        printTestInfo("Logout button found: " + logoutButtonExists);
        printTestInfo("Is logged in: " + isLoggedIn);
        
        boolean testPassed = isLoggedIn && (isOnHomePage || currentUrl.contains(baseUrl));
        printTestResult("Test: Đúng username - Đúng password", testPassed);
        
        assertTrue(testPassed, "Login should redirect to home page and show logout button");
    }

    @Test
    public void testFailedLoginWithWrongPassword() {
        printTestInfo("Testing login: ĐÚNG username - SAI password");
        
        // Navigate to login page
        navigateToLoginPage();
        showTestBanner("Testing login with: admin / wrongpassword");
        sleep(2);
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        typeSlowly(usernameField, "admin", 700);
        printTestInfo("✅ Entered CORRECT username: admin");
        sleep(1);
        
        // Find and fill wrong password
        WebElement passwordField = driver.findElement(By.name("password"));
        typeSlowly(passwordField, "wrongpassword", 700);
        printTestInfo("❌ Entered WRONG password: wrongpassword");
        sleep(1);
        
        // Click login button using JavaScript (more reliable)
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        printTestInfo("Clicked login button");
        sleep(3);
        
        // Wait for error message
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-danger")));
        
        // Check if error message is displayed
        WebElement errorMessage = driver.findElement(By.className("alert-danger"));
        boolean errorDisplayed = errorMessage.isDisplayed();
        
        // Check if we're still on login page
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnLoginPage = currentUrl.contains("/login");
        
        boolean testPassed = errorDisplayed && stillOnLoginPage;
        printTestResult("Test: Đúng username - Sai password", testPassed);
        
        assertTrue(testPassed, "Login should show error message and stay on login page");
    }

    // (Bỏ các case khác để chỉ còn đúng 4 case theo yêu cầu)

    @Test
    public void testUserLoginFailWrongPassword() {
        printTestInfo("Testing user login: FAIL (wrong password)");
        navigateToLoginPage();
        showTestBanner("Testing login with: user / wrongpassword");
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        typeSlowly(usernameField, "user", 700);
        WebElement passwordField = driver.findElement(By.name("password"));
        typeSlowly(passwordField, "wrongpassword", 700);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-danger")));
        boolean stillOnLoginPage = driver.getCurrentUrl().contains("/login");
        boolean testPassed = errorMessage.isDisplayed() && stillOnLoginPage;
        printTestResult("User Login FAIL", testPassed);
        assertTrue(testPassed, "User wrong password should show error and stay on login page");
    }

    @Test
    public void testUserLogin() {
        printTestInfo("Testing login with regular user credentials");
        
        // Navigate to login page
        navigateToLoginPage();
        showTestBanner("Testing login with: user / 123456");
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("user");
        printTestInfo("Entered username: user");
        
        // Find and fill password field
        WebElement passwordField = driver.findElement(By.name("password"));
        typeSlowly(passwordField, "123456", 700);
        printTestInfo("Entered password: 123456");
        
        // Click login button using JavaScript (more reliable)
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        printTestInfo("Clicked login button");
        
        // Wait for redirect
        wait.until(ExpectedConditions.urlContains("/"));
        
        // Check if user is logged in
        boolean logoutButtonExists = driver.findElements(By.linkText("Đăng xuất")).size() > 0;
        
        // Check if admin panel is NOT visible (user doesn't have admin role)
        boolean adminPanelNotVisible = driver.findElements(By.cssSelector("a[href*='/admin']")).size() == 0;
        
        boolean testPassed = logoutButtonExists && adminPanelNotVisible;
        printTestResult("User Login Test", testPassed);
        
        assertTrue(testPassed, "User should be logged in but not see admin panel");
    }

    // Bỏ test logout để tập trung 4 case login
}
