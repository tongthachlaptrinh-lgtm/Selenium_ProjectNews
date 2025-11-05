@echo off
chcp 65001 >nul
echo ========================================
echo 🔍 KIỂM TRA VÀ SỬA LỖI TRANG CHỦ
echo ========================================
echo.

cd /d "%~dp0"

echo 📊 Bước 1: Kiểm tra kết nối database...
mysql -u root -proot -e "SELECT 'Database connection OK' as status;"
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Lỗi: Không kết nối được database!
    echo 💡 Giải pháp:
    echo    - Khởi động MySQL/MariaDB server
    echo    - Kiểm tra username/password trong application.properties
    pause
    exit /b 1
)
echo ✅ Database connection OK
echo.

echo 📊 Bước 2: Tạo database và load dữ liệu...
mysql -u root -proot < check-data.sql
echo.

echo 🚀 Bước 3: Khởi động ứng dụng...
echo ⏳ Đang khởi động Spring Boot...
echo 📝 Xem log để kiểm tra dữ liệu được load...
echo.
start cmd /k "cd /d %~dp0 && mvn spring-boot:run"

echo.
echo ========================================
echo ✅ Đã khởi động ứng dụng!
echo 🌐 Mở browser và vào: http://localhost:8081
echo ========================================
echo.
timeout /t 5
start http://localhost:8081
pause

