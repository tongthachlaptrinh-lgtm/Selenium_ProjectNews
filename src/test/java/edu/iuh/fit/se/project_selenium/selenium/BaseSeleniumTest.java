package edu.iuh.fit.se.project_selenium.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
        options.addArguments("--headless"); // Run in headless mode for CI/CD
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
}
