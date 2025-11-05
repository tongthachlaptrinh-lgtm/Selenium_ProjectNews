package edu.iuh.fit.se.project_selenium.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
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
     * Hiển thị banner nhỏ ở góc trình duyệt để biết đang chạy testcase nào
     */
    protected void showTestBanner(String message) {
        try {
            String script = "var id='__selenium_test_banner__';" +
                    "var b=document.getElementById(id);" +
                    "if(!b){b=document.createElement('div');b.id=id;" +
                    "b.style.position='fixed';b.style.top='12px';b.style.right='12px';" +
                    "b.style.zIndex='2147483647';b.style.background='rgba(0,0,0,0.8)';" +
                    "b.style.color='#fff';b.style.padding='10px 14px';b.style.borderRadius='8px';" +
                    "b.style.fontFamily='system-ui,Segoe UI,Roboto,Arial';b.style.fontSize='14px';" +
                    "b.style.boxShadow='0 4px 10px rgba(0,0,0,.3)';document.body.appendChild(b);}" +
                    "b.textContent=arguments[0];";
            ((JavascriptExecutor) driver).executeScript(script, message);
        } catch (Exception ignore) { }
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
     * Gõ chuỗi vào một ô input từng ký tự có độ trễ (msPerChar milliseconds)
     */
    protected void typeSlowly(WebElement element, String text, int msPerChar) {
        if (text == null) return;
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(msPerChar);
            } catch (InterruptedException ignored) { }
        }
    }
}
