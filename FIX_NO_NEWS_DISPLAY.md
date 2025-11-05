# 🔥 SỬA LỖI: KHÔNG HIỂN THỊ DANH SÁCH BÀI VIẾT

## ❌ Vấn đề hiện tại:
Trang chủ chỉ hiển thị:
- ✅ Search box
- ✅ Footer
- ❌ KHÔNG CÓ danh sách bài viết
- ❌ Không có sidebar

## 🔍 Nguyên nhân:
**Database không có dữ liệu** hoặc **dữ liệu chưa được load từ data.sql**

## ✅ GIẢI PHÁP NHANH (Làm theo thứ tự)

### BƯỚC 1: Kiểm tra ứng dụng đã chạy chưa
Mở Command Prompt (CMD) hoặc PowerShell:
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
.\mvnw.cmd spring-boot:run
```

**Chờ đến khi thấy:**
```
Started ProjectSeleniumApplication in X.XXX seconds
```

### BƯỚC 2: Kiểm tra log console
Tìm trong console log dòng:
```
========== HOME CONTROLLER DEBUG ==========
Total news in database: ?
```

**Nếu hiển thị `0` hoặc không thấy dòng này → Dữ liệu chưa load!**

### BƯỚC 3: Load dữ liệu vào database
**Mở terminal MỚI** (giữ nguyên terminal đang chạy app):

```bash
# Tìm đường dẫn MySQL
where mysql
```

**Nếu tìm thấy MySQL:**
```bash
cd D:\KiemThuCode\NhomDeTaiSelenium\Selenium_ProjectNews
mysql -u root -proot newsportal < src\main\resources\data.sql
```

**Nếu KHÔNG tìm thấy MySQL:**
```bash
# Thay đổi đường dẫn này theo máy bạn
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -proot newsportal < src\main\resources\data.sql
```

### BƯỚC 4: Kiểm tra dữ liệu đã load chưa
```bash
mysql -u root -proot -e "USE newsportal; SELECT COUNT(*) FROM news WHERE is_published = 1;"
```

**Phải thấy kết quả:** `5` (5 bài viết)

### BƯỚC 5: Refresh trang web
1. Vào browser: `http://localhost:8081`
2. Nhấn `Ctrl + F5` (hard refresh)
3. Hoặc `Ctrl + Shift + R`

## 📊 Kết quả mong đợi:

Sau khi làm xong 5 bước trên, bạn sẽ thấy:

✅ **Search box** màu tím gradient  
✅ **Tiêu đề** "Tin tức mới nhất"  
✅ **5 bài viết** hiển thị với cards:
   - Xu hướng AI và Machine Learning năm 2024
   - Giải bóng đá World Cup 2026
   - Chương trình giáo dục STEM
   - Thị trường chứng khoán Việt Nam
   - Phong trào sống xanh

✅ **Sidebar** bên phải:
   - Tin mới nhất
   - Tin phổ biến
   - Liên kết nhanh

## 🐛 NẾU VẪN KHÔNG ĐƯỢC:

### Vấn đề 1: MySQL không chạy
```bash
# Khởi động MySQL service
net start MySQL80
```

### Vấn đề 2: Database chưa tồn tại
```bash
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS newsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

### Vấn đề 3: Port 8081 bị chiếm
```bash
# Tìm process đang dùng port 8081
netstat -ano | findstr :8081

# Kill process (thay PID bằng số thực tế)
taskkill /PID [PID_NUMBER] /F
```

### Vấn đề 4: Log hiển thị lỗi SQL
- Kiểm tra username/password MySQL trong `application.properties`
- Đảm bảo MySQL server đang chạy
- Thử reset database:
```sql
DROP DATABASE newsportal;
CREATE DATABASE newsportal;
```

## 📝 LOG MẪU THÀNH CÔNG:

Khi ứng dụng chạy đúng, bạn sẽ thấy log như này:

```
2024-11-05 11:00:00.123  INFO --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081
2024-11-05 11:00:00.456  INFO --- [main] e.i.f.s.p.ProjectSeleniumApplication     : Started ProjectSeleniumApplication in 5.123 seconds

[REQUEST TO /]
========== HOME CONTROLLER DEBUG ==========
Total news in database: 5
News page size: 5
Is empty: false
  - News: Xu hướng AI và Machine Learning năm 2024 | Published: true | Author: admin
  - News: Giải bóng đá World Cup 2026 sẽ có 48 đội tham dự | Published: true | Author: admin
  - News: Chương trình giáo dục STEM tại Việt Nam | Published: true | Author: user
  - News: Thị trường chứng khoán Việt Nam tăng trưởng mạnh | Published: true | Author: admin
  - News: Phong trào sống xanh tại các thành phố lớn | Published: true | Author: user
Latest news count: 5
Popular news count: 5
===========================================
```

## 🎯 CHECKLIST TRƯỚC KHI BÁO LỖI:

- [ ] MySQL server đã khởi động
- [ ] Database `newsportal` đã tồn tại
- [ ] File `data.sql` đã được chạy
- [ ] Table `news` có 5 records với `is_published = 1`
- [ ] Ứng dụng Spring Boot đã khởi động thành công
- [ ] Port 8081 không bị chiếm
- [ ] Browser đã hard refresh (Ctrl + F5)
- [ ] Console log hiển thị "Total news in database: 5"

## 🚀 SCRIPT TỰ ĐỘNG (Windows)

Tạo file `fix-and-run.bat`:
```batch
@echo off
echo ============================================
echo SUA LOI VA KHOI DONG UNG DUNG
echo ============================================

echo [1/4] Kiem tra MySQL...
mysql -u root -proot -e "SELECT 1" 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo FAILED: MySQL khong chay hoac sai password!
    pause
    exit /b 1
)
echo OK: MySQL dang chay

echo [2/4] Tao database...
mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS newsportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
echo OK: Database da tao

echo [3/4] Load du lieu...
cd /d "%~dp0"
mysql -u root -proot newsportal < src\main\resources\data.sql
echo OK: Du lieu da load

echo [4/4] Khoi dong ung dung...
start cmd /k ".\mvnw.cmd spring-boot:run"

echo ============================================
echo Doi 30 giay roi mo browser: http://localhost:8081
echo ============================================
timeout /t 30
start http://localhost:8081
```

Chạy file này sẽ tự động làm tất cả!

---

**Cập nhật:** 2025-11-05  
**Tác giả:** Nhóm Đề Tài Selenium

