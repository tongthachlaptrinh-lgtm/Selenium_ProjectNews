@echo off
echo ============================================
echo  SELENIUM TEST DEMO - CHẠY ỨNG DỤNG TRƯỚC
echo ============================================
echo.
echo BƯỚC 1: Start Application (mở terminal MỚI)
echo Chạy: mvn spring-boot:run
echo.
echo BƯỚC 2: Đợi app khởi động xong
echo Sẽ thấy: Started ProjectSeleniumApplication
echo.
echo BƯỚC 3: Chạy test trong terminal này
echo Chạy: mvn test -D僧人test="LoginTest#testSuccessfulLogin"
echo.
echo Browser sẽ tự động mở và show thao tác! ^^
echo.
pause

