@echo off
chcp 65001 >nul
echo ========================================
echo 🧪 CHẠY TEST TC_COMMENT_01
echo ========================================
echo.
echo 📋 Test Case: TC_COMMENT_01
echo 📝 Mô tả: Kiểm tra chức năng bình luận
echo.
echo ⏳ Đang chạy test...
echo.

cd /d "%~dp0"

call mvn test -Dtest=CommentTest#TC_COMMENT_01_testAddCommentAsLoggedInUser

echo.
echo ========================================
if %ERRORLEVEL% EQU 0 (
    echo ✅ TEST PASSED - Thành công!
) else (
    echo ❌ TEST FAILED - Thất bại!
)
echo ========================================
echo.
pause

