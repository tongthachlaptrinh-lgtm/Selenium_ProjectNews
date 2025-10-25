-- =====================================================
-- News Portal Database Setup Script
-- =====================================================
-- Tạo database và cấu hình cho dự án News Portal
-- Sử dụng với MariaDB hoặc MySQL 8.0+

-- Tạo database
CREATE DATABASE IF NOT EXISTS newsportal 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Sử dụng database
USE newsportal;

-- Tạo user cho ứng dụng (tùy chọn)
-- CREATE USER IF NOT EXISTS 'newsportal'@'localhost' IDENTIFIED BY '123456';
-- GRANT ALL PRIVILEGES ON newsportal.* TO 'newsportal'@'localhost';
-- FLUSH PRIVILEGES;

-- =====================================================
-- Cấu trúc bảng sẽ được tạo tự động bởi Hibernate
-- với spring.jpa.hibernate.ddl-auto=update
-- =====================================================

-- Các bảng sẽ được tạo:
-- - users (id, username, password, role, created_at, is_active)
-- - categories (id, name, description, created_at)
-- - news (id, title, content, image_url, summary, view_count, is_published, created_at, updated_at, author_id, category_id)
-- - comments (id, content, is_approved, created_at, news_id, user_id)

-- Dữ liệu mẫu sẽ được load từ file data.sql

-- =====================================================
-- Kiểm tra kết nối
-- =====================================================
SELECT 'Database newsportal created successfully!' as Status;
SELECT NOW() as Created_At;
SELECT DATABASE() as Current_Database;
