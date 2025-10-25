package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest extends BaseSeleniumTest {

    @Test
    public void testAddCommentAsLoggedInUser() {
        printTestInfo("Testing add comment functionality as logged in user");
        
        // First login
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        printTestInfo("Successfully logged in as user");
        
        // Navigate to home page and click on first news
        navigateToHomePage();
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        printTestInfo("Navigated to news detail page");
        
        // Wait for news detail page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Find comment form
        WebElement commentTextarea = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("content")));
        String testComment = "Đây là bình luận test từ Selenium - " + System.currentTimeMillis();
        commentTextarea.sendKeys(testComment);
        printTestInfo("Entered comment: " + testComment);
        
        // Submit comment
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Submitted comment");
        
        // Wait for redirect back to news page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Check if success message is displayed
        boolean successMessageDisplayed = driver.findElements(By.className("alert-success")).size() > 0;
        
        // Check if comment appears in the comments list
        boolean commentAppears = driver.getPageSource().contains(testComment);
        
        boolean testPassed = successMessageDisplayed && commentAppears;
        printTestResult("Add Comment Test", testPassed);
        
        assertTrue(testPassed, "Comment should be added successfully and appear in the list");
    }

    @Test
    public void testAddCommentWithoutLogin() {
        printTestInfo("Testing add comment functionality without login");
        
        // Navigate to home page and click on first news
        navigateToHomePage();
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        printTestInfo("Navigated to news detail page");
        
        // Wait for news detail page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Check if login prompt is displayed instead of comment form
        boolean loginPromptDisplayed = driver.findElements(By.cssSelector(".card-body.text-center")).size() > 0;
        boolean loginPromptText = driver.getPageSource().contains("Đăng nhập để bình luận");
        
        // Check if comment form is NOT displayed
        boolean commentFormNotDisplayed = driver.findElements(By.name("content")).size() == 0;
        
        boolean testPassed = loginPromptDisplayed && loginPromptText && commentFormNotDisplayed;
        printTestResult("Comment Without Login Test", testPassed);
        
        assertTrue(testPassed, "Should show login prompt instead of comment form for non-logged users");
    }

    @Test
    public void testAddEmptyComment() {
        printTestInfo("Testing add empty comment");
        
        // First login
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        
        // Navigate to news detail page
        navigateToHomePage();
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Try to submit empty comment
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        printTestInfo("Attempted to submit empty comment");
        
        // Check if we're still on the same page (form validation should prevent submission)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnNewsPage = currentUrl.contains("/news/");
        
        boolean testPassed = stillOnNewsPage;
        printTestResult("Empty Comment Test", testPassed);
        
        assertTrue(testPassed, "Should stay on news page when trying to submit empty comment");
    }

    @Test
    public void testViewExistingComments() {
        printTestInfo("Testing view existing comments");
        
        // Navigate to news detail page
        navigateToHomePage();
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        printTestInfo("Navigated to news detail page");
        
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Check if comments section exists
        boolean commentsSectionExists = driver.findElements(By.cssSelector("h4")).stream()
                .anyMatch(element -> element.getText().contains("Bình luận"));
        
        // Check if there are existing comments or "no comments" message
        boolean hasCommentsOrMessage = driver.findElements(By.cssSelector(".card.mb-3")).size() > 0 ||
                                       driver.getPageSource().contains("Chưa có bình luận nào");
        
        boolean testPassed = commentsSectionExists && hasCommentsOrMessage;
        printTestResult("View Comments Test", testPassed);
        
        assertTrue(testPassed, "Comments section should be visible with either comments or no comments message");
    }

    @Test
    public void testCommentCharacterLimit() {
        printTestInfo("Testing comment character limit");
        
        // First login
        navigateToLoginPage();
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        usernameField.sendKeys("user");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123456");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Đăng xuất")));
        
        // Navigate to news detail page
        navigateToHomePage();
        WebElement firstNewsLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".news-card a")));
        firstNewsLink.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".news-content")));
        
        // Create a very long comment (over 1000 characters)
        StringBuilder longComment = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            longComment.append("Test comment text. ");
        }
        
        WebElement commentTextarea = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("content")));
        commentTextarea.sendKeys(longComment.toString());
        printTestInfo("Entered very long comment (" + longComment.length() + " characters)");
        
        // Try to submit
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        
        // Check if we're still on the same page (validation should prevent submission)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnNewsPage = currentUrl.contains("/news/");
        
        boolean testPassed = stillOnNewsPage;
        printTestResult("Comment Character Limit Test", testPassed);
        
        assertTrue(testPassed, "Should stay on news page when trying to submit comment over character limit");
    }
}
