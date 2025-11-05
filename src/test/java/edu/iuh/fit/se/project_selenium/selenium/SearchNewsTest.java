package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class SearchNewsTest extends BaseSeleniumTest {

    @Test
    public void testSearchWithValidKeyword() {
        printTestInfo("Testing search functionality with valid keyword");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Find search input field
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchInput.sendKeys("Công nghệ");
        printTestInfo("Entered search keyword: Công nghệ");
        
        // Click search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        printTestInfo("Clicked search button");
        
        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if we're on search results page
        String currentUrl = driver.getCurrentUrl();
        boolean onSearchPage = currentUrl.contains("/search");
        
        // Check if search results are displayed
        boolean hasSearchResults = driver.findElements(By.className("card")).size() > 0;
        
        // Check if keyword is displayed in the page
        boolean keywordDisplayed = driver.getPageSource().contains("Công nghệ");
        
        boolean testPassed = onSearchPage && hasSearchResults && keywordDisplayed;
        printTestResult("Valid Keyword Search Test", testPassed);
        
        assertTrue(testPassed, "Search should show results for valid keyword");
    }

    @Test
    public void testSearchWithEmptyKeyword() {
        printTestInfo("Testing search with empty keyword");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Click search button without entering keyword
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        printTestInfo("Clicked search button without entering keyword");
        
        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if we're on search results page
        String currentUrl = driver.getCurrentUrl();
        boolean onSearchPage = currentUrl.contains("/search");
        
        boolean testPassed = onSearchPage;
        printTestResult("Empty Keyword Search Test", testPassed);
        
        assertTrue(testPassed, "Should navigate to search page even with empty keyword");
    }

    @Test
    public void testSearchWithNonExistentKeyword() {
        printTestInfo("Testing search with non-existent keyword");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Find search input field
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchInput.sendKeys("xyz123nonexistent");
        printTestInfo("Entered non-existent keyword: xyz123nonexistent");
        
        // Click search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        printTestInfo("Clicked search button");
        
        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if we're on search results page
        String currentUrl = driver.getCurrentUrl();
        boolean onSearchPage = currentUrl.contains("/search");
        
        // Check if "no results" message is displayed or no cards are shown
        boolean noResults = driver.findElements(By.className("card")).size() == 0 || 
                           driver.getPageSource().contains("Chưa có tin tức nào");
        
        boolean testPassed = onSearchPage && noResults;
        printTestResult("Non-existent Keyword Search Test", testPassed);
        
        assertTrue(testPassed, "Search should show no results for non-existent keyword");
    }

    @Test
    public void testSearchWithPartialKeyword() {
        printTestInfo("Testing search with partial keyword");
        
        // Navigate to home page
        navigateToHomePage();
        
        // Find search input field
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchInput.sendKeys("AI");
        printTestInfo("Entered partial keyword: AI");
        
        // Click search button
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        printTestInfo("Clicked search button");
        
        // Wait for search results page
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if we're on search results page
        String currentUrl = driver.getCurrentUrl();
        boolean onSearchPage = currentUrl.contains("/search");
        
        // Check if search results are displayed
        boolean hasSearchResults = driver.findElements(By.className("card")).size() > 0;
        
        boolean testPassed = onSearchPage && hasSearchResults;
        printTestResult("Partial Keyword Search Test", testPassed);
        
        assertTrue(testPassed, "Search should find results for partial keyword");
    }

    @Test
    public void testSearchFromNewsDetailPage() {
        printTestInfo("Testing search functionality from news detail page");
        
        // First navigate to a news detail page
        navigateToHomePage();
        
        // Click on first news article
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        printTestInfo("Clicked on first news article");
        
        // Wait for news detail page to load
        wait.until(ExpectedConditions.urlContains("/news/"));
        
        // Navigate back to home page for search
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("/"));
        
        // Perform search
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("keyword")));
        searchInput.sendKeys("Thể thao");
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
        
        // Wait for search results
        wait.until(ExpectedConditions.urlContains("/search"));
        
        // Check if search worked
        String currentUrl = driver.getCurrentUrl();
        boolean searchWorked = currentUrl.contains("/search");
        
        boolean testPassed = searchWorked;
        printTestResult("Search From Detail Page Test", testPassed);
        
        assertTrue(testPassed, "Search should work from any page");
    }
}
