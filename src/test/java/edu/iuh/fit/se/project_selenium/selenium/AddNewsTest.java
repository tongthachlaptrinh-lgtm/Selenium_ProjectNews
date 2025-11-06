package edu.iuh.fit.se.project_selenium.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Suite cho tính năng Admin tạo bài viết
 * TC_ADMIN_POST_01: Tạo bài viết (Admin)
 */
public class AddNewsTest extends BaseSeleniumTest {

    /**
     * TC_ADMIN_POST_01: Kiểm tra người dùng có quyền Admin có thể đăng một bài viết mới thành công
     *
     * Các bước test:
     * 1. Đăng nhập với tài khoản Admin
     * 2. Đi đến trang 'Admin Dashboard' -> 'Tạo bài viết mới'
     * 3. Nhập 'Tiêu đề' (vd: 'Bài test tự động Selenium')
     * 4. Nhập 'Nội dung'
     * 5. Chọn một 'Danh mục'
     * 6. Nhấp vào nút 'Đăng bài'
     *
     * Kết quả mong đợi:
     * - Hiển thị thông báo 'Đăng bài viết thành công'
     * - Bài viết mới xuất hiện trên trang chủ
     */
    @Test
    public void TC_ADMIN_POST_01_testAddNewsAsAdminSuccess() {
        printTestInfo("=== TC_ADMIN_POST_01: Kiểm tra Admin có thể đăng bài viết mới thành công ===");

        // Bước 1: Đăng nhập với tài khoản Admin
        printTestInfo("Bước 1: Đăng nhập với tài khoản Admin");
        loginAsAdmin();
        
        // Bước 2: Đi đến trang 'Admin Dashboard' -> 'Tạo bài viết mới'
        printTestInfo("Bước 2: Điều hướng đến trang 'Tạo bài viết mới'");
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        printTestInfo("✓ Đã vào trang tạo bài viết");

        // Bước 3: Nhập 'Tiêu đề'
        printTestInfo("Bước 3: Nhập tiêu đề bài viết");
        String testTitle = "Bài test tự động Selenium - " + System.currentTimeMillis();
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(testTitle);
        printTestInfo("✓ Đã nhập tiêu đề: " + testTitle);

        // Nhập tóm tắt (tùy chọn)
        WebElement summaryField = driver.findElement(By.id("summary"));
        summaryField.sendKeys("Đây là tóm tắt bài viết test từ Selenium WebDriver để kiểm thử chức năng tạo bài viết.");
        printTestInfo("✓ Đã nhập tóm tắt");

        // Bước 4: Nhập 'Nội dung'
        printTestInfo("Bước 4: Nhập nội dung bài viết");
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Đây là nội dung chi tiết của bài viết test được tạo từ Selenium WebDriver. " +
                            "Bài viết này được sử dụng để kiểm thử chức năng thêm bài viết mới trong hệ thống News Portal. " +
                            "Nội dung này đủ dài để đảm bảo validation hoạt động đúng.");
        printTestInfo("✓ Đã nhập nội dung");

        // Nhập URL hình ảnh (tùy chọn)
        WebElement imageUrlField = driver.findElement(By.id("imageUrl"));
        imageUrlField.sendKeys("https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800&h=400&fit=crop");
        printTestInfo("✓ Đã nhập URL hình ảnh");

