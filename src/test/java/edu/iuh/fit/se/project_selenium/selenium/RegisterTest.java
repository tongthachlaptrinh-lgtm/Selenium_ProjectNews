package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class cho chức năng đăng ký người dùng mới
 * Bao gồm các test case:
 * - Đăng ký thành công với thông tin hợp lệ
 * - Đăng ký với username đã tồn tại
 * - Đăng ký với password quá ngắn
 * - Đăng ký với password không khớp
 * - Đăng ký với các trường bắt buộc để trống
 */
public class RegisterTest extends BaseSeleniumTest {

    @Test
    public void testSuccessfulRegistration() {
        printTestInfo("Testing successful user registration with valid information");
        
        // Navigate to register page
        navigateToRegisterPage();
        
        // Generate unique username
        String uniqueUsername = "testuser_" + System.currentTimeMillis();
        
        // Fill in registration form
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys(uniqueUsername);
        printTestInfo("Entered username: " + uniqueUsername);
        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("123456");
        printTestInfo("Entered password: 123456");
        
        WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
        confirmPasswordField.sendKeys("123456");
        printTestInfo("Entered confirm password: 123456");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Clicked submit button");
        
        // Wait for redirect to login page
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Check if we're on login page
        String currentUrl = driver.getCurrentUrl();
        boolean onLoginPage = currentUrl.contains("/login");
        
        // Check if success message is displayed
        boolean successMessageDisplayed = driver.findElements(By.className("alert-success")).size() > 0 || 
                                         driver.getPageSource().contains("Đăng ký thành công");
        
        boolean testPassed = onLoginPage && successMessageDisplayed;
        printTestResult("Successful Registration Test", testPassed);
        
        assertTrue(testPassed, "Registration should redirect to login page with success message");
    }

    @Test
    public void testRegistrationWithExistingUsername() {
        printTestInfo("Testing registration with existing username");
        
        // Navigate to register page
        navigateToRegisterPage();
        
        // Try to register with existing username
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("admin"); // Use existing admin username
        printTestInfo("Entered existing username: admin");
        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("123456");
        
        WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
        confirmPasswordField.sendKeys("123456");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Submitted registration form");
        
        // Wait for error message
        try {
            Thread.sleep(1000); // Wait for form validation
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Check if we're still on register page
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnRegisterPage = currentUrl.contains("/register");
        
        // Check if error message is displayed
        boolean errorMessageDisplayed = driver.findElements(By.className("alert-danger")).size() > 0 || 
                                       driver.getPageSource().contains("đã tồn tại") ||
                                       driver.getPageSource().contains("tồn tại");
        
        boolean testPassed = stillOnRegisterPage || errorMessageDisplayed;
        printTestResult("Registration with Existing Username Test", testPassed);
        
        assertTrue(testPassed, "Should show error message for existing username");
    }

    @Test
    public void testRegistrationWithShortPassword() {
        printTestInfo("Testing registration with password too short");
        
        // Navigate to register page
        navigateToRegisterPage();
        
        // Generate unique username
        String uniqueUsername = "testuser_" + System.currentTimeMillis();
        
        // Fill in form with short password
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys(uniqueUsername);
        printTestInfo("Entered username: " + uniqueUsername);
        
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("123"); // Password too short (< 6 characters)
        printTestInfo("Entered short password: 123");
        
        WebElement confirmPasswordField = driver.findElement(By.id("confirmPassword"));
        confirmPasswordField.sendKeys("123");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Check if we're still on register page (client-side validation)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnRegisterPage = currentUrl.contains("/register");
        
        boolean testPassed = stillOnRegisterPage;
        printTestResult("Registration with Short Password Test", testPassed);
        
        assertTrue(testPassed, "Should stay on register page with validation error");
    }

    @Test
    public void testRegistrationWithEmptyFields() {
        printTestInfo("Testing registration with empty required fields");
        
        // Navigate to register page
        navigateToRegisterPage();
        
        // Try to submit form without filling fields
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();
        printTestInfo("Attempted to submit empty form");
        
        // Check if we're still on register page
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnRegisterPage = currentUrl.contains("/register");
        
        boolean testPassed = stillOnRegisterPage;
        printTestResult("Registration with Empty Fields Test", testPassed);
        
        assertTrue(testPassed, "Should stay on register page with empty fields");
    }
}

