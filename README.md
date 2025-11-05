## 🎯 Giới thiệu

**News Portal** là một website tin tức được phát triển bằng **Java Spring Boot** với giao diện hiện đại và tích hợp **kiểm thử tự động bằng Selenium WebDriver**. Dự án này được thiết kế để phục vụ mục đích học tập và nghiên cứu về kiểm thử tự động trong phát triển web.

### ✨ Tính năng chính

- **Người dùng**: Xem tin tức, tìm kiếm, bình luận
- **Quản trị viên**: Đăng bài viết, quản lý bình luận, dashboard
- **Kiểm thử tự động**: Selenium WebDriver cho tất cả chức năng UI

## 🛠️ Công nghệ sử dụng

### Backend
- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Hibernate)
- **MariaDB** (Database)

### Frontend
- **Thymeleaf** (Template Engine)
- **Bootstrap 5.3.0** (UI Framework)
- **Font Awesome** (Icons)
- **Responsive Design**

### Testing
- **Selenium WebDriver 4.15.0**
- **WebDriverManager 5.6.2**
- **JUnit 5**
- **ChromeDriver** (Headless mode)

## 📁 Cấu trúc dự án

```
Project_Selenium/
├── src/
│   ├── main/
│   │   ├── java/edu/iuh/fit/se/project_selenium/
│   │   │   ├── model/           # Entities (User, News, Comment, Category)
│   │   │   ├── repository/      # Data Access Layer
│   │   │   ├── service/         # Business Logic Layer
│   │   │   ├── controller/      # Web Layer
│   │   │   ├── config/          # Configuration Classes
│   │   │   └── ProjectSeleniumApplication.java
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf Templates
│   │       ├── static/          # CSS, JS, Images
│   │       ├── application.properties
│   │       └── data.sql         # Sample Data
│   └── test/
│       └── java/edu/iuh/fit/se/project_selenium/
│           └── selenium/         # Selenium Test Cases
├── pom.xml                      # Maven Dependencies
└── README.md
```

## 🚀 Hướng dẫn cài đặt và chạy

### 1. Yêu cầu hệ thống

- **Java 21** hoặc cao hơn
- **Maven 3.6+**
- **MariaDB 10.6+** hoặc **MySQL 8.0+**
- **Chrome Browser** (cho Selenium testing)

### 2. Cài đặt Database

```sql
-- Tạo database
CREATE DATABASE newsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Tạo user (tùy chọn)
CREATE USER 'newsportal'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON newsportal.* TO 'newsportal'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Cấu hình ứng dụng

Chỉnh sửa file `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/newsportal
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

### 4. Chạy ứng dụng

