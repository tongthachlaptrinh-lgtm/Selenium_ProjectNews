@echo off
chcp 65001 >nul
echo ========================================
echo 🔄 KHỞI ĐỘNG LẠI ỨNG DỤNG
echo ========================================
echo.
echo ⏳ Đang dừng ứng dụng cũ (nếu có)...
taskkill /F /IM java.exe 2>nul
timeout /t 2 >nul
echo.
echo 🧹 Xóa cache và compile lại...
cd /d "%~dp0"
call mvn clean compile
echo.
echo 🚀 Khởi động ứng dụng...
echo 📝 XEM LOG CONSOLE BÊN DƯỚI:
echo ========================================
echo.
call mvn spring-boot:run

