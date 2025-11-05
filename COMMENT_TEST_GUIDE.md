# HƯỚNG DẪN TEST CASE COMMENT

## Tổng quan
File CommentTest.java hiện có **15 test cases** đầy đủ để kiểm tra chức năng bình luận.

## Danh sách Test Cases

### 1. TC_COMMENT_01: Test đăng bình luận khi đã đăng nhập
- **Mục đích**: Kiểm tra người dùng đã đăng nhập có thể thêm bình luận
- **Các bước**:
  1. Đăng nhập tài khoản 'user'
  2. Truy cập một bài viết
  3. Kéo xuống phần bình luận
  4. Nhập nội dung bình luận
  5. Nhấn nút gửi
- **Kết quả mong đợi**: Bình luận xuất hiện trong danh sách kèm tên user

### 2. TC_COMMENT_02: Test không đăng nhập
- **Mục đích**: Kiểm tra người dùng chưa đăng nhập không thể bình luận
- **Kết quả mong đợi**: Hiển thị thông báo "Đăng nhập để bình luận"

### 3. TC_COMMENT_03: Test bình luận rỗng
- **Mục đích**: Kiểm tra validation không cho phép gửi bình luận rỗng
- **Kết quả mong đợi**: Vẫn ở lại trang, không gửi được

### 4. TC_COMMENT_04: Test xem danh sách bình luận
- **Mục đích**: Kiểm tra hiển thị danh sách bình luận hiện có
- **Kết quả mong đợi**: Hiển thị danh sách hoặc thông báo chưa có bình luận

### 5. TC_COMMENT_05: Test bình luận dài
- **Mục đích**: Kiểm tra xử lý bình luận dài (500+ ký tự)
- **Kết quả mong đợi**: Bình luận dài được thêm thành công

### 6. TC_COMMENT_06: Test ký tự đặc biệt
- **Mục đích**: Kiểm tra bình luận với ký tự đặc biệt @#$%^&*()...
- **Kết quả mong đợi**: Ký tự đặc biệt được hiển thị đúng

### 7. TC_COMMENT_07: Test tiếng Việt có dấu
- **Mục đích**: Kiểm tra encoding tiếng Việt
- **Kết quả mong đợi**: Tiếng Việt hiển thị đúng dấu

### 8. TC_COMMENT_08: Test bình luận liên tiếp
- **Mục đích**: Kiểm tra đăng nhiều bình luận liên tiếp (3 lần)
- **Kết quả mong đợi**: Tất cả bình luận được thêm thành công

### 9. TC_COMMENT_09: Test khoảng trắng
- **Mục đích**: Kiểm tra validation với bình luận chỉ có khoảng trắng
- **Kết quả mong đợi**: Không cho phép gửi

### 10. TC_COMMENT_10: Test bảo mật XSS
- **Mục đích**: Kiểm tra bảo mật chống XSS injection
- **Input**: `<script>alert('XSS')</script>`
- **Kết quả mong đợi**: Script được escape, không thực thi

### 11. TC_COMMENT_11: Test HTML injection
- **Mục đích**: Kiểm tra bảo mật chống HTML injection
- **Input**: `<b>Bold</b> <i>Italic</i>`
- **Kết quả mong đợi**: HTML tags được escape

### 12. TC_COMMENT_12: Test đếm số lượng bình luận
- **Mục đích**: Kiểm tra hiển thị số lượng bình luận
- **Kết quả mong đợi**: Số lượng >= 0

### 13. TC_COMMENT_13: Test thông tin người bình luận
- **Mục đích**: Kiểm tra hiển thị tên người bình luận
- **Kết quả mong đợi**: Bình luận hiển thị kèm tên user

### 14. TC_COMMENT_14: Test timestamp
- **Mục đích**: Kiểm tra hiển thị thời gian bình luận
- **Kết quả mong đợi**: Có hiển thị thời gian (giây/phút/giờ trước hoặc dd/mm/yyyy)

### 15. TC_COMMENT_15: Test form reset
- **Mục đích**: Kiểm tra form được reset sau khi gửi thành công
- **Kết quả mong đợi**: Textarea trống sau khi gửi

## Cách chạy test

### Chạy tất cả test cases:
```bash
run-all-comment-tests.bat
```

Hoặc:
```bash
mvn clean test -Dtest=CommentTest
```

### Chạy một test case cụ thể:
```bash
mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
```

### Chạy nhóm test cases:
```bash
# Chạy test từ 01 đến 05
mvn test -Dtest=CommentTest#TC_COMMENT_01*,TC_COMMENT_02*,TC_COMMENT_03*,TC_COMMENT_04*,TC_COMMENT_05*
```

## Yêu cầu trước khi chạy test

1. ✅ Database đã được setup và có dữ liệu
2. ✅ Application đang chạy ở http://localhost:8080
3. ✅ Tài khoản test 'user' / '123456' tồn tại
4. ✅ Có ít nhất 1 bài viết trong database
5. ✅ Chrome browser đã được cài đặt

## Kiểm tra kết quả

Sau khi chạy test, kiểm tra:
- Console output: Hiển thị kết quả từng test case
- File report: `target/surefire-reports/CommentTest.txt`
- HTML report: `target/surefire-reports/TEST-CommentTest.xml`

## Các test case cover

✅ **Chức năng cơ bản**:
- Thêm bình luận
- Xem bình luận
- Đăng nhập/không đăng nhập

✅ **Validation**:
- Bình luận rỗng
- Bình luận chỉ có khoảng trắng
- Bình luận dài

✅ **Bảo mật**:
- XSS injection
- HTML injection
- Script injection

✅ **Định dạng dữ liệu**:
- Ký tự đặc biệt
- Tiếng Việt có dấu
- Bình luận liên tiếp

✅ **Giao diện**:
- Hiển thị thông tin user
- Hiển thị timestamp
- Form reset sau submit
- Đếm số lượng bình luận

## Lưu ý

- Mỗi test case chạy độc lập với browser mới
- Test cases sử dụng dữ liệu động (timestamp) để tránh trùng lặp
- Nếu test fail, kiểm tra console log để xem chi tiết lỗi
- Đảm bảo không có test case nào khác đang chạy đồng thời

## Troubleshooting

### Lỗi: Element not found
→ Kiểm tra application có đang chạy không
→ Kiểm tra database có dữ liệu không

### Lỗi: Login failed
→ Kiểm tra tài khoản 'user'/'123456' tồn tại trong database

### Lỗi: Comment not submitted
→ Kiểm tra form bình luận có element id đúng không:
  - comment-content (textarea)
  - submit-comment-btn (button)
  - comments-section (section)
  - comments-list (div/ul)

## Báo cáo Test Coverage

- **Total Test Cases**: 15
- **Positive Tests**: 10 (test các trường hợp thành công)
- **Negative Tests**: 5 (test các trường hợp fail)
- **Security Tests**: 2 (XSS, HTML injection)
- **UI Tests**: 4 (hiển thị thông tin, timestamp, form reset, count)

---
**Cập nhật**: 2025-01-05
**Phiên bản**: 1.0
**Tác giả**: Selenium Test Team