```bash
# Clone hoặc download dự án
cd Project_Selenium

# Cài đặt dependencies
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại: **http://localhost:8081**

### 5. Tài khoản demo

| Role | Username | Password |
|------|----------|----------|
| **Admin** | `admin` | `123456` |
| **User** | `user` | `123456` |

## 🧪 Chạy kiểm thử Selenium

### ⚠️ QUAN TRỌNG: Trước khi chạy test

1. **Đảm bảo ứng dụng đang chạy** trên port **8081**:
   ```bash
   mvn spring-boot:run
   ```

2. **Kiểm tra database** đã có dữ liệu từ `data.sql`

3. **Kiểm tra Chrome browser** đã được cài đặt

---

### 🤖 SELENIUM TỰ ĐỘNG LÀM GÌ?

**Selenium hoạt động như một ROBOT tự động** trên trình duyệt:

✅ **Tự động mở Chrome browser**  
✅ **Tự động điều hướng** đến các trang web  
✅ **Tự động nhập text** vào các ô input (username, password, v.v.)  
✅ **Tự động click** vào các button/link  
✅ **Tự động submit form** (đăng nhập, đăng ký, v.v.)  
✅ **Tự động kiểm tra kết quả** (xem có thành công hay không)  
✅ **Tự động đóng browser** sau khi xong  

**Ví dụ:** Khi chạy test đăng nhập:
```
1. Selenium mở Chrome → http://localhost:8081/login
2. Tự động nhập username: admin
3. Tự động nhập password: 123456
4. Tự động click nút "Đăng nhập"
5. Kiểm tra xem có nút "Đăng xuất" không → Nếu có = PASS ✅
6. Đóng browser
```

Bạn **không cần làm gì**, chỉ cần chạy test và xem kết quả! 🎉

---

### 🔍 LOGIC CODE CỦA TESTS

#### 📌 **BaseSeleniumTest.java** - Base Configuration
```java
protected String baseUrl = "http://localhost:8081";  // ← Đang cố kết nối đến đây!
```

#### 📌 **application.properties** - Server Config
```properties
server.port=8081  // ← Application chạy trên port 8081
```

#### 💡 **Logic hoạt động:**

**1. Khi bạn chạy `mvn spring-boot:run`:**
```java
✅ Application khởi động
✅ Listens trên port 8081
✅ Sẵn sàng nhận requests từ Selenium
```

**2. Khi bạn chạy `mvn test`:**
```java
✅ Selenium mở Chrome
✅ Cố kết nối đến: http://localhost:8081  // ← baseUrl
```

**3. Nếu application CHƯA chạy:**
```java
❌ Connection refused!
❌ Test FAIL ngay lập tức
❌ Error: "Connection refused to localhost:8081"
```

**4. Nếu application ĐANG chạy:**
```java
✅ Kết nối thành công
✅ Selenium bắt đầu test các chức năng
✅ Mở trang login, nhập text, click button, v.v.
```

#### 🎯 **KẾT LUẬN:**
> **Application PHẢI chạy TRƯỚC để Selenium có thể kết nối và test!**
> 
> Đây là logic đúng ✅ - Test không thể chạy nếu không có app để test!

---

### 📝 HƯỚNG DẪN CHẠY TEST CHI TIẾT

#### 🎯 **Cách 1: Chạy Test trong IDE (IntelliJ IDEA / Eclipse)**

> ⚠️ **NHỚ:** Bạn PHẢI chạy ứng dụng TRƯỚC, sau đó mới chạy test!

**📌 BƯỚC 1: Chạy ứng dụng (Giao diện web)**
```bash
# Mở Terminal trong IDE hoặc Command Prompt
mvn spring-boot:run
```
- Đợi thấy dòng: `Started ProjectSeleniumApplication in X seconds`
- Application chạy tại: **http://localhost:8081**
- **KHÔNG đóng terminal này** (để application tiếp tục chạy)

**📌 BƯỚC 2: Chạy Test (Terminal MỚI hoặc trong IDE)**
- Right-click vào package `selenium`
- Chọn **"Run All Tests"** hoặc **"Run 'Tests in selenium'"**

**📌 BƯỚC 3: Xem kết quả**
- IDE hiển thị: ✅ PASSED hoặc ❌ FAILED
- Log chi tiết trong console

---

#### 🖥️ **Cách 2: Chạy Test từ Command Line**

> ⚠️ **NHỚ:** Cần 2 terminal - 1 để chạy app, 1 để chạy test!

**📌 Terminal 1 - Chạy ứng dụng:**
```bash
cd T:\Selenium\Project_Selenium
mvn spring-boot:run
```
⏸️ **Đợi app chạy xong** → Sẽ thấy: `Started ProjectSeleniumApplication`

**📌 Terminal 2 - Chạy test:**
```bash
cd T:\Selenium\Project_Selenium
mvn test
```

**Kết quả:**
```
[INFO] Running edu.iuh.fit.se.project_selenium.selenium.LoginTest
✅ Selenium WebDriver initialized successfully
🌐 Navigated to login page: http://localhost:8081/login
✅ Successful Login Test - PASSED
[INFO] Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
```

✅ **Success!** Test tự động kiểm tra tất cả chức năng!

---

#### 💡 **TÓM TẮT NHANH:**
```
1️⃣ Terminal 1: mvn spring-boot:run  ← Chạy ứng dụng
   ⬇️ Đợi... Started
   
