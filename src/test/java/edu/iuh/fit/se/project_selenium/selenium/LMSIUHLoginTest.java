package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LMS IUH Login Page
 * Tests the login functionality at https://lms.iuh.edu.vn/login/index.php
 */
public class LMSIUHLoginTest extends BaseSeleniumTest {
    
    private final String lmsLoginUrl = "https://lms.iuh.edu.vn/login/index.php";
    
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        // Override baseUrl for LMS IUH portal
        baseUrl = "https://lms.iuh.edu.vn";
    }

    @Test
    public void testLMSSuccessfulLogin() {
        printTestInfo("🧪 Testing LMS IUH successful login with valid credentials");
        printTestInfo("👤 Username: 22643731");
        printTestInfo("🔑 Password: tt11qq22");
        
        // Navigate to LMS login page
        driver.get(lmsLoginUrl);
        sleep(2);
        
        // Find username field - Moodle LMS typically uses 'username' field
        WebElement usernameField = findUsernameField();
        if (usernameField == null) {
            try {
                // Try common Moodle selectors
                usernameField = driver.findElement(By.xpath("//input[@type='text' and (@name='username' or @id='username' or contains(@id, 'user'))] | //input[@name='username'] | //input[@id='username']"));
            } catch (Exception e) {
                printTestInfo("❌ Could not find username field: " + e.getMessage());
                fail("Username field not found");
            }
        }
        
        // Nhập username nhanh
        String username = "22643731";
        usernameField.clear();
        usernameField.sendKeys(username);
        printTestInfo("✅ Entered username: 22643731");
        
        // Find password field
        WebElement passwordField = findPasswordField();
        if (passwordField == null) {
            try {
                passwordField = driver.findElement(By.cssSelector("input[type='password']"));
            } catch (Exception e) {
                printTestInfo("❌ Could not find password field: " + e.getMessage());
                fail("Password field not found");
            }
        }
        
        // Nhập password nhanh
        String password = "tt11qq22";
        passwordField.clear();
        passwordField.sendKeys(password);
        printTestInfo("✅ Entered password: tt11qq22");
        
        // Trang LMS không có captcha, click login button ngay
        printTestInfo("🖱️  Đang tìm và nhấn nút login...");
        WebElement loginButton = findLoginButton();
        if (loginButton == null) {
            try {
                // Try Moodle-specific login button selectors
                loginButton = driver.findElement(By.xpath("//button[@type='submit'] | //input[@type='submit'] | //button[contains(@id, 'loginbtn')] | //button[contains(@id, 'login')] | //button[contains(text(), 'Đăng nhập')] | //button[contains(text(), 'Login')]"));
            } catch (Exception e) {
                printTestInfo("⚠️  Could not find login button, trying form submit");
                try {
                    WebElement form = driver.findElement(By.tagName("form"));
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].submit();", form);
                    printTestInfo("✅ Submitted form directly");
                } catch (Exception ex) {
                    fail("Login button not found and form submit failed: " + ex.getMessage());
                }
            }
        }
        
        // Click login button if found
        if (loginButton != null) {
            try {
                printTestInfo("🖱️  Clicking login button...");
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
                printTestInfo("✅ Clicked login button");
            } catch (Exception e) {
                printTestInfo("⚠️  Error clicking button: " + e.getMessage());
            }
        }
        
        sleep(3); // Đợi redirect
        
        // Check if login was successful
        String currentUrl = driver.getCurrentUrl();
        printTestInfo("🌐 Current URL after login: " + currentUrl);
        printTestInfo("📄 Page title: " + driver.getTitle());
        
        // Check if we're redirected away from login page
        boolean notOnLoginPage = !currentUrl.contains("/login") && !currentUrl.contains("/Login");
        
        // Check for error messages (should not be present for successful login)
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasError = pageSource.contains("sai") || 
                          pageSource.contains("lỗi") ||
                          pageSource.contains("error") ||
                          pageSource.contains("invalid") ||
                          pageSource.contains("thất bại");
        
        boolean testPassed = notOnLoginPage && !hasError;
        printTestResult("LMS Successful Login Test", testPassed);
        
        if (testPassed) {
            printTestInfo("✨ LOGIN THÀNH CÔNG! Đang hiển thị kết quả...");
        } else {
            printTestInfo("⚠️  Login có thể đã thất bại hoặc đang chờ xử lý");
        }
        
        // Keep browser open to see result
        sleep(5);
        
        assertTrue(testPassed, "Login should be successful and redirect away from login page");
    }

    @Test
    public void testLMSFailedLogin() {
        printTestInfo("🧪 Testing LMS IUH failed login with wrong password");
        printTestInfo("👤 Username: 22643731");
        printTestInfo("🔑 Password: wrongpass123 (wrong password)");
        
        // Navigate to LMS login page
        driver.get(lmsLoginUrl);
        sleep(2);
        
        // Find username field
        WebElement usernameField = findUsernameField();
        if (usernameField == null) {
            try {
                usernameField = driver.findElement(By.xpath("//input[@type='text' and (@name='username' or @id='username' or contains(@id, 'user'))] | //input[@name='username'] | //input[@id='username']"));
            } catch (Exception e) {
                printTestInfo("❌ Could not find username field: " + e.getMessage());
                fail("Username field not found");
            }
        }
        
        // Nhập username nhanh
        String username = "22643731";
        usernameField.clear();
        usernameField.sendKeys(username);
        printTestInfo("✅ Entered username: 22643731");
        
        // Find password field
        WebElement passwordField = findPasswordField();
        if (passwordField == null) {
            try {
                passwordField = driver.findElement(By.cssSelector("input[type='password']"));
            } catch (Exception e) {
                printTestInfo("❌ Could not find password field: " + e.getMessage());
                fail("Password field not found");
            }
        }
        
        // Nhập password sai nhanh
        String password = "wrongpass123";
        passwordField.clear();
        passwordField.sendKeys(password);
        printTestInfo("✅ Entered wrong password: wrongpass123");
        
        // Trang LMS không có captcha, click login button ngay
        printTestInfo("🖱️  Đang tìm và nhấn nút login...");
        WebElement loginButton = findLoginButton();
        if (loginButton == null) {
            try {
                loginButton = driver.findElement(By.xpath("//button[@type='submit'] | //input[@type='submit'] | //button[contains(@id, 'loginbtn')] | //button[contains(@id, 'login')] | //button[contains(text(), 'Đăng nhập')] | //button[contains(text(), 'Login')]"));
            } catch (Exception e) {
                try {
                    WebElement form = driver.findElement(By.tagName("form"));
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].submit();", form);
                    printTestInfo("✅ Submitted form directly");
                } catch (Exception ex) {
                    fail("Login button not found: " + ex.getMessage());
                }
            }
        }
        
        // Click login button if found
        if (loginButton != null) {
            try {
                printTestInfo("🖱️  Clicking login button...");
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
                printTestInfo("✅ Clicked login button");
            } catch (Exception e) {
                printTestInfo("⚠️  Error clicking button: " + e.getMessage());
            }
        }
        
        sleep(3); // Đợi xem kết quả
        
        // Check if error message is displayed
        String currentUrl = driver.getCurrentUrl();
        printTestInfo("🌐 Current URL after login attempt: " + currentUrl);
        
        // Check if still on login page
        boolean stillOnLoginPage = currentUrl.contains("/login") || currentUrl.contains("/Login");
        
        // Check for error messages
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasError = pageSource.contains("sai") || 
                          pageSource.contains("lỗi") ||
                          pageSource.contains("error") ||
                          pageSource.contains("invalid") ||
                          pageSource.contains("thất bại") ||
                          pageSource.contains("không đúng") ||
                          pageSource.contains("mật khẩu") ||
                          pageSource.contains("username or password");
        
        // Also check for visible error elements (Moodle-specific)
        boolean hasErrorElement = false;
        try {
            hasErrorElement = driver.findElements(By.className("error")).size() > 0 ||
                            driver.findElements(By.className("alert-danger")).size() > 0 ||
                            driver.findElements(By.className("text-danger")).size() > 0 ||
                            driver.findElements(By.className("alert")).size() > 0 ||
                            driver.findElements(By.xpath("//*[contains(@class, 'alert') or contains(@class, 'error')]")).size() > 0 ||
                            driver.findElements(By.xpath("//*[contains(text(), 'sai') or contains(text(), 'lỗi') or contains(text(), 'error')]")).size() > 0;
        } catch (Exception e) {
            // Continue
        }
        
        boolean testPassed = stillOnLoginPage || hasError || hasErrorElement;
        printTestResult("LMS Failed Login Test", testPassed);
        
        if (testPassed) {
            printTestInfo("✅ Test passed: Login correctly rejected with wrong password");
        } else {
            printTestInfo("⚠️  Could not verify login failure");
        }
        
        // Keep browser open to see result
        sleep(5);
        
        assertTrue(testPassed, "Login should fail and show error or stay on login page");
    }

    /**
     * Helper method to try finding username field with various selectors
     */
    private WebElement findUsernameField() {
        String[] selectors = {
            "input[name='username']",
            "input[id='username']",
            "input[name='Username']",
            "input[id='Username']",
            "input[name='userName']",
            "input[name='UserName']",
            "input[id*='username']",
            "input[id*='user']",
            "input[type='text']",
            "input[type='email']"
        };
        
        for (String selector : selectors) {
            try {
                WebElement element = driver.findElement(By.cssSelector(selector));
                if (element != null && element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                // Continue
            }
        }
        return null;
    }

    /**
     * Helper method to try finding password field with various selectors
     */
    private WebElement findPasswordField() {
        try {
            return driver.findElement(By.cssSelector("input[type='password']"));
        } catch (Exception e) {
            try {
                return driver.findElement(By.cssSelector("input[name*='password']"));
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Helper method to try finding login button with various selectors
     */
    private WebElement findLoginButton() {
        String[] selectors = {
            "button[type='submit']",
            "input[type='submit']",
            "button#loginbtn",
            "button[id*='loginbtn']",
            "button[id*='login']",
            "button.btn-primary",
            "button.btn"
        };
        
        for (String selector : selectors) {
            try {
                WebElement element = driver.findElement(By.cssSelector(selector));
                if (element != null && element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                // Continue
            }
        }
        
        // Try XPath for buttons with text
        try {
            return driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')] | //button[contains(text(), 'Login')] | //button[contains(@id, 'login')]"));
        } catch (Exception e) {
            return null;
        }
    }
}

