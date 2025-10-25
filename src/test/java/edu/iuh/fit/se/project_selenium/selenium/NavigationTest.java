package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationTest extends BaseSeleniumTest {

    @Test
    public void testHomePageNavigation() {
        printTestInfo("Testing home page navigation");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Check if home page elements are present
        boolean navbarPresent = driver.findElements(By.className("navbar")).size() > 0;
        boolean searchBoxPresent = driver.findElements(By.name("keyword")).size() > 0;
        boolean newsCardsPresent = driver.findElements(By.className("news-card")).size() > 0;
        boolean footerPresent = driver.findElements(By.className("footer")).size() > 0;
        
        boolean testPassed = navbarPresent && searchBoxPresent && newsCardsPresent && footerPresent;
        printTestResult("Home Page Navigation Test", testPassed);
        
        assertTrue(testPassed, "Home page should display all main elements");
    }

    @Test
    public void testNewsDetailNavigation() {
        printTestInfo("Testing news detail page navigation");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Click on first news article
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        printTestInfo("Clicked on first news article");
        
        // Wait for news detail page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Check if news detail elements are present
        boolean newsTitlePresent = driver.findElements(By.cssSelector("h1")).size() > 0;
        boolean newsContentPresent = driver.findElements(By.cssSelector(".news-content")).size() > 0;
        boolean commentsSectionPresent = driver.findElements(By.cssSelector("h4")).stream()
                .anyMatch(element -> element.getText().contains("Bình luận"));
        boolean backButtonPresent = driver.findElements(By.linkText("Về trang chủ")).size() > 0;
        
        boolean testPassed = newsTitlePresent && newsContentPresent && commentsSectionPresent && backButtonPresent;
        printTestResult("News Detail Navigation Test", testPassed);
        
        assertTrue(testPassed, "News detail page should display all required elements");
    }

    @Test
    public void testLoginPageNavigation() {
        printTestInfo("Testing login page navigation");
        
        // Navigate to login page
        navigateToLoginPage();
        
        // Check if login page elements are present
        boolean loginFormPresent = driver.findElements(By.cssSelector("form")).size() > 0;
        boolean usernameFieldPresent = driver.findElements(By.name("username")).size() > 0;
        boolean passwordFieldPresent = driver.findElements(By.name("password")).size() > 0;
        boolean loginButtonPresent = driver.findElements(By.cssSelector("button[type='submit']")).size() > 0;
        boolean registerLinkPresent = driver.findElements(By.linkText("Đăng ký ngay")).size() > 0;
        
        boolean testPassed = loginFormPresent && usernameFieldPresent && passwordFieldPresent && 
                           loginButtonPresent && registerLinkPresent;
        printTestResult("Login Page Navigation Test", testPassed);
        
        assertTrue(testPassed, "Login page should display all required elements");
    }

    @Test
    public void testRegisterPageNavigation() {
        printTestInfo("Testing register page navigation");
        
        // Navigate to register page
        navigateToRegisterPage();
        
        // Check if register page elements are present
        boolean registerFormPresent = driver.findElements(By.cssSelector("form")).size() > 0;
        boolean usernameFieldPresent = driver.findElements(By.name("username")).size() > 0;
        boolean passwordFieldPresent = driver.findElements(By.name("password")).size() > 0;
        boolean confirmPasswordFieldPresent = driver.findElements(By.id("confirmPassword")).size() > 0;
        boolean registerButtonPresent = driver.findElements(By.cssSelector("button[type='submit']")).size() > 0;
        boolean loginLinkPresent = driver.findElements(By.linkText("Đăng nhập ngay")).size() > 0;
        
        boolean testPassed = registerFormPresent && usernameFieldPresent && passwordFieldPresent && 
                           confirmPasswordFieldPresent && registerButtonPresent && loginLinkPresent;
        printTestResult("Register Page Navigation Test", testPassed);
        
        assertTrue(testPassed, "Register page should display all required elements");
    }

    @Test
    public void testSearchResultsNavigation() {
        printTestInfo("Testing search results page navigation");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Perform search
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchInput.sendKeys("Công nghệ");
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        
        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if search results page elements are present
        boolean searchKeywordDisplayed = driver.getPageSource().contains("Công nghệ");
        boolean searchResultsPresent = driver.findElements(By.className("card")).size() > 0;
        boolean searchBoxPresent = driver.findElements(By.name("keyword")).size() > 0;
        
        boolean testPassed = searchKeywordDisplayed && searchResultsPresent && searchBoxPresent;
        printTestResult("Search Results Navigation Test", testPassed);
        
        assertTrue(testPassed, "Search results page should display search keyword and results");
    }

    @Test
    public void testAdminNavigation() {
        printTestInfo("Testing admin navigation");
        
        // First login as admin
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("admin");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        
        // Check if admin link is visible in navbar
        boolean adminLinkVisible = driver.findElements(By.linkText("Quản trị")).size() > 0;
        
        // Click on admin link
        if (adminLinkVisible) {
            WebElement adminLink = driver.findElement(By.linkText("Quản trị"));
            adminLink.click();
            printTestInfo("Clicked on admin link");
            
            // Wait for admin dashboard to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2")));
            
            // Check if admin dashboard elements are present
            boolean dashboardTitle = driver.getPageSource().contains("Bảng điều khiển quản trị");
            boolean statisticsCards = driver.findElements(By.cssSelector(".card.bg-primary")).size() > 0;
            
            boolean testPassed = dashboardTitle && statisticsCards;
            printTestResult("Admin Navigation Test", testPassed);
            
            assertTrue(testPassed, "Admin navigation should work and display dashboard");
        } else {
            printTestResult("Admin Navigation Test", false);
            fail("Admin link should be visible for admin users");
        }
    }

    @Test
    public void testBreadcrumbNavigation() {
        printTestInfo("Testing breadcrumb navigation");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Click on first news article
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        
        // Wait for news detail page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Click back to home button
        WebElement backButton = driver.findElement(By.linkText("Về trang chủ"));
        backButton.click();
        printTestInfo("Clicked back to home button");
        
        // Wait for home page to load
        wait.until(ExpectedConditions.urlContains("/"));
        
        // Check if we're back on home page
        String currentUrl = driver.getCurrentUrl();
        boolean backOnHomePage = currentUrl.equals(baseUrl + "/") || currentUrl.equals(baseUrl + "/");
        
        boolean testPassed = backOnHomePage;
        printTestResult("Breadcrumb Navigation Test", testPassed);
        
        assertTrue(testPassed, "Back navigation should return to home page");
    }

    @Test
    public void testResponsiveNavigation() {
        printTestInfo("Testing responsive navigation");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Check if navbar is responsive
        boolean navbarPresent = driver.findElements(By.className("navbar")).size() > 0;
        boolean navbarTogglePresent = driver.findElements(By.className("navbar-toggler")).size() > 0;
        boolean navbarCollapsePresent = driver.findElements(By.id("navbarNav")).size() > 0;
        
        boolean testPassed = navbarPresent && navbarTogglePresent && navbarCollapsePresent;
        printTestResult("Responsive Navigation Test", testPassed);
        
        assertTrue(testPassed, "Navigation should be responsive with toggle button");
    }
}
