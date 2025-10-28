package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseSeleniumTest {

    @Test
    public void testSuccessfulLogin() {
        printTestInfo("Testing successful login with valid credentials");
        
        // Navigate to login page
        navigateToLoginPage();
        sleep(3); // Đợi 3 giây để xem trang login
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        
        // Nhập từng ký tự để trông như người thật type
        String username = "admin";
        for (char c : username.toCharArray()) {
            usernameField.sendKeys(String.valueOf(c));
            sleep(1); // Đợi 1 giây giữa mỗi ký tự
        }
        printTestInfo("Entered username: admin");
        sleep(2); // Đợi thêm 2 giây để xem kết quả
        
        // Find and fill password field
        WebElement passwordField = driver.findElement(By.name("password"));
        
        // Nhập password từng ký tự
        String password = "123456";
        for (char c : password.toCharArray()) {
            passwordField.sendKeys(String.valueOf(c));
            sleep(1); // Đợi 1 giây giữa mỗi ký tự
        }
        printTestInfo("Entered password: 123456");
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
        printTestResult("Successful Login Test", testPassed);
        
        assertTrue(testPassed, "Login should redirect to home page and show logout button");
    }

    @Test
    public void testFailedLoginWithWrongPassword() {
        printTestInfo("Testing login with wrong password");
        
        // Navigate to login page
        navigateToLoginPage();
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("admin");
        printTestInfo("Entered username: admin");
        
        // Find and fill wrong password
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("wrongpassword");
        printTestInfo("Entered wrong password: wrongpassword");
        
        // Click login button using JavaScript (more reliable)
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        printTestInfo("Clicked login button");
        
        // Wait for error message
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-danger")));
        
        // Check if error message is displayed
        WebElement errorMessage = driver.findElement(By.className("alert-danger"));
        boolean errorDisplayed = errorMessage.isDisplayed();
        
        // Check if we're still on login page
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnLoginPage = currentUrl.contains("/login");
        
        boolean testPassed = errorDisplayed && stillOnLoginPage;
        printTestResult("Failed Login Test", testPassed);
        
        assertTrue(testPassed, "Login should show error message and stay on login page");
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        printTestInfo("Testing login with empty credentials");
        
        // Navigate to login page
        navigateToLoginPage();
        
        // Click login button without filling fields
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        printTestInfo("Clicked login button without entering credentials");
        
        // Check if we're still on login page
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnLoginPage = currentUrl.contains("/login");
        
        boolean testPassed = stillOnLoginPage;
        printTestResult("Empty Credentials Login Test", testPassed);
        
        assertTrue(testPassed, "Should stay on login page with empty credentials");
    }

    @Test
    public void testUserLogin() {
        printTestInfo("Testing login with regular user credentials");
        
        // Navigate to login page
        navigateToLoginPage();
        
        // Find and fill username field
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("user");
        printTestInfo("Entered username: user");
        
        // Find and fill password field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
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
        boolean adminPanelNotVisible = driver.findElements(By.linkText("Quản trị")).size() == 0;
        
        boolean testPassed = logoutButtonExists && adminPanelNotVisible;
        printTestResult("User Login Test", testPassed);
        
        assertTrue(testPassed, "User should be logged in but not see admin panel");
    }

    @Test
    public void testLogout() {
        printTestInfo("Testing logout functionality");
        
        // First login
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("admin");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        printTestInfo("Successfully logged in");
        
        // Click logout button
        WebElement logoutButton = driver.findElement(By.linkText("Đăng xuất"));
        logoutButton.click();
        printTestInfo("Clicked logout button");
        
        // Wait for redirect to login page
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Check if we're on login page
        String currentUrl = driver.getCurrentUrl();
        boolean onLoginPage = currentUrl.contains("/login");
        
        // Check if login button is visible
        boolean loginButtonVisible = driver.findElements(By.linkText("Đăng nhập")).size() > 0;
        
        boolean testPassed = onLoginPage && loginButtonVisible;
        printTestResult("Logout Test", testPassed);
        
        assertTrue(testPassed, "Should be redirected to login page after logout");
    }
}