2️⃣ Terminal 2: mvn test            ← Chạy test
   ⬇️ Đợi... Tests run: X

✅ XONG! Xem kết quả ✅/❌
```

---

#### 🎨 **Cách 3: Chạy Test với Browser hiển thị (Non-Headless)**

**Bước 1:** Mở file `BaseSeleniumTest.java`

**Bước 2:** Comment dòng headless:
```java
// options.addArguments("--headless"); // Comment dòng này
```

**Bước 3:** Save và chạy test lại

**Kết quả:** Browser sẽ hiển thị và bạn có thể xem các thao tác test

---

### 📊 Danh sách Test Cases chi tiết

#### 1️⃣ **LoginTest** - Test đăng nhập
```bash
mvn test -Dtest="LoginTest"
```
- ✅ `testSuccessfulLogin`: Đăng nhập admin thành công
- ✅ `testUserLogin`: Đăng nhập user thường thành công  
- ❌ `testFailedLoginWithWrongPassword`: Login với password sai
- ❌ `testLoginWithEmptyCredentials`: Login với trường trống
- ✅ `testLogout`: Đăng xuất thành công

#### 2️⃣ **RegisterTest** - Test đăng ký (MỚI)
```bash
mvn test -Dtest="RegisterTest"
```
- ✅ `testSuccessfulRegistration`: Đăng ký user mới thành công
- ❌ `testRegistrationWithExistingUsername`: Đăng ký với username đã tồn tại
- ❌ `testRegistrationWithShortPassword`: Password quá ngắn (< 6 ký tự)
- ❌ `testRegistrationWithEmptyFields`: Đăng ký với trường bắt buộc trống

#### 3️⃣ **NavigationTest** - Test điều hướng
```bash
mvn test -Dtest="NavigationTest"
```
- ✅ `testHomePageNavigation`: Kiểm tra trang chủ
- ✅ `testNewsDetailNavigation`: Xem chi tiết tin tức
- ✅ `testLoginPageNavigation`: Điều hướng trang login
- ✅ `testRegisterPageNavigation`: Điều hướng trang register
- ✅ `testSearchResultsNavigation`: Điều hướng trang tìm kiếm
- ✅ `testAdminNavigation`: Admin truy cập dashboard
- ✅ `testBreadcrumbNavigation`: Điều hướng breadcrumb
- ✅ `testResponsiveNavigation`: Test responsive design

#### 4️⃣ **SearchNewsTest** - Test tìm kiếm
```bash
mvn test -Dtest="SearchNewsTest"
```
- ✅ `testSearchWithValidKeyword`: Tìm với từ khóa hợp lệ ("Công nghệ")
- ✅ `testSearchWithEmptyKeyword`: Tìm với từ khóa trống
- ✅ `testSearchWithNonExistentKeyword`: Từ khóa không tồn tại
- ✅ `testSearchWithPartialKeyword`: Từ khóa một phần ("AI")
- ✅ `testSearchFromNewsDetailPage`: Tìm từ trang chi tiết

#### 5️⃣ **CommentTest** - Test bình luận
```bash
mvn test -Dtest="CommentTest"
```
- ✅ `testAddCommentAsLoggedInUser`: Thêm bình luận khi đã login
- ❌ `testAddCommentWithoutLogin`: Chưa login không thể comment
- ❌ `testAddEmptyComment`: Comment trống không được gửi
- ✅ `testViewExistingComments`: Xem các bình luận có sẵn
- ❌ `testCommentCharacterLimit`: Comment quá dài (> 1000 ký tự)

#### 6️⃣ **AddNewsTest** - Test thêm tin tức (Admin)
```bash
mvn test -Dtest="AddNewsTest"
```
- ✅ `testAddNewsAsAdmin`: Admin thêm tin tức thành công
- ❌ `testAddNewsWithoutLogin`: Chưa login không thể thêm tin
- ❌ `testAddNewsAsRegularUser`: User thường không thể thêm tin
- ❌ `testAddNewsWithEmptyFields`: Thêm tin với trường bắt buộc trống
- ✅ `testAddNewsWithInvalidImageUrl`: Thêm tin với URL hình ảnh không hợp lệ
- ✅ `testAdminDashboardAccess`: Truy cập admin dashboard

#### 7️⃣ **NewsManagementTest** - Test quản lý tin tức (MỚI)
```bash
mvn test -Dtest="NewsManagementTest"
```
- ✅ `testNavigateToNewsList`: Admin xem danh sách tin tức
- ❌ `testAccessAdminAsRegularUser`: User thường không thể truy cập admin area

---

### 📈 Xem kết quả chi tiết

#### Console Output
```
✅ Selenium WebDriver initialized successfully
🌐 Navigated to login page: http://localhost:8081/login
ℹ️  Testing successful login with valid credentials
ℹ️  Entered username: admin
ℹ️  Entered password: 123456
ℹ️  Clicked login button
✅ Successful Login Test - PASSED
✅ WebDriver closed successfully
```

#### Test Reports
Sau khi chạy test, xem reports tại:
- **Maven Surefire Reports**: `target/surefire-reports/index.html`
- **JUnit Reports**: `target/site/surefire-report.html`

Mở file bằng browser để xem kết quả chi tiết!

---

### 🔍 Debug Test Cases

#### Nếu test bị fail:

1. **Kiểm tra ứng dụng có đang chạy không:**
   ```bash
   curl http://localhost:8081
   ```

2. **Xem log chi tiết:**
   ```bash
   mvn test -X
   ```

3. **Chạy từng test để tìm lỗi:**
   ```bash
   mvn test -Dtest="LoginTest#testSuccessfulLogin"
   ```

4. **Tắt headless để xem browser:**
   - Mở `BaseSeleniumTest.java`
   - Comment dòng: `// options.addArguments("--headless");`
   - Chạy lại test

