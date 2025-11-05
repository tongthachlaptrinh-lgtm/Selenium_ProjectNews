# Hướng Dẫn Test Case TC_COMMENT_01 - Chức Năng Bình Luận

## 📋 Thông Tin Test Case

**Mã Test Case:** TC_COMMENT_01  
**Tên:** Kiểm tra người dùng (đã đăng nhập) có thể đăng một bình luận mới vào bài viết  
**Mục đích:** Đảm bảo người dùng đã đăng nhập có thể thêm bình luận vào bài viết thành công

---

## 🎯 Các Bước Thực Hiện

### Bước 1: Đăng nhập vào tài khoản
- Mở trang đăng nhập
- Nhập username: `user`
- Nhập password: `123456`
- Nhấn nút đăng nhập

### Bước 2: Từ trang chủ, nhấp vào một bài viết bất kỳ
- Vào trang chủ
- Chọn bài viết đầu tiên trong danh sách

### Bước 3: Kéo xuống phần bình luận
- Chờ trang chi tiết bài viết tải xong
- Scroll xuống phần bình luận

### Bước 4: Nhập nội dung bình luận
- Tìm ô nhập bình luận
- Nhập nội dung: `"Bài viết này rất hữu ích!"`

### Bước 5: Nhấp vào nút 'Gửi bình luận'
- Click nút "Gửi bình luận"

---

## ✅ Kết Quả Mong Đợi

1. ✔️ Trang web tải lại (redirect về trang chi tiết bài viết)
2. ✔️ Hiển thị thông báo thành công: "Bình luận đã được gửi thành công!"
3. ✔️ Bình luận `"Bài viết này rất hữu ích!"` xuất hiện trong danh sách bình luận
4. ✔️ Bình luận hiển thị kèm theo tên người dùng `"user"`
5. ✔️ Hiển thị thời gian đăng bình luận

---

## 🚀 Cách Chạy Test

### Cách 1: Chạy từ IDE (IntelliJ IDEA / Eclipse)

1. **Mở file test:**
   ```
   src/test/java/edu/iuh/fit/se/project_selenium/selenium/CommentTest.java
   ```

2. **Chạy test TC_COMMENT_01:**
   - Click chuột phải vào method `TC_COMMENT_01_testAddCommentAsLoggedInUser()`
   - Chọn "Run 'TC_COMMENT_01_testAddCommentAsLoggedInUser()'"

3. **Chạy tất cả test trong CommentTest:**
   - Click chuột phải vào class `CommentTest`
   - Chọn "Run 'CommentTest'"

### Cách 2: Chạy từ Command Line (Maven)

1. **Mở Terminal/Command Prompt:**
   ```bash
   cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
   ```

2. **Chạy RIÊNG test TC_COMMENT_01:**
   ```bash
   mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
   ```

3. **Chạy TẤT CẢ test trong CommentTest:**
   ```bash
   mvn test -Dtest=CommentTest
   ```

4. **Chạy TẤT CẢ test Selenium:**
   ```bash
   mvn test -Dtest=AllSeleniumTests
   ```

### Cách 3: Sử dụng file .bat (Windows)

1. **Tạo file `run-comment-test.bat`:**
   ```batch
   @echo off
   echo ========================================
   echo Running TC_COMMENT_01 - Comment Test
   echo ========================================
   cd /d D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
   call mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
   pause
   ```

2. **Chạy file .bat:**
   - Double-click file `run-comment-test.bat`

---

## 🔧 Yêu Cầu Trước Khi Chạy Test

### 1. Khởi động ứng dụng
```bash
# Mở Terminal 1
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn spring-boot:run
```

**Hoặc chạy từ IDE:**
- Mở `ProjectSeleniumApplication.java`
- Click chuột phải → Run

**Đảm bảo ứng dụng chạy ở:** `http://localhost:8081`

### 2. Kiểm tra database
- Database phải có sẵn dữ liệu từ `data.sql`
- Có tài khoản `user/123456`
- Có ít nhất 1 bài viết để test

### 3. Kiểm tra ChromeDriver
- WebDriverManager tự động tải ChromeDriver
- Đảm bảo có kết nối Internet lần đầu chạy

---

## 📊 Kết Quả Test

Khi chạy test thành công, bạn sẽ thấy output như sau:

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

---

## 🐛 Xử Lý Lỗi Thường Gặp

### Lỗi 1: Cannot connect to localhost:8081
**Nguyên nhân:** Ứng dụng chưa được khởi động  
**Giải pháp:**
```bash
mvn spring-boot:run
```

### Lỗi 2: Element not found
**Nguyên nhân:** Trang tải chậm hoặc selector sai  
**Giải pháp:**
- Kiểm tra ứng dụng có đang chạy không
- Tăng thời gian timeout trong `BaseSeleniumTest.java`

### Lỗi 3: User not found
**Nguyên nhân:** Database chưa có dữ liệu  
**Giải pháp:**
- Chạy lại ứng dụng để load `data.sql`
- Hoặc chạy script `database-setup.sql`

### Lỗi 4: ChromeDriver version mismatch
**Nguyên nhân:** WebDriverManager chưa tải đúng version  
**Giải pháp:**
- Xóa cache: `~/.cache/selenium` (Linux/Mac) hoặc `%USERPROFILE%\.cache\selenium` (Windows)
- Chạy lại test

---

## 📝 Ghi Chú

- Test sẽ tự động đóng browser sau 10 giây để dễ quan sát kết quả
- Có thể tắt chế độ headless để xem browser chạy tự động
- Test này kiểm tra cả validation và UI/UX

---

## 📂 Các File Liên Quan

### Backend:
- `CommentController.java` - Controller xử lý bình luận
- `CommentService.java` - Service logic bình luận
- `Comment.java` - Model bình luận
- `CommentRepository.java` - Repository truy vấn DB

### Frontend:
- `news-detail.html` - Giao diện chi tiết bài viết và form bình luận

### Test:
- `CommentTest.java` - File test Selenium
- `BaseSeleniumTest.java` - Base class cho tất cả test

### Database:
- `data.sql` - Dữ liệu mẫu ban đầu

---

## ✨ Các Test Case Khác Trong CommentTest

1. `TC_COMMENT_01_testAddCommentAsLoggedInUser()` - ✅ Test chính
2. `testAddCommentWithoutLogin()` - Kiểm tra user chưa đăng nhập
3. `testAddEmptyComment()` - Kiểm tra validation bình luận rỗng
4. `testViewExistingComments()` - Kiểm tra xem danh sách bình luận

---

## 🎓 Demo Cho Giảng Viên

Test được thiết kế để:
- ✅ Chạy tự động hoàn toàn
- ✅ Hiển thị từng bước thực hiện rõ ràng
- ✅ Đợi 10 giây trước khi đóng browser để demo
- ✅ In ra console đầy đủ thông tin
- ✅ Assert đầy đủ các điều kiện

---

**Tác giả:** Nhóm Đề Tài Selenium  
**Ngày tạo:** 2025-11-05  
**Phiên bản:** 1.0

