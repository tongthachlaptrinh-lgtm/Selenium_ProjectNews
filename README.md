# 📰 News Portal - Dự án Website Tin tức với Selenium Testing

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

Ứng dụng sẽ chạy tại: **http://localhost:8080**

### 5. Tài khoản demo

| Role | Username | Password |
|------|----------|----------|
| **Admin** | `admin` | `123456` |
| **User** | `user` | `123456` |

## 🧪 Chạy kiểm thử Selenium

### 1. Chạy tất cả test cases

```bash
# Chạy tất cả Selenium tests
mvn test

# Chạy với verbose output
mvn test -Dtest="*Selenium*" -X
```

### 2. Chạy từng test class riêng lẻ

```bash
# Test đăng nhập
mvn test -Dtest="LoginTest"

# Test tìm kiếm
mvn test -Dtest="SearchNewsTest"

# Test bình luận
mvn test -Dtest="CommentTest"

# Test thêm bài viết
mvn test -Dtest="AddNewsTest"

# Test điều hướng
mvn test -Dtest="NavigationTest"
```

### 3. Chạy với Chrome hiển thị (không headless)

Để xem browser khi chạy test, chỉnh sửa file `BaseSeleniumTest.java`:

```java
// Comment dòng này để không chạy headless
// options.addArguments("--headless");
```

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
