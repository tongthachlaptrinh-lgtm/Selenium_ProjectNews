package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class AddNewsTest extends BaseSeleniumTest {

    @Test
    public void testAddNewsAsAdmin() {
        printTestInfo("Testing add news functionality as admin");
        
        // Login as admin using helper method
        loginAsAdmin();
        
        // Navigate to add news page
        driver.get(baseUrl + "/admin/news/add");
        printTestInfo("Navigated to add news page");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        // Fill in news form
        WebElement titleField = driver.findElement(By.id("title"));
        String testTitle = "Tin tức test từ Selenium - " + System.currentTimeMillis();
        titleField.sendKeys(testTitle);
        printTestInfo("Entered title: " + testTitle);
        
        WebElement summaryField = driver.findElement(By.id("summary"));
        summaryField.sendKeys("Đây là tóm tắt bài viết test từ Selenium WebDriver");
        printTestInfo("Entered summary");
        
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Đây là nội dung chi tiết của bài viết test được tạo từ Selenium WebDriver. " +
                            "Bài viết này được sử dụng để kiểm thử chức năng thêm bài viết mới trong hệ thống News Portal.");
        printTestInfo("Entered content");
        
        WebElement imageUrlField = driver.findElement(By.id("imageUrl"));
        imageUrlField.sendKeys("https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800&h=400&fit=crop");
        printTestInfo("Entered image URL");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Submitted news form");
        
        // Wait for redirect to news management page
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        // Check if we're on the news management page
        String currentUrl = driver.getCurrentUrl();
        boolean onNewsManagementPage = currentUrl.contains("/admin/news");
        
        // Check if success message is displayed
        boolean successMessageDisplayed = driver.findElements(By.className("alert-success")).size() > 0;
        
        // Check if the new news appears in the list
        boolean newsAppearsInList = driver.getPageSource().contains(testTitle);
        
        boolean testPassed = onNewsManagementPage && successMessageDisplayed && newsAppearsInList;
        printTestResult("Add News Test", testPassed);
        
        assertTrue(testPassed, "News should be added successfully and appear in management page");
    }

    @Test
    public void testAddNewsWithoutLogin() {
        printTestInfo("Testing add news functionality without login");
        
        // Try to access add news page without login
        driver.get(baseUrl + "/admin/news/add");
        printTestInfo("Attempted to access add news page without login");
        
        // Wait for redirect to login page
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Check if we're redirected to login page
        String currentUrl = driver.getCurrentUrl();
        boolean redirectedToLogin = currentUrl.contains("/login");
        
        boolean testPassed = redirectedToLogin;
        printTestResult("Add News Without Login Test", testPassed);
        
        assertTrue(testPassed, "Should be redirected to login page when accessing admin area without login");
    }

    @Test
    public void testAddNewsAsRegularUser() {
        printTestInfo("Testing add news functionality as regular user");
        
        // Login as regular user using helper method
        loginAsUser();
        
        // Try to access add news page
        driver.get(baseUrl + "/admin/news/add");
        printTestInfo("Attempted to access add news page as regular user");
        
        // Wait for redirect to login page or access denied
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("/login"),
            ExpectedConditions.presenceOfElementLocated(By.className("alert-danger"))
        ));
        
        // Check if we're redirected to login page or get access denied
        String currentUrl = driver.getCurrentUrl();
        boolean redirectedToLogin = currentUrl.contains("/login");
        boolean accessDenied = driver.findElements(By.className("alert-danger")).size() > 0;
        
        boolean testPassed = redirectedToLogin || accessDenied;
        printTestResult("Add News As Regular User Test", testPassed);
        
        assertTrue(testPassed, "Regular user should not be able to access admin add news page");
    }

    @Test
    public void testAddNewsWithEmptyFields() {
        printTestInfo("Testing add news with empty required fields");
        
        // Login as admin using helper method
        loginAsAdmin();
        
        // Navigate to add news page
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        // Try to submit form without filling required fields
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Attempted to submit form with empty required fields");
        
        // Check if we're still on the add news page (validation should prevent submission)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnAddNewsPage = currentUrl.contains("/admin/news/add");
        
        boolean testPassed = stillOnAddNewsPage;
        printTestResult("Add News With Empty Fields Test", testPassed);
        
        assertTrue(testPassed, "Should stay on add news page when trying to submit empty required fields");
    }

    @Test
    public void testAddNewsWithInvalidImageUrl() {
        printTestInfo("Testing add news with invalid image URL");
        
        // Login as admin using helper method
        loginAsAdmin();
        
        // Navigate to add news page
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        // Fill in required fields
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Test News with Invalid Image URL");
        
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("This is test content for news with invalid image URL.");
        
        // Enter invalid image URL
        WebElement imageUrlField = driver.findElement(By.id("imageUrl"));
        imageUrlField.sendKeys("invalid-url-format");
        printTestInfo("Entered invalid image URL");
        
        // Submit form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Submitted form with invalid image URL");
        
        // Wait for redirect to news management page
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        // Check if we're on the news management page (form should still submit)
        String currentUrl = driver.getCurrentUrl();
        boolean onNewsManagementPage = currentUrl.contains("/admin/news");
        
        boolean testPassed = onNewsManagementPage;
        printTestResult("Add News With Invalid Image URL Test", testPassed);
        
        assertTrue(testPassed, "Form should still submit even with invalid image URL");
    }

    @Test
    public void testAdminDashboardAccess() {
        printTestInfo("Testing admin dashboard access");
        
        // Login as admin using helper method
        loginAsAdmin();
        
        // Navigate to admin dashboard
        driver.get(baseUrl + "/admin/dashboard");
        printTestInfo("Navigated to admin dashboard");
        
        // Wait for dashboard to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2")));
        
        // Check if dashboard elements are present
        boolean dashboardTitle = driver.getPageSource().contains("Bảng điều khiển quản trị");
        boolean statisticsCards = driver.findElements(By.cssSelector(".card.bg-primary")).size() > 0;
        boolean quickActions = driver.findElements(By.cssSelector(".btn-primary")).size() > 0;
        
        boolean testPassed = dashboardTitle && statisticsCards && quickActions;
        printTestResult("Admin Dashboard Access Test", testPassed);
        
        assertTrue(testPassed, "Admin dashboard should be accessible and display all elements");
    }
}