#### Common Issues:

❌ **Connection refused**: Ứng dụng chưa chạy → `mvn spring-boot:run`  
❌ **Element not found**: UI đã thay đổi → Cập nhật selector trong test  
❌ **Timeout**: Ứng dụng chậm → Tăng timeout trong test  
❌ **ChromeDriver error**: Chrome chưa cài → Cài Chrome browser

## 📋 Danh sách Test Cases

### 🔐 LoginTest
- ✅ Đăng nhập thành công với admin
- ✅ Đăng nhập thành công với user
- ❌ Đăng nhập sai mật khẩu
- ❌ Đăng nhập với thông tin trống
- ✅ Đăng xuất thành công

### 🔍 SearchNewsTest
- ✅ Tìm kiếm với từ khóa hợp lệ
- ✅ Tìm kiếm với từ khóa rỗng
- ✅ Tìm kiếm với từ khóa không tồn tại
- ✅ Tìm kiếm với từ khóa một phần
- ✅ Tìm kiếm từ trang chi tiết

### 💬 CommentTest
- ✅ Thêm bình luận khi đã đăng nhập
- ❌ Thêm bình luận khi chưa đăng nhập
- ❌ Thêm bình luận rỗng
- ✅ Xem bình luận hiện có
- ❌ Thêm bình luận quá dài

### 📝 AddNewsTest
- ✅ Thêm bài viết với admin
- ❌ Thêm bài viết khi chưa đăng nhập
- ❌ Thêm bài viết với user thường
- ❌ Thêm bài viết với trường bắt buộc trống
- ❌ Thêm bài viết với URL hình ảnh không hợp lệ
- ✅ Truy cập dashboard admin

### 🧭 NavigationTest
- ✅ Điều hướng trang chủ
- ✅ Điều hướng trang chi tiết tin tức
- ✅ Điều hướng trang đăng nhập
- ✅ Điều hướng trang đăng ký
- ✅ Điều hướng trang kết quả tìm kiếm
- ✅ Điều hướng admin
- ✅ Điều hướng breadcrumb
- ✅ Điều hướng responsive

## 🎨 Giao diện

