package edu.iuh.fit.se.project_selenium.selenium;

/**
 * Test Suite để chạy tất cả các Selenium Tests
 * 
 * HƯỚNG DẪN SỬ DỤNG:
 * 
 * 1. Chạy tất cả tests trong IDE:
 *    - Right-click vào package selenium
 *    - Chọn "Run All Tests"
 * 
 * 2. Chạy từ command line:
 *    mvn test
 * 
 * 3. Chạy từng test class riêng lẻ:
 *    - LoginTest: Test chức năng đăng nhập
 *    - RegisterTest: Test chức năng đăng ký
 *    - NavigationTest: Test điều hướng
 *    - SearchNewsTest: Test tìm kiếm tin tức
 *    - CommentTest: Test bình luận
 *    - AddNewsTest: Test thêm tin tức (admin)
 *    - NewsManagementTest: Test quản lý tin tức
 * 
 * DANH SÁCH TEST CASES:
 * 
 * 1. LoginTest
 *    - testSuccessfulLogin: Đăng nhập thành công
 *    - testFailedLoginWithWrongPassword: Đăng nhập với password sai
 *    - testLoginWithEmptyCredentials: Đăng nhập với trường trống
 *    - testUserLogin: Đăng nhập user thường
 *    - testLogout: Đăng xuất
 * 
 * 2. RegisterTest
 *    - testSuccessfulRegistration: Đăng ký thành công
 *    - testRegistrationWithExistingUsername: Đăng ký với username đã tồn tại
 *    - testRegistrationWithShortPassword: Password quá ngắn
 *    - testRegistrationWithEmptyFields: Trường bắt buộc để trống
 * 
 * 3. NavigationTest
 *    - testHomePageNavigation: Điều hướng trang chủ
 *    - testNewsDetailNavigation: Xem chi tiết tin tức
 *    - testLoginPageNavigation: Điều hướng trang đăng nhập
 *    - testRegisterPageNavigation: Điều hướng trang đăng ký
 *    - testSearchResultsNavigation: Kết quả tìm kiếm
 *    - testAdminNavigation: Điều hướng admin
 *    - testBreadcrumbNavigation: Breadcrumb
 *    - testResponsiveNavigation: Responsive
 * 
 * 4. SearchNewsTest
 *    - testSearchWithValidKeyword: Tìm kiếm với từ khóa hợp lệ
 *    - testSearchWithEmptyKeyword: Tìm kiếm với từ khóa trống
 *    - testSearchWithNonExistentKeyword: Từ khóa không tồn tại
 *    - testSearchWithPartialKeyword: Từ khóa một phần
 *    - testSearchFromNewsDetailPage: Tìm kiếm từ trang chi tiết
 * 
 * 5. CommentTest
 *    - testAddCommentAsLoggedInUser: Thêm bình luận (đã đăng nhập)
 *    - testAddCommentWithoutLogin: Thêm bình luận (chưa đăng nhập)
 *    - testAddEmptyComment: Bình luận trống
 *    - testViewExistingComments: Xem bình luận có sẵn
 *    - testCommentCharacterLimit: Giới hạn ký tự
 * 
 * 6. AddNewsTest
 *    - testAddNewsAsAdmin: Admin thêm tin tức
 *    - testAddNewsWithoutLogin: Chưa đăng nhập
 *    - testAddNewsAsRegularUser: User thường
 *    - testAddNewsWithEmptyFields: Trường trống
 *    - testAddNewsWithInvalidImageUrl: URL hình ảnh không hợp lệ
 *    - testAdminDashboardAccess: Truy cập dashboard
 * 
 * 7. NewsManagementTest
 *    - testNavigateToNewsList: Điều hướng danh sách tin tức
 *    - testAccessAdminAsRegularUser: User thường truy cập admin
 * 
 * LƯU Ý:
 * - Đảm bảo application đang chạy trên http://localhost:8081
 * - Database đã được khởi tạo với data.sql
 * - Chrome browser đã được cài đặt
 */
public class AllSeleniumTests {
    // This is a documentation file for test suite
    // Run all tests by selecting "selenium" package and clicking "Run All Tests"
}

