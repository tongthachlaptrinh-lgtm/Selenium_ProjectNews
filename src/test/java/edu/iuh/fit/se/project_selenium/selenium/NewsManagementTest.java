package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class cho chức năng quản lý tin tức
 */
public class NewsManagementTest extends BaseSeleniumTest {

    @Test
    public void testNavigateToNewsList() {
        printTestInfo("Testing navigation to news management list");
        
        loginAsAdmin();
        
        driver.get(baseUrl + "/admin/news");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2")));
        
        boolean pageLoaded = driver.getCurrentUrl().contains("/admin/news");
        
        printTestResult("Navigate to News List Test", pageLoaded);
        assertTrue(pageLoaded);
    }

    @Test
    public void testAccessAdminAsRegularUser() {
        printTestInfo("Testing regular user cannot access admin area");
        
        loginAsUser();
        
        driver.get(baseUrl + "/admin/news");
        
        boolean redirected = driver.getCurrentUrl().contains("/login");
        
        printTestResult("Regular User Access Test", redirected);
        assertTrue(redirected, "Should redirect to login page");
    }
}