### Trang chủ
- **Header**: Logo, menu điều hướng, đăng nhập/đăng xuất
- **Search Box**: Tìm kiếm tin tức với gradient đẹp mắt
- **News Grid**: Hiển thị tin tức dạng card với hover effects
- **Sidebar**: Tin mới nhất, tin phổ biến, liên kết nhanh
- **Footer**: Thông tin liên hệ và liên kết

### Trang chi tiết tin tức
- **Article**: Tiêu đề, hình ảnh, nội dung, thông tin tác giả
- **Comments Section**: Form bình luận (nếu đã đăng nhập)
- **Share Buttons**: Chia sẻ lên mạng xã hội
- **Related News**: Tin tức liên quan

### Admin Dashboard
- **Statistics Cards**: Thống kê tổng quan
- **Quick Actions**: Thao tác nhanh
- **Latest News Table**: Danh sách bài viết mới nhất
- **Management Links**: Quản lý bài viết, bình luận

## 🔧 Cấu hình Selenium

### WebDriverManager
Dự án sử dụng **WebDriverManager** để tự động quản lý ChromeDriver:

```java
WebDriverManager.chromedriver().setup();
```

### Chrome Options
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");        // Chạy không giao diện
options.addArguments("--no-sandbox");     // Bảo mật
options.addArguments("--disable-dev-shm-usage"); // Hiệu năng
options.addArguments("--window-size=1920,1080"); // Kích thước
```

### Base Test Class
Tất cả test cases kế thừa từ `BaseSeleniumTest`:
- Setup/Teardown WebDriver
- Helper methods cho navigation
- Logging và reporting

## 📊 Kết quả kiểm thử

### Console Output
```
✅ Selenium WebDriver initialized successfully
🌐 Navigated to homepage: http://localhost:8080
ℹ️  Testing successful login with valid credentials
ℹ️  Entered username: admin
ℹ️  Entered password: 123456
ℹ️  Clicked login button
✅ Successful Login Test - PASSED
✅ WebDriver closed successfully
```

### Test Reports
- **Maven Surefire Reports**: `target/surefire-reports/`
- **JUnit Reports**: `target/site/surefire-report.html`

## 🐛 Troubleshooting

### Lỗi thường gặp

1. **Database Connection Error**
   ```
   Solution: Kiểm tra MariaDB đang chạy và cấu hình trong application.properties
   ```

2. **ChromeDriver Not Found**
   ```
   Solution: WebDriverManager sẽ tự động download, kiểm tra internet connection
   ```

3. **Port 8080 Already in Use**
   ```
   Solution: Thay đổi port trong application.properties hoặc kill process đang dùng port
   ```

4. **Test Timeout**
   ```
   Solution: Tăng timeout trong WebDriverWait hoặc kiểm tra ứng dụng có chạy không
   ```

### Debug Mode

Để debug Selenium tests:

```java
// Thêm vào BaseSeleniumTest
options.addArguments("--remote-debugging-port=9222");
// Không chạy headless để xem browser
```

## 📈 Mở rộng dự án

### Thêm Test Cases mới
1. Tạo class mới kế thừa `BaseSeleniumTest`
2. Implement các test methods với `@Test`
3. Sử dụng helper methods có sẵn

### Thêm chức năng mới
1. Tạo Model → Repository → Service → Controller
2. Tạo Template Thymeleaf
3. Viết Selenium tests tương ứng

### CI/CD Integration
```yaml
# GitHub Actions example
- name: Run Selenium Tests
  run: mvn test -Dtest="*Selenium*"
```

## 📚 Tài liệu tham khảo

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Selenium WebDriver](https://selenium.dev/documentation/)
- [Thymeleaf](https://www.thymeleaf.org/documentation.html)
- [Bootstrap](https://getbootstrap.com/docs/5.3/)
- [WebDriverManager](https://bonigarcia.dev/webdrivermanager/)

## 👥 Đóng góp

1. Fork dự án
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu tại **IUH FIT SE**.

---

**🎓 Dự án News Portal - Kiểm thử Selenium**  
*Phát triển bởi: IUH FIT SE Students*  
*Công nghệ: Spring Boot + Selenium WebDriver*
