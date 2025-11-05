-- Tạo database nếu chưa có
CREATE DATABASE IF NOT EXISTS newsportal;
USE newsportal;

-- Kiểm tra dữ liệu
SELECT 'Checking users...' as status;
SELECT COUNT(*) as user_count FROM users;
SELECT * FROM users LIMIT 5;

SELECT 'Checking news...' as status;
SELECT COUNT(*) as news_count FROM news;
SELECT id, title, is_published FROM news LIMIT 5;

SELECT 'Checking categories...' as status;
SELECT COUNT(*) as category_count FROM categories;
SELECT * FROM categories LIMIT 5;

SELECT 'Checking comments...' as status;
SELECT COUNT(*) as comment_count FROM comments;
SELECT * FROM comments LIMIT 5;

