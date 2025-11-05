# 📖 HƯỚNG DẪN CHẠY TEST - TC_COMMENT_01

## 🎯 Tổng Quan

Dự án đã được hoàn thiện với:
- ✅ **Chức năng bình luận** hoàn chỉnh (backend + frontend)
- ✅ **Test case TC_COMMENT_01** theo đúng yêu cầu
- ✅ **Giao diện** đẹp và responsive
- ✅ **Validation** đầy đủ

---

## 🚀 CÁCH CHẠY TEST (3 CÁCH)

### 📌 CÁCH 1: Sử dụng File .BAT (ĐƠN GIẢN NHẤT - KHUYẾN NGHỊ)

#### Bước 1: Khởi động ứng dụng
```bash
# Mở Terminal/Command Prompt
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn spring-boot:run
```

**Chờ đến khi thấy dòng:**
```
Started ProjectSeleniumApplication in X.XXX seconds
```

#### Bước 2: Chạy test
**MỞ TERMINAL MỚI** và chạy 1 trong 2 file .bat:

**A. Chạy riêng TC_COMMENT_01:**
```bash
# Double-click hoặc chạy:
run-comment-test.bat
```

**B. Chạy tất cả test bình luận:**
```bash
# Double-click hoặc chạy:
run-all-comment-tests.bat
```

---

### 📌 CÁCH 2: Sử dụng Maven Command Line

#### Bước 1: Khởi động ứng dụng
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn spring-boot:run
```

#### Bước 2: Mở terminal mới và chạy test

**Chạy riêng TC_COMMENT_01:**
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
```

**Chạy tất cả test trong CommentTest:**
```bash
mvn test -Dtest=CommentTest
```

**Chạy tất cả test Selenium:**
```bash
mvn test
```

---

### 📌 CÁCH 3: Chạy từ IDE (IntelliJ IDEA / Eclipse)

#### Bước 1: Khởi động ứng dụng
1. Mở file: `src/main/java/edu/iuh/fit/se/project_selenium/ProjectSeleniumApplication.java`
2. Click chuột phải → **Run 'ProjectSeleniumApplication'**
3. Chờ ứng dụng khởi động xong

#### Bước 2: Chạy test
1. Mở file: `src/test/java/edu/iuh/fit/se/project_selenium/selenium/CommentTest.java`
2. Tìm method: `TC_COMMENT_01_testAddCommentAsLoggedInUser()`
3. Click chuột phải vào method → **Run 'TC_COMMENT_01...'**

---

## 📋 CHI TIẾT TEST CASE TC_COMMENT_01

### Mô tả
**Kiểm tra người dùng (đã đăng nhập) có thể đăng một bình luận mới vào bài viết**

### Các bước thực hiện:
1. ✅ Đăng nhập vào tài khoản 'user' / '123456'
2. ✅ Từ trang chủ, nhấp vào một bài viết bất kỳ
3. ✅ Kéo xuống phần bình luận
4. ✅ Nhập nội dung: "Bài viết này rất hữu ích!"
5. ✅ Nhấp vào nút 'Gửi bình luận'

### Kết quả mong đợi:
- ✅ Trang web tải lại (redirect)
- ✅ Hiển thị thông báo: "Bình luận đã được gửi thành công!"
- ✅ Bình luận xuất hiện trong danh sách
- ✅ Bình luận kèm tên người dùng 'user'
- ✅ Hiển thị thời gian đăng bình luận

---

## 🎬 KẾT QUẢ DEMO

Khi chạy test thành công, bạn sẽ thấy:

```
✅ Selenium WebDriver initialized successfully
🌐 Navigated to login page: http://localhost:8081/login
ℹ️  Logged in as: user
ℹ️  === TC_COMMENT_01: Kiểm tra người dùng đã đăng nhập có thể đăng bình luận ===
ℹ️  Bước 1: Đăng nhập vào tài khoản 'user'
ℹ️  Bước 2: Từ trang chủ, nhấp vào bài viết đầu tiên
ℹ️  ✓ Đã nhấp vào bài viết
ℹ️  Bước 3: Chờ phần bình luận tải xong
ℹ️  ✓ Đã kéo xuống phần bình luận
ℹ️  Bước 4: Nhập nội dung bình luận 'Bài viết này rất hữu ích!'
ℹ️  ✓ Đã nhập: 'Bài viết này rất hữu ích!'
ℹ️  Bước 5: Nhấp vào nút 'Gửi bình luận'
ℹ️  ✓ Đã nhấp nút 'Gửi bình luận'
ℹ️  Kiểm tra kết quả...
ℹ️  ✓ Thông báo thành công: Bình luận đã được gửi thành công!
ℹ️  ✓ Bình luận xuất hiện trong danh sách: true
ℹ️  ✓ Bình luận hiển thị kèm tên 'user': true
✅ TC_COMMENT_01 - PASSED
✅ Tất cả điều kiện đã thỏa mãn:
   - Trang đã tải lại
   - Bình luận 'Bài viết này rất hữu ích!' xuất hiện trong danh sách
   - Bình luận kèm theo tên 'user'
⏱️  Browser sẽ đóng sau 10 giây...
✅ WebDriver closed successfully
```

**Browser Chrome sẽ tự động mở và thực hiện các bước test!** 🎉

---

## 🛠️ CẤU TRÚC DỰ ÁN ĐÃ BỔ SUNG

### Backend (Đã hoàn thiện)
```
src/main/java/.../controller/
  └─ CommentController.java      ✅ Xử lý POST /news/{id}/comment

src/main/java/.../service/
  └─ CommentService.java          ✅ Logic lưu và lấy bình luận

src/main/java/.../model/
  └─ Comment.java                 ✅ Entity bình luận

src/main/java/.../repository/
  └─ CommentRepository.java       ✅ Query database
```