        // Bước 5: Chọn một 'Danh mục'
        printTestInfo("Bước 5: Chọn danh mục");
        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        // Chọn danh mục đầu tiên (không phải option trống)
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
            printTestInfo("✓ Đã chọn danh mục: " + select.getFirstSelectedOption().getText());
        }

        // Bước 6: Nhấp vào nút 'Đăng bài'
        printTestInfo("Bước 6: Nhấp vào nút 'Đăng bài'");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));

        // Sử dụng safeClick helper method
        safeClick(submitButton);
        printTestInfo("✓ Đã nhấp nút 'Đăng bài'");

        // Kiểm tra kết quả mong đợi
        printTestInfo("Kiểm tra kết quả...");

        // 1. Kiểm tra chuyển hướng về trang quản lý bài viết
        wait.until(ExpectedConditions.urlContains("/admin/news"));
        String currentUrl = driver.getCurrentUrl();
        boolean onNewsManagementPage = currentUrl.contains("/admin/news") && !currentUrl.contains("/add");
        printTestInfo("✓ Đã chuyển hướng về trang quản lý: " + onNewsManagementPage);

        // 2. Kiểm tra thông báo thành công
        boolean hasSuccessMessage = false;
        try {
            WebElement successAlert = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("success-message")));
            hasSuccessMessage = successAlert.getText().toLowerCase().contains("thành công");
            printTestInfo("✓ Hiển thị thông báo thành công: " + successAlert.getText());
        } catch (Exception e) {
            printTestInfo("⚠ Không tìm thấy thông báo thành công");
        }

        // 3. Kiểm tra bài viết xuất hiện trong danh sách quản lý
        boolean newsAppearsInList = driver.getPageSource().contains(testTitle);
        printTestInfo("✓ Bài viết xuất hiện trong danh sách: " + newsAppearsInList);

        // 4. Kiểm tra bài viết xuất hiện trên trang chủ
        printTestInfo("Kiểm tra bài viết trên trang chủ...");
        navigateToHomePage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        boolean newsOnHomepage = driver.getPageSource().contains(testTitle);
        printTestInfo("✓ Bài viết xuất hiện trên trang chủ: " + newsOnHomepage);

        // Tổng hợp kết quả
        boolean testPassed = onNewsManagementPage && hasSuccessMessage && newsAppearsInList && newsOnHomepage;
        printTestResult("TC_ADMIN_POST_01", testPassed);

        assertTrue(testPassed, "Admin phải có thể tạo bài viết thành công và bài viết phải xuất hiện trên trang chủ");
    }

    /**
     * TC_ADMIN_POST_02: Kiểm tra người dùng chưa đăng nhập không thể truy cập trang tạo bài viết
     */
    @Test
    public void TC_ADMIN_POST_02_testAddNewsWithoutLogin() {
        printTestInfo("=== TC_ADMIN_POST_02: Kiểm tra không thể truy cập trang tạo bài viết khi chưa đăng nhập ===");

        // Thử truy cập trang tạo bài viết mà không đăng nhập
        printTestInfo("Bước 1: Truy cập trang tạo bài viết mà không đăng nhập");
        driver.get(baseUrl + "/admin/news/add");

        // Chờ chuyển hướng về trang đăng nhập
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Kiểm tra đã chuyển hướng về trang đăng nhập
        String currentUrl = driver.getCurrentUrl();
        boolean redirectedToLogin = currentUrl.contains("/login");
        printTestInfo("✓ Đã chuyển hướng về trang đăng nhập: " + redirectedToLogin);

        printTestResult("TC_ADMIN_POST_02", redirectedToLogin);

        assertTrue(redirectedToLogin, "Phải chuyển hướng về trang đăng nhập khi chưa đăng nhập");
    }

    /**
     * TC_ADMIN_POST_03: Kiểm tra người dùng thường không thể truy cập trang tạo bài viết
     */
    @Test
    public void TC_ADMIN_POST_03_testAddNewsAsRegularUser() {
        printTestInfo("=== TC_ADMIN_POST_03: Kiểm tra user thường không thể truy cập trang tạo bài viết ===");

        // Đăng nhập với tài khoản user thường
        printTestInfo("Bước 1: Đăng nhập với tài khoản user thường");
        loginAsUser();
        
        // Thử truy cập trang tạo bài viết
        printTestInfo("Bước 2: Thử truy cập trang tạo bài viết");
        driver.get(baseUrl + "/admin/news/add");

        // Chờ chuyển hướng về trang đăng nhập hoặc hiển thị lỗi truy cập bị từ chối
        waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        boolean redirectedToLogin = currentUrl.contains("/login");
        boolean accessDenied = driver.getPageSource().toLowerCase().contains("access denied")
                            || driver.getPageSource().toLowerCase().contains("truy cập bị từ chối")
                            || driver.getPageSource().contains("403");

        boolean testPassed = redirectedToLogin || accessDenied;
        printTestInfo("✓ Chuyển hướng về login: " + redirectedToLogin);
        printTestInfo("✓ Hiển thị lỗi truy cập: " + accessDenied);

        printTestResult("TC_ADMIN_POST_03", testPassed);

        assertTrue(testPassed, "User thường không được phép truy cập trang tạo bài viết");
    }

    /**
     * TC_ADMIN_POST_04: Kiểm tra không thể tạo bài viết khi thiếu trường bắt buộc (Tiêu đề)
     */
    @Test
    public void TC_ADMIN_POST_04_testAddNewsWithoutTitle() {
        printTestInfo("=== TC_ADMIN_POST_04: Kiểm tra không thể tạo bài viết khi thiếu Tiêu đề ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Không nhập tiêu đề");
        // Chỉ nhập nội dung và danh mục, bỏ qua tiêu đề

        printTestInfo("Bước 2: Nhập nội dung");
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test");

        printTestInfo("Bước 3: Chọn danh mục");
        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        printTestInfo("Bước 4: Thử submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        waitForPageLoad();

        // Kiểm tra vẫn ở trang tạo bài viết (validation ngăn chặn submit)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnAddNewsPage = currentUrl.contains("/admin/news/add");

        // Kiểm tra HTML5 validation hoặc thông báo lỗi
        WebElement titleField = driver.findElement(By.id("title"));
        String validationMessage = (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", titleField);
        boolean hasValidation = validationMessage != null && !validationMessage.isEmpty();

        printTestInfo("✓ Vẫn ở trang tạo bài viết: " + stillOnAddNewsPage);
        printTestInfo("✓ Có thông báo validation: " + hasValidation);

        boolean testPassed = stillOnAddNewsPage && hasValidation;
        printTestResult("TC_ADMIN_POST_04", testPassed);

        assertTrue(testPassed, "Không thể tạo bài viết khi thiếu tiêu đề");
    }

    /**
     * TC_ADMIN_POST_05: Kiểm tra không thể tạo bài viết khi thiếu Nội dung
     */
    @Test
    public void TC_ADMIN_POST_05_testAddNewsWithoutContent() {
        printTestInfo("=== TC_ADMIN_POST_05: Kiểm tra không thể tạo bài viết khi thiếu Nội dung ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        printTestInfo("Bước 1: Nhập tiêu đề");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Tiêu đề test");

        printTestInfo("Bước 2: Không nhập nội dung");
        // Bỏ qua nội dung

        printTestInfo("Bước 3: Chọn danh mục");
        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        printTestInfo("Bước 4: Thử submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        boolean stillOnAddNewsPage = currentUrl.contains("/admin/news/add");
        
        WebElement contentField = driver.findElement(By.id("content"));
        String validationMessage = (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", contentField);
        boolean hasValidation = validationMessage != null && !validationMessage.isEmpty();

        printTestInfo("✓ Vẫn ở trang tạo bài viết: " + stillOnAddNewsPage);
        printTestInfo("✓ Có thông báo validation: " + hasValidation);

        boolean testPassed = stillOnAddNewsPage && hasValidation;
        printTestResult("TC_ADMIN_POST_05", testPassed);

        assertTrue(testPassed, "Không thể tạo bài viết khi thiếu nội dung");
    }

    /**
     * TC_ADMIN_POST_06: Kiểm tra không thể tạo bài viết khi không chọn Danh mục
     */
    @Test
    public void TC_ADMIN_POST_06_testAddNewsWithoutCategory() {
        printTestInfo("=== TC_ADMIN_POST_06: Kiểm tra không thể tạo bài viết khi không chọn Danh mục ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Nhập tiêu đề");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Tiêu đề test");

        printTestInfo("Bước 2: Nhập nội dung");
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test");

        printTestInfo("Bước 3: Không chọn danh mục");
        // Bỏ qua việc chọn danh mục

        printTestInfo("Bước 4: Thử submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        waitForPageLoad();

        String currentUrl = driver.getCurrentUrl();
        boolean stillOnAddNewsPage = currentUrl.contains("/admin/news/add");

        // Kiểm tra alert hoặc validation
        boolean hasAlert = false;
        try {
            driver.switchTo().alert();
            hasAlert = true;
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // Không có alert
        }

        printTestInfo("✓ Vẫn ở trang tạo bài viết: " + stillOnAddNewsPage);
        printTestInfo("✓ Có cảnh báo: " + hasAlert);

        boolean testPassed = stillOnAddNewsPage;
        printTestResult("TC_ADMIN_POST_06", testPassed);

        assertTrue(testPassed, "Không thể tạo bài viết khi không chọn danh mục");
    }

    /**
     * TC_ADMIN_POST_07: Kiểm tra tạo bài viết với tiêu đề dài (boundary test)
     */
    @Test
    public void TC_ADMIN_POST_07_testAddNewsWithLongTitle() {
        printTestInfo("=== TC_ADMIN_POST_07: Kiểm tra tạo bài viết với tiêu đề dài (200 ký tự) ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        // Tạo tiêu đề đúng 200 ký tự (giới hạn tối đa)
        String longTitle = "A".repeat(190) + System.currentTimeMillis() / 1000; // ~200 chars

        printTestInfo("Bước 1: Nhập tiêu đề dài 200 ký tự");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(longTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test cho tiêu đề dài");

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        printTestInfo("Bước 2: Submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));

        boolean success = !driver.getCurrentUrl().contains("/add");
        printTestInfo("✓ Tạo bài viết thành công với tiêu đề dài: " + success);

        printTestResult("TC_ADMIN_POST_07", success);

        assertTrue(success, "Có thể tạo bài viết với tiêu đề dài 200 ký tự");
    }

    /**
     * TC_ADMIN_POST_08: Kiểm tra tạo bài viết với URL hình ảnh hợp lệ
     */
    @Test
    public void TC_ADMIN_POST_08_testAddNewsWithValidImageUrl() {
        printTestInfo("=== TC_ADMIN_POST_08: Kiểm tra tạo bài viết với URL hình ảnh hợp lệ ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String testTitle = "Bài viết có hình ảnh - " + System.currentTimeMillis();

        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(testTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung bài viết có hình ảnh");

        printTestInfo("Bước 1: Nhập URL hình ảnh hợp lệ");
        WebElement imageUrlField = driver.findElement(By.id("imageUrl"));
        String validImageUrl = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?w=800&h=400&fit=crop";
        imageUrlField.sendKeys(validImageUrl);
        printTestInfo("✓ URL hình ảnh: " + validImageUrl);

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));
        
        boolean success = !driver.getCurrentUrl().contains("/add");
        printTestInfo("✓ Tạo bài viết thành công với hình ảnh: " + success);

        printTestResult("TC_ADMIN_POST_08", success);

        assertTrue(success, "Có thể tạo bài viết với URL hình ảnh hợp lệ");
    }

    /**
     * TC_ADMIN_POST_09: Kiểm tra tạo bài viết không có hình ảnh (trường tùy chọn)
     */
    @Test
    public void TC_ADMIN_POST_09_testAddNewsWithoutImage() {
        printTestInfo("=== TC_ADMIN_POST_09: Kiểm tra tạo bài viết không có hình ảnh ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String testTitle = "Bài viết không có hình ảnh - " + System.currentTimeMillis();

        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(testTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung bài viết không có hình ảnh");

        printTestInfo("Bước 1: Không nhập URL hình ảnh");
        // Bỏ qua imageUrl vì là trường tùy chọn

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));

        boolean success = !driver.getCurrentUrl().contains("/add");
        printTestInfo("✓ Tạo bài viết thành công không cần hình ảnh: " + success);

        printTestResult("TC_ADMIN_POST_09", success);

        assertTrue(success, "Có thể tạo bài viết mà không cần hình ảnh (trường tùy chọn)");
    }

    /**
     * TC_ADMIN_POST_10: Kiểm tra bài viết mới xuất hiện trên trang chủ sau khi tạo
     */
    @Test
    public void TC_ADMIN_POST_10_testNewNewsAppearsOnHomepage() {
        printTestInfo("=== TC_ADMIN_POST_10: Kiểm tra bài viết mới xuất hiện trên trang chủ ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String uniqueTitle = "Bài viết kiểm tra trang chủ - " + System.currentTimeMillis();

        printTestInfo("Bước 1: Tạo bài viết mới");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(uniqueTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung kiểm tra xuất hiện trên trang chủ");

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));
        printTestInfo("✓ Đã tạo bài viết");

        printTestInfo("Bước 2: Truy cập trang chủ");
        navigateToHomePage();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        printTestInfo("Bước 3: Kiểm tra bài viết xuất hiện trên trang chủ");
        boolean newsOnHomepage = driver.getPageSource().contains(uniqueTitle);
        printTestInfo("✓ Bài viết xuất hiện trên trang chủ: " + newsOnHomepage);

        printTestResult("TC_ADMIN_POST_10", newsOnHomepage);

        assertTrue(newsOnHomepage, "Bài viết mới phải xuất hiện trên trang chủ sau khi tạo");
    }

    /**
     * TC_ADMIN_POST_11: Kiểm tra tạo bài viết với tiêu đề quá dài (> 200 ký tự)
     * Boundary test: vượt quá giới hạn
     */
    @Test
    public void TC_ADMIN_POST_11_testAddNewsWithTitleTooLong() {
        printTestInfo("=== TC_ADMIN_POST_11: Kiểm tra tiêu đề quá dài (> 200 ký tự) ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        // Tạo tiêu đề 250 ký tự (vượt giới hạn 200)
        String tooLongTitle = "A".repeat(250);

        printTestInfo("Bước 1: Nhập tiêu đề quá dài (250 ký tự)");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(tooLongTitle);

        // Kiểm tra maxlength attribute đã chặn
        String actualValue = titleField.getAttribute("value");
        boolean isLimited = actualValue.length() <= 200;
        printTestInfo("✓ Giá trị thực tế: " + actualValue.length() + " ký tự");
        printTestInfo("✓ Đã giới hạn tối đa 200 ký tự: " + isLimited);

        printTestResult("TC_ADMIN_POST_11", isLimited);

        assertTrue(isLimited, "Tiêu đề phải bị giới hạn tối đa 200 ký tự");
    }

    /**
     * TC_ADMIN_POST_12: Kiểm tra tạo bài viết với tóm tắt quá dài (> 500 ký tự)
     * Boundary test cho trường tóm tắt
     */
    @Test
    public void TC_ADMIN_POST_12_testAddNewsWithSummaryTooLong() {
        printTestInfo("=== TC_ADMIN_POST_12: Kiểm tra tóm tắt quá dài (> 500 ký tự) ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        // Tạo tóm tắt 600 ký tự (vượt giới hạn 500)
        String tooLongSummary = "B".repeat(600);

        printTestInfo("Bước 1: Nhập tóm tắt quá dài (600 ký tự)");
        WebElement summaryField = driver.findElement(By.id("summary"));
        summaryField.sendKeys(tooLongSummary);

        // Kiểm tra maxlength attribute đã chặn
        String actualValue = summaryField.getAttribute("value");
        boolean isLimited = actualValue.length() <= 500;
        printTestInfo("✓ Giá trị thực tế: " + actualValue.length() + " ký tự");
        printTestInfo("✓ Đã giới hạn tối đa 500 ký tự: " + isLimited);

        printTestResult("TC_ADMIN_POST_12", isLimited);

        assertTrue(isLimited, "Tóm tắt phải bị giới hạn tối đa 500 ký tự");
    }

    /**
     * TC_ADMIN_POST_13: Kiểm tra tạo bài viết với URL hình ảnh không hợp lệ
     * Negative test cho URL validation
     */
    @Test
    public void TC_ADMIN_POST_13_testAddNewsWithInvalidImageUrl() {
        printTestInfo("=== TC_ADMIN_POST_13: Kiểm tra URL hình ảnh không hợp lệ ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String testTitle = "Bài viết URL không hợp lệ - " + System.currentTimeMillis();

        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(testTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung bài viết URL không hợp lệ");

        printTestInfo("Bước 1: Nhập URL hình ảnh không hợp lệ");
        WebElement imageUrlField = driver.findElement(By.id("imageUrl"));
        String invalidUrl = "not-a-valid-url";
        imageUrlField.sendKeys(invalidUrl);

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        printTestInfo("Bước 2: Thử submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        waitForPageLoad();

        // Kiểm tra HTML5 URL validation
        String validationMessage = (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", imageUrlField);
        boolean hasValidation = validationMessage != null && !validationMessage.isEmpty();

        printTestInfo("✓ Có validation URL: " + hasValidation);
        printTestInfo("✓ Thông báo: " + validationMessage);

        printTestResult("TC_ADMIN_POST_13", hasValidation);

        assertTrue(hasValidation, "Phải có validation cho URL không hợp lệ");
    }

    /**
     * TC_ADMIN_POST_14: Kiểm tra tạo bài viết với ký tự đặc biệt trong tiêu đề
     * Test với special characters
     */
    @Test
    public void TC_ADMIN_POST_14_testAddNewsWithSpecialCharactersInTitle() {
        printTestInfo("=== TC_ADMIN_POST_14: Kiểm tra tiêu đề có ký tự đặc biệt ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String specialTitle = "Tiêu đề <test> & \"đặc biệt\" @ 2025 - " + System.currentTimeMillis();

        printTestInfo("Bước 1: Nhập tiêu đề có ký tự đặc biệt");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(specialTitle);

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test ký tự đặc biệt");

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));

        boolean success = !driver.getCurrentUrl().contains("/add");
        printTestInfo("✓ Tạo bài viết thành công với ký tự đặc biệt: " + success);

        printTestResult("TC_ADMIN_POST_14", success);

        assertTrue(success, "Phải có thể tạo bài viết với ký tự đặc biệt");
    }

    /**
     * TC_ADMIN_POST_15: Kiểm tra tạo bài viết với nội dung chỉ có khoảng trắng
     * Test với whitespace-only content
     */
    @Test
    public void TC_ADMIN_POST_15_testAddNewsWithWhitespaceOnlyContent() {
        printTestInfo("=== TC_ADMIN_POST_15: Kiểm tra nội dung chỉ có khoảng trắng ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Tiêu đề test");

        printTestInfo("Bước 1: Nhập nội dung chỉ có khoảng trắng");
        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("     "); // Chỉ có khoảng trắng

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        printTestInfo("Bước 2: Thử submit form");
        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        waitForPageLoad();

        // Kiểm tra alert xuất hiện (validation từ JavaScript)
        boolean hasAlert = false;
        String alertText = "";
        try {
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            hasAlert = true;
            printTestInfo("✓ Alert xuất hiện với thông báo: " + alertText);
            alert.accept(); // Đóng alert
        } catch (Exception e) {
            printTestInfo("⚠ Không có alert");
        }

        // Kiểm tra vẫn ở trang add (validation phía client hoặc server)
        String currentUrl = driver.getCurrentUrl();
        boolean stillOnAddPage = currentUrl.contains("/admin/news/add");

        printTestInfo("✓ Vẫn ở trang tạo bài viết: " + stillOnAddPage);
        printTestInfo("✓ Có cảnh báo: " + hasAlert);

        boolean testPassed = stillOnAddPage && hasAlert;
        printTestResult("TC_ADMIN_POST_15", testPassed);

        assertTrue(testPassed, "Không được phép tạo bài viết với nội dung chỉ có khoảng trắng");
    }

    /**
     * TC_ADMIN_POST_16: Kiểm tra tạo nhiều bài viết liên tiếp
     * Test với multiple submissions
     */
    @Test
    public void TC_ADMIN_POST_16_testAddMultipleNewsInSequence() {
        printTestInfo("=== TC_ADMIN_POST_16: Kiểm tra tạo nhiều bài viết liên tiếp ===");

        loginAsAdmin();

        int numberOfNews = 3;
        boolean allSuccess = true;

        for (int i = 1; i <= numberOfNews; i++) {
            printTestInfo("Tạo bài viết thứ " + i + "...");

            driver.get(baseUrl + "/admin/news/add");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

            String testTitle = "Bài viết liên tiếp số " + i + " - " + System.currentTimeMillis();

            WebElement titleField = driver.findElement(By.id("title"));
            titleField.sendKeys(testTitle);

            WebElement contentField = driver.findElement(By.id("content"));
            contentField.sendKeys("Nội dung bài viết số " + i);

            WebElement categorySelect = driver.findElement(By.id("category"));
            Select select = new Select(categorySelect);
            if (select.getOptions().size() > 1) {
                select.selectByIndex(1);
            }

            WebElement submitButton = driver.findElement(By.id("submit-btn"));
            safeClick(submitButton);

            wait.until(ExpectedConditions.urlContains("/admin/news"));

            boolean success = !driver.getCurrentUrl().contains("/add");
            printTestInfo("✓ Bài viết " + i + " thành công: " + success);

            if (!success) {
                allSuccess = false;
                break;
            }

            sleep(1); // Tránh tạo quá nhanh
        }

        printTestResult("TC_ADMIN_POST_16", allSuccess);

        assertTrue(allSuccess, "Phải có thể tạo nhiều bài viết liên tiếp");
    }

    /**
     * TC_ADMIN_POST_17: Kiểm tra nút "Làm mới" (Reset form)
     * Test reset button functionality
     */
    @Test
    public void TC_ADMIN_POST_17_testResetFormButton() {
        printTestInfo("=== TC_ADMIN_POST_17: Kiểm tra nút 'Làm mới' form ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Nhập dữ liệu vào form");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Tiêu đề test reset");

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test reset");

        printTestInfo("Bước 2: Nhấp nút 'Làm mới'");
        WebElement resetButton = driver.findElement(By.cssSelector("button[type='reset']"));
        safeClick(resetButton);

        waitForPageLoad();

        // Kiểm tra form đã được reset
        String titleValue = driver.findElement(By.id("title")).getAttribute("value");
        String contentValue = driver.findElement(By.id("content")).getAttribute("value");

        boolean isReset = (titleValue == null || titleValue.isEmpty()) &&
                         (contentValue == null || contentValue.isEmpty());

        printTestInfo("✓ Form đã được reset: " + isReset);

        printTestResult("TC_ADMIN_POST_17", isReset);

        assertTrue(isReset, "Nút 'Làm mới' phải reset toàn bộ form");
    }

    /**
     * TC_ADMIN_POST_18: Kiểm tra nút "Hủy" quay lại trang danh sách
     * Test cancel button navigation
     */
    @Test
    public void TC_ADMIN_POST_18_testCancelButtonNavigation() {
        printTestInfo("=== TC_ADMIN_POST_18: Kiểm tra nút 'Hủy' quay lại ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Nhập một số dữ liệu");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Tiêu đề test hủy");

        printTestInfo("Bước 2: Nhấp nút 'Hủy'");
        WebElement cancelButton = driver.findElement(By.cssSelector("a.btn-outline-danger"));
        safeClick(cancelButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));

        String currentUrl = driver.getCurrentUrl();
        boolean navigatedBack = currentUrl.contains("/admin/news") && !currentUrl.contains("/add");

        printTestInfo("✓ Đã quay lại trang danh sách: " + navigatedBack);

        printTestResult("TC_ADMIN_POST_18", navigatedBack);

        assertTrue(navigatedBack, "Nút 'Hủy' phải quay lại trang danh sách");
    }

    /**
     * TC_ADMIN_POST_19: Kiểm tra Character Counter hiển thị đúng
     * Test UI character counter functionality
     */
    @Test
    public void TC_ADMIN_POST_19_testCharacterCounterDisplay() {
        printTestInfo("=== TC_ADMIN_POST_19: Kiểm tra Character Counter ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Nhập tiêu đề và kiểm tra counter");
        WebElement titleField = driver.findElement(By.id("title"));
        String testText = "Test Counter";
        titleField.sendKeys(testText);

        waitForPageLoad();

        WebElement titleCounter = driver.findElement(By.id("titleCounter"));
        String counterText = titleCounter.getText();

        printTestInfo("✓ Counter hiển thị: " + counterText);

        boolean hasCounter = counterText.contains(String.valueOf(testText.length()));

        printTestResult("TC_ADMIN_POST_19", hasCounter);

        assertTrue(hasCounter, "Character counter phải hiển thị đúng số ký tự");
    }

    /**
     * TC_ADMIN_POST_20: Kiểm tra Preview Panel cập nhật real-time
     * Test real-time preview functionality
     */
    @Test
    public void TC_ADMIN_POST_20_testPreviewPanelRealtime() {
        printTestInfo("=== TC_ADMIN_POST_20: Kiểm tra Preview Panel real-time ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        String testTitle = "Tiêu đề xem trước";

        printTestInfo("Bước 1: Nhập tiêu đề");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(testTitle);

        waitForPageLoad();

        printTestInfo("Bước 2: Kiểm tra preview cập nhật");
        WebElement previewTitle = driver.findElement(By.id("previewTitle"));
        String previewText = previewTitle.getText();

        boolean isUpdated = previewText.equals(testTitle);

        printTestInfo("✓ Preview title: " + previewText);
        printTestInfo("✓ Preview đã cập nhật: " + isUpdated);

        printTestResult("TC_ADMIN_POST_20", isUpdated);

        assertTrue(isUpdated, "Preview panel phải cập nhật real-time");
    }

    /**
     * TC_ADMIN_POST_21: Kiểm tra tạo bài viết với tiêu đề ngắn (1 ký tự)
     * Boundary test: minimum length
     */
    @Test
    public void TC_ADMIN_POST_21_testAddNewsWithVeryShortTitle() {
        printTestInfo("=== TC_ADMIN_POST_21: Kiểm tra tiêu đề rất ngắn (1 ký tự) ===");

        loginAsAdmin();
        driver.get(baseUrl + "/admin/news/add");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));

        printTestInfo("Bước 1: Nhập tiêu đề 1 ký tự");
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("A");

        WebElement contentField = driver.findElement(By.id("content"));
        contentField.sendKeys("Nội dung test tiêu đề ngắn");

        WebElement categorySelect = driver.findElement(By.id("category"));
        Select select = new Select(categorySelect);
        if (select.getOptions().size() > 1) {
            select.selectByIndex(1);
        }

        WebElement submitButton = driver.findElement(By.id("submit-btn"));
        safeClick(submitButton);

        wait.until(ExpectedConditions.urlContains("/admin/news"));

        boolean success = !driver.getCurrentUrl().contains("/add");
        printTestInfo("✓ Tạo bài viết thành công với tiêu đề ngắn: " + success);

        printTestResult("TC_ADMIN_POST_21", success);

        assertTrue(success, "Phải có thể tạo bài viết với tiêu đề ngắn (1 ký tự)");
    }
}
