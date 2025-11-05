# ⚡ HƯỚNG DẪN NHANH - CHẠY TEST TC_COMMENT_01

## 🎯 Để chạy test TC_COMMENT_01, làm theo 2 bước:

### BƯỚC 1: Khởi động ứng dụng
Mở Terminal/CMD và chạy:
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn spring-boot:run
```
**Chờ đến khi thấy:** `Started ProjectSeleniumApplication`

### BƯỚC 2: Chạy test
**Mở Terminal/CMD MỚI** và chạy:

**Cách 1 - Dùng file .bat (NHANH NHẤT):**
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
run-comment-test.bat
```

**Cách 2 - Dùng Maven:**
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser
```

## ✅ Kết quả
- Browser Chrome sẽ tự động mở
- Test tự động thực hiện: login → chọn bài viết → nhập bình luận → gửi
- Xem kết quả trên console
- Browser tự động đóng sau 10 giây

## 📖 Chi tiết
Xem file: `HUONG_DAN_CHAY_TEST.md` hoặc `TC_COMMENT_01_GUIDE.md`