### Frontend (Đã hoàn thiện)
```
src/main/resources/templates/
  └─ news-detail.html             ✅ Form bình luận với ID rõ ràng
                                  ✅ Hiển thị danh sách bình luận
                                  ✅ Thông báo cho user chưa đăng nhập
```

### Test (Đã hoàn thiện)
```
src/test/java/.../selenium/
  └─ CommentTest.java             ✅ TC_COMMENT_01 và các test khác
  └─ BaseSeleniumTest.java        ✅ Base class với helper methods
```

### Scripts & Documentation
```
run-comment-test.bat              ✅ Chạy TC_COMMENT_01
run-all-comment-tests.bat         ✅ Chạy tất cả test bình luận
TC_COMMENT_01_GUIDE.md            ✅ Hướng dẫn chi tiết
HUONG_DAN_CHAY_TEST.md            ✅ Hướng dẫn tổng hợp (file này)
```

---

## 🔍 KIỂM TRA DATABASE

Sau khi chạy test, bạn có thể kiểm tra database để xem bình luận đã được lưu:

```sql
-- Xem tất cả bình luận
SELECT c.id, c.content, c.created_at, u.username, n.title 
FROM comments c
JOIN users u ON c.user_id = u.id
JOIN news n ON c.news_id = n.id
ORDER BY c.created_at DESC;

-- Xem bình luận của user 'user'
SELECT * FROM comments c
JOIN users u ON c.user_id = u.id
WHERE u.username = 'user'
ORDER BY c.created_at DESC;
```

---

## ⚙️ THÔNG SỐ KỸ THUẬT

### Hệ thống test
- **Framework:** Selenium WebDriver + JUnit 5
- **WebDriverManager:** Tự động quản lý ChromeDriver
- **Browser:** Chrome (tự động mở)
- **Timeout:** 10 giây cho mỗi thao tác
- **Demo time:** 10 giây trước khi đóng browser

### Backend
- **Framework:** Spring Boot 3.5.7
- **Database:** H2 (in-memory) hoặc MySQL
- **Security:** Spring Security
- **View Engine:** Thymeleaf

### API Endpoint
```
POST /news/{newsId}/comment
Parameters:
  - content: String (required, max 1000 chars)
Authentication: Required (Spring Security)
Response: Redirect to /news/{newsId}
Flash Message: "Bình luận đã được gửi thành công!"
```

---

## 🐛 XỬ LÝ LỖI THƯỜNG GẶP

### ❌ Lỗi: "Connection refused to localhost:8081"
**Nguyên nhân:** Ứng dụng chưa khởi động  
**Giải pháp:**
```bash
mvn spring-boot:run
```

### ❌ Lỗi: "Element not found: comment-content"
**Nguyên nhân:** 
- Trang tải chậm
- User chưa đăng nhập (không hiển thị form)

**Giải pháp:**
- Kiểm tra xem ứng dụng có chạy ổn định không
- Kiểm tra login có thành công không

### ❌ Lỗi: "ChromeDriver not found"
**Nguyên nhân:** WebDriverManager chưa tải driver

**Giải pháp:**
- Đảm bảo có Internet
- Xóa cache: `%USERPROFILE%\.cache\selenium`
- Chạy lại test

### ❌ Lỗi: "java.lang.NoSuchMethodError"
**Nguyên nhân:** Conflict dependency

**Giải pháp:**
```bash
mvn clean install
```

---

## 📦 CÀI ĐẶT DỰ ÁN (Lần Đầu)

Nếu đây là lần đầu chạy dự án:

```bash
# 1. Clone hoặc mở project
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews

# 2. Cài đặt dependencies
mvn clean install

# 3. Chạy ứng dụng
mvn spring-boot:run

# 4. Mở terminal mới và chạy test
mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
```

---

## 📞 HỖ TRỢ

Nếu gặp vấn đề:

1. **Kiểm tra log console** - Xem thông báo lỗi chi tiết
2. **Kiểm tra port 8081** - Đảm bảo không bị chiếm dụng
3. **Xem file log** - Nếu có trong `logs/` folder
4. **Liên hệ team** - Báo lỗi với thông tin chi tiết

---

## 🎓 CHO GIẢNG VIÊN

Test case này được thiết kế để:

✅ **Dễ chạy:** Chỉ cần double-click file .bat  
✅ **Dễ quan sát:** Browser mở 10 giây để xem  
✅ **Dễ hiểu:** Console log từng bước rõ ràng  
✅ **Đầy đủ:** Kiểm tra tất cả điều kiện  
✅ **Chuyên nghiệp:** Code sạch, có documentation  

---

## 📄 TÀI LIỆU THAM KHẢO

- `TC_COMMENT_01_GUIDE.md` - Hướng dẫn chi tiết test case
- `README.md` - Tổng quan dự án
- `database-setup.sql` - Script setup database
- `data.sql` - Dữ liệu mẫu

---

## ✅ CHECKLIST TRƯỚC KHI DEMO

- [ ] Ứng dụng đã khởi động ở port 8081
- [ ] Database có dữ liệu (user/123456 tồn tại)
- [ ] Chrome browser đã cài đặt
- [ ] Internet available (lần đầu chạy)
- [ ] File .bat đã tạo và có quyền thực thi

---

**Chúc bạn demo thành công!** 🎉🚀

**Nhóm Đề Tài Selenium**  
**Ngày cập nhật:** 2025-11-05

