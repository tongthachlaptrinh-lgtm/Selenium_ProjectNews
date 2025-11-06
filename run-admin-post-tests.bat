@echo off
chcp 65001 >nul
echo ====================================
echo   ADMIN POST TEST SUITE
echo   Kiểm thử tính năng tạo bài viết
echo ====================================
echo.

echo [1/3] Kiểm tra ứng dụng đang chạy...
curl -s http://localhost:8081 >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ CẢNH BÁO: Ứng dụng chưa chạy tại http://localhost:8081
    echo    Vui lòng khởi động ứng dụng trước khi chạy test!
    echo.
    pause
    exit /b 1
)
echo ✅ Ứng dụng đang chạy

echo.
echo [2/3] Đang chạy Test Suite...
echo.

REM Chạy toàn bộ test cases
call mvn clean test -Dtest=AddNewsTest

echo.
echo [3/3] Hoàn thành!
echo.
echo ====================================
echo   XEM KẾT QUẢ CHI TIẾT
echo ====================================
echo 📊 Test Report: target\surefire-reports\
echo 📝 Console logs ở trên
echo.
pause

