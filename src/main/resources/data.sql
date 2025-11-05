-- Tạo dữ liệu mẫu cho News Portal

-- Tắt foreign key check tạm thời
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa dữ liệu cũ (nếu có) theo đúng thứ tự
DELETE FROM comments;
DELETE FROM news;
DELETE FROM categories;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE news AUTO_INCREMENT = 1;
ALTER TABLE comments AUTO_INCREMENT = 1;

-- Bật lại foreign key check
SET FOREIGN_KEY_CHECKS = 1;

-- Thêm người dùng TRƯỚC (để có author_id cho news)
INSERT INTO users (id, username, password, role, created_at, is_active) VALUES
(1, 'admin', '123456', 'ADMIN', NOW(), true),
(2, 'user', '123456', 'USER', NOW(), true),
(3, 'editor', '123456', 'ADMIN', NOW(), true);

-- Thêm danh mục SAU khi đã có users
INSERT INTO categories (id, name, description, created_at) VALUES
(1, 'Công nghệ', 'Tin tức về công nghệ, phần mềm, ứng dụng', NOW()),
(2, 'Thể thao', 'Tin tức thể thao trong nước và quốc tế', NOW()),
(3, 'Giáo dục', 'Tin tức về giáo dục, đào tạo', NOW()),
(4, 'Kinh tế', 'Tin tức kinh tế, tài chính', NOW()),
(5, 'Xã hội', 'Tin tức xã hội, đời sống', NOW());

-- Thêm tin tức - QUAN TRỌNG: is_published PHẢI là 1 hoặc true
INSERT INTO news (id, title, content, summary, image_url, view_count, is_published, created_at, updated_at, author_id, category_id) VALUES
(1, 'Xu hướng AI và Machine Learning năm 2024',
'Trí tuệ nhân tạo (AI) và Machine Learning đang phát triển với tốc độ chóng mặt trong năm 2024. Các công nghệ mới như GPT-4, ChatGPT, và các mô hình ngôn ngữ lớn đang thay đổi cách chúng ta làm việc và học tập.

Các ứng dụng AI đang được tích hợp vào nhiều lĩnh vực khác nhau:
- Y tế: Chẩn đoán bệnh, phát hiện ung thư
- Giáo dục: Hỗ trợ học tập cá nhân hóa
- Kinh doanh: Tự động hóa quy trình, phân tích dữ liệu
- Nghệ thuật: Tạo nội dung sáng tạo',
'AI và Machine Learning đang thay đổi thế giới với tốc độ chóng mặt trong năm 2024',
'https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&h=400&fit=crop',
15, 1, NOW(), NOW(), 1, 1),

(2, 'Giải bóng đá World Cup 2026 sẽ có 48 đội tham dự',
'FIFA đã chính thức xác nhận rằng World Cup 2026 sẽ có 48 đội tham dự thay vì 32 đội như trước đây. Đây là một thay đổi lớn trong lịch sử bóng đá thế giới.',
'World Cup 2026 sẽ có 48 đội tham dự và được tổ chức tại 3 quốc gia',
'https://images.unsplash.com/photo-1431324155629-1a6deb1dec8d?w=800&h=400&fit=crop',
23, 1, NOW(), NOW(), 1, 2),

(3, 'Chương trình giáo dục STEM tại Việt Nam',
'Chương trình giáo dục STEM (Science, Technology, Engineering, Mathematics) đang được triển khai rộng rãi tại các trường học Việt Nam.',
'STEM đang được triển khai rộng rãi tại các trường học Việt Nam',
'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=800&h=400&fit=crop',
8, 1, NOW(), NOW(), 2, 3),

(4, 'Thị trường chứng khoán Việt Nam tăng trưởng mạnh',
'Thị trường chứng khoán Việt Nam đã có những tín hiệu tích cực trong quý đầu năm 2024.',
'Thị trường chứng khoán Việt Nam có tín hiệu tích cực trong quý đầu năm 2024',
'https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=800&h=400&fit=crop',
12, 1, NOW(), NOW(), 1, 4),

(5, 'Phong trào sống xanh tại các thành phố lớn',
'Phong trào sống xanh đang lan rộng tại các thành phố lớn của Việt Nam.',
'Phong trào sống xanh đang lan rộng tại các thành phố lớn Việt Nam',
'https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800&h=400&fit=crop',
19, 1, NOW(), NOW(), 2, 5);

-- Thêm bình luận
INSERT INTO comments (id, content, is_approved, created_at, news_id, user_id) VALUES
(1, 'Bài viết rất hay và cập nhật! AI thực sự đang thay đổi thế giới.', 1, NOW(), 1, 2),
(2, 'Tôi rất quan tâm đến xu hướng AI trong giáo dục. Có thể chia sẻ thêm không?', 1, NOW(), 1, 3),
(3, 'World Cup 48 đội sẽ rất thú vị! Mong chờ được xem.', 1, NOW(), 2, 2),
(4, 'STEM là tương lai của giáo dục. Rất tốt khi Việt Nam đang áp dụng.', 1, NOW(), 3, 3),
(5, 'Thị trường chứng khoán có vẻ khả quan. Cần nghiên cứu kỹ trước khi đầu tư.', 1, NOW(), 4, 2);

COMMIT;
