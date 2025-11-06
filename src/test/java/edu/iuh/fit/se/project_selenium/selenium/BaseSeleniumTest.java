package edu.iuh.fit.se.project_selenium.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseSeleniumTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "http://localhost:8081";

    @BeforeEach
    public void setUp() {
        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // TẮT headless để xem browser tự động chạy! 🎉
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        
        // Initialize driver
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Maximize window
        driver.manage().window().maximize();
        
        System.out.println("✅ Selenium WebDriver initialized successfully");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(10000); // Đợi 10 giây trước khi đóng browser để DEMO cho giảng viên! 🎓
                System.out.println("⏱️  Browser sẽ đóng sau 10 giây...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("✅ WebDriver closed successfully");
        }
    }

    protected void navigateToHomePage() {
        driver.get(baseUrl);
        System.out.println("🌐 Navigated to homepage: " + baseUrl);
    }

    protected void navigateToLoginPage() {
        driver.get(baseUrl + "/login");
        System.out.println("🌐 Navigated to login page: " + baseUrl + "/login");
    }

    protected void navigateToRegisterPage() {
        driver.get(baseUrl + "/register");
        System.out.println("🌐 Navigated to register page: " + baseUrl + "/register");
    }

    protected void navigateToAdminDashboard() {
        driver.get(baseUrl + "/admin/dashboard");
        System.out.println("🌐 Navigated to admin dashboard: " + baseUrl + "/admin/dashboard");
    }

    protected void printTestResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("✅ " + testName + " - PASSED");
        } else {
            System.out.println("❌ " + testName + " - FAILED");
        }
    }

    protected void printTestInfo(String info) {
        System.out.println("ℹ️  " + info);
    }

    /**
     * Helper method to login with credentials
     */
    protected void login(String username, String password) {
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        printTestInfo("Logged in as: " + username);
    }

    /**
     * Helper method to login as admin
     */
    protected void loginAsAdmin() {
        login("admin", "123456");
    }

    /**
     * Helper method to login as regular user
     */
    protected void loginAsUser() {
        login("user", "123456");
    }

    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        try {
            Thread.sleep(2000); // Tăng từ 1s lên 2s để dễ nhìn hơn
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Wait for a moment (for demo purposes)
     */
    protected void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Safe click method with scroll and JavaScript click to avoid ElementClickInterceptedException
     */
    protected void safeClick(WebElement element) {
        try {
            // Thử 1: Cuộn đến element với nhiều khoảng trống ở trên
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", element);
            Thread.sleep(800); // Tăng thời gian chờ

            // Thử 2: Cuộn thêm lên trên một chút để tránh bị che bởi footer/sticky elements
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0, -150);");
            Thread.sleep(500);

            // Thử 3: Click bằng JavaScript (bỏ qua tất cả interceptors)
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);

            Thread.sleep(300); // Chờ sau khi click

        } catch (InterruptedException e) {
            e.printStackTrace();
            // Fallback: nếu tất cả thất bại, thử click trực tiếp
            try {
                element.click();
            } catch (Exception ex) {
                System.err.println("⚠️ Click failed even with fallback: " + ex.getMessage());
            }
        }
    }
}
