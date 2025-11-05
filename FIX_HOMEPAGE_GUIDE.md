# 🔧 SỬA LỖI TRANG CHỦ KHÔNG HIỂN THỊ BÀI VIẾT

## ❌ Vấn đề
Trang chủ không hiển thị bài viết, chỉ thấy thông báo "Chưa có tin tức nào"

## ✅ Nguyên nhân
1. Database chưa có dữ liệu
2. Dữ liệu không được load đúng thứ tự
3. Query không trả về kết quả

## 🚀 GIẢI PHÁP (Chọn 1 trong 3 cách)

### **CÁCH 1: Tự động sửa (NHANH NHẤT - KHUYẾN NGHỊ)**

Chạy file bat tự động:
```bash
fix-homepage.bat
```

Script sẽ tự động:
- ✅ Kiểm tra kết nối database
- ✅ Load dữ liệu mẫu
- ✅ Khởi động ứng dụng
- ✅ Mở browser

---

### **CÁCH 2: Kiểm tra và sửa thủ công**

#### Bước 1: Kiểm tra database
```bash
# Kết nối MySQL
mysql -u root -proot

# Chọn database
USE newsportal;

# Kiểm tra số lượng tin tức
SELECT COUNT(*) as total_news FROM news;
SELECT COUNT(*) as published_news FROM news WHERE is_published = true;

# Xem danh sách tin tức
SELECT id, title, is_published FROM news;
```

#### Bước 2: Nếu không có dữ liệu, load lại
```bash
# Thoát MySQL (gõ: exit)

# Load dữ liệu mới
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mysql -u root -proot newsportal < src/main/resources/data.sql

# Kiểm tra lại
mysql -u root -proot newsportal -e "SELECT COUNT(*) FROM news WHERE is_published = true;"
```

Kết quả phải > 0 (ví dụ: 5 bài viết)

#### Bước 3: Khởi động lại ứng dụng
```bash
mvn clean spring-boot:run
```

Xem log phải thấy:
```
Hibernate: INSERT INTO news ...
Hibernate: SELECT n1_0.id, n1_0.title ... FROM news n1_0 WHERE n1_0.is_published=1
```

#### Bước 4: Kiểm tra trên browser
```
http://localhost:8081
```

Phải thấy các bài viết hiển thị!

---

### **CÁCH 3: Reset toàn bộ database**

```bash
# 1. Drop database cũ
mysql -u root -proot -e "DROP DATABASE IF EXISTS newsportal;"

# 2. Tạo database mới
mysql -u root -proot -e "CREATE DATABASE newsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 3. Chạy lại ứng dụng (sẽ tự động tạo tables và load data)
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mvn clean spring-boot:run
```

---

## 🔍 KIỂM TRA KẾT QUẢ

### Trên console/log, phải thấy:
```
Hibernate: select ... from news n1_0 where n1_0.is_published=? order by n1_0.created_at desc
```

### Trên browser, phải thấy:
- ✅ Danh sách bài viết (cards với hình ảnh)
- ✅ Tiêu đề, tóm tắt, tác giả, ngày đăng
- ✅ Nút "Đọc tiếp"
- ✅ Sidebar với tin mới nhất

### Nếu thấy debug info:
```
Debug: newsPage.content.size = 5
Debug: latestNews.size = 5
```
→ Có nghĩa là dữ liệu đã được load!

---

## 🐛 NẾU VẪN KHÔNG ĐƯỢC

### Lỗi 1: "Access denied for user 'root'@'localhost'"
**Giải pháp:** Sửa username/password trong `application.properties`

### Lỗi 2: "Unknown database 'newsportal'"
**Giải pháp:** Tạo database thủ công:
```sql
CREATE DATABASE newsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Lỗi 3: "Table 'newsportal.news' doesn't exist"
**Giải pháp:** Đảm bảo `spring.jpa.hibernate.ddl-auto=create-drop` trong `application.properties`

### Lỗi 4: Có dữ liệu nhưng trang chủ vẫn trống
**Giải pháp:** Kiểm tra `is_published` column:
```sql
UPDATE news SET is_published = true WHERE is_published IS NULL OR is_published = false;
```

---

## 📊 DEBUG

Nếu muốn debug chi tiết, thêm vào method `home()` trong `HomeController.java`:

```java
@GetMapping("/")
public String home(Model model, ...) {
    // ...existing code...
    
    // DEBUG
    System.out.println("===== DEBUG HOME PAGE =====");
    System.out.println("Total news in DB: " + newsService.countPublishedNews());
    System.out.println("News page size: " + newsPage.getContent().size());
    System.out.println("News page empty: " + newsPage.isEmpty());
    newsPage.getContent().forEach(n -> 
        System.out.println("  - " + n.getTitle() + " (published: " + n.getIsPublished() + ")")
    );
    System.out.println("===========================");
    
    return "index";
}
```

---

## ✅ CHECKLIST

Trước khi báo lỗi, hãy kiểm tra:

- [ ] MySQL/MariaDB server đã khởi động
- [ ] Database `newsportal` đã tồn tại
- [ ] Table `news` đã có dữ liệu (> 0 records)
- [ ] Column `is_published = true` (ít nhất 1 bài)
- [ ] Ứng dụng Spring Boot đã khởi động thành công
- [ ] Port 8081 không bị chiếm dụng
- [ ] Không có lỗi trong console log

---

## 📞 Liên hệ

Nếu vẫn không được sau khi thử tất cả cách trên:
1. Chụp màn hình console log
2. Chụp màn hình browser
3. Chạy: `mysql -u root -proot newsportal -e "SELECT * FROM news;" > news-data.txt`
4. Gửi file `news-data.txt` để kiểm tra

---

**Cập nhật:** 2025-11-05  
**Tác giả:** Nhóm Đề Tài Selenium

