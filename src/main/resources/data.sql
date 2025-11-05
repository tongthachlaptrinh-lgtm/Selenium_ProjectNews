-- Tạo dữ liệu mẫu cho News Portal

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM comments;
DELETE FROM news;
DELETE FROM categories;
DELETE FROM users;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE news AUTO_INCREMENT = 1;
ALTER TABLE comments AUTO_INCREMENT = 1;

-- Thêm danh mục
INSERT INTO categories (name, description, created_at) VALUES
('Công nghệ', 'Tin tức về công nghệ, phần mềm, ứng dụng', NOW()),
('Thể thao', 'Tin tức thể thao trong nước và quốc tế', NOW()),
('Giáo dục', 'Tin tức về giáo dục, đào tạo', NOW()),
('Kinh tế', 'Tin tức kinh tế, tài chính', NOW()),
('Xã hội', 'Tin tức xã hội, đời sống', NOW());

-- Thêm người dùng (password không hash - PasswordConfig so sánh trực tiếp)
-- admin/123456
-- user/123456  
-- editor/123456
INSERT INTO users (username, password, role, created_at, is_active) VALUES
('admin', '123456', 'ADMIN', NOW(), true),
('user', '123456', 'USER', NOW(), true),
('editor', '123456', 'ADMIN', NOW(), true);

-- Thêm tin tức
INSERT INTO news (title, content, summary, image_url, view_count, is_published, created_at, updated_at, author_id, category_id) VALUES
('Xu hướng AI và Machine Learning năm 2024', 
'Trí tuệ nhân tạo (AI) và Machine Learning đang phát triển với tốc độ chóng mặt trong năm 2024. Các công nghệ mới như GPT-4, ChatGPT, và các mô hình ngôn ngữ lớn đang thay đổi cách chúng ta làm việc và học tập.

Các ứng dụng AI đang được tích hợp vào nhiều lĩnh vực khác nhau:
- Y tế: Chẩn đoán bệnh, phát hiện ung thư
- Giáo dục: Hỗ trợ học tập cá nhân hóa
- Kinh doanh: Tự động hóa quy trình, phân tích dữ liệu
- Nghệ thuật: Tạo nội dung sáng tạo

Tuy nhiên, cũng có những thách thức về đạo đức và quyền riêng tư cần được giải quyết.',
'AI và Machine Learning đang thay đổi thế giới với tốc độ chóng mặt trong năm 2024',
'https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&h=400&fit=crop',
15, true, NOW(), NOW(), 1, 1),

('Giải bóng đá World Cup 2026 sẽ có 48 đội tham dự',
'FIFA đã chính thức xác nhận rằng World Cup 2026 sẽ có 48 đội tham dự thay vì 32 đội như trước đây. Đây là một thay đổi lớn trong lịch sử bóng đá thế giới.

Giải đấu sẽ được tổ chức tại 3 quốc gia: Mỹ, Canada và Mexico. Điều này sẽ tạo ra nhiều cơ hội cho các đội tuyển từ các châu lục khác nhau có cơ hội tham dự giải đấu lớn nhất hành tinh.

Các thay đổi chính:
- Tăng từ 32 lên 48 đội
- Tổ chức tại 3 quốc gia
- Thêm nhiều suất cho các châu lục
- Thời gian thi đấu có thể kéo dài hơn',
'World Cup 2026 sẽ có 48 đội tham dự và được tổ chức tại 3 quốc gia',
'https://images.unsplash.com/photo-1431324155629-1a6deb1dec8d?w=800&h=400&fit=crop',
23, true, NOW(), NOW(), 1, 2),

('Chương trình giáo dục STEM tại Việt Nam',
'Chương trình giáo dục STEM (Science, Technology, Engineering, Mathematics) đang được triển khai rộng rãi tại các trường học Việt Nam. Đây là một xu hướng giáo dục hiện đại nhằm chuẩn bị cho học sinh những kỹ năng cần thiết trong thời đại số.

Các hoạt động STEM bao gồm:
- Lập trình và robotics
- Thí nghiệm khoa học thực tế
- Dự án kỹ thuật sáng tạo
- Toán học ứng dụng

Nhiều trường học đã đầu tư phòng lab STEM hiện đại và đào tạo giáo viên chuyên môn để hỗ trợ học sinh phát triển tư duy logic và sáng tạo.',
'STEM đang được triển khai rộng rãi tại các trường học Việt Nam',
'https://images.unsplash.com/photo-1503676260728-1c00da094a0b?w=800&h=400&fit=crop',
8, true, NOW(), NOW(), 2, 3),

('Thị trường chứng khoán Việt Nam tăng trưởng mạnh',
'Thị trường chứng khoán Việt Nam đã có những tín hiệu tích cực trong quý đầu năm 2024. Chỉ số VN-Index đã tăng trưởng ổn định và thu hút được sự quan tâm của các nhà đầu tư trong và ngoài nước.

Các yếu tố tích cực:
- Chính sách kinh tế ổn định
- Tăng trưởng GDP tích cực
- Dòng vốn FDI tăng mạnh
- Cải thiện môi trường kinh doanh

Các chuyên gia dự báo thị trường sẽ tiếp tục tăng trưởng trong các tháng tới, tạo cơ hội cho các nhà đầu tư.',
'Thị trường chứng khoán Việt Nam có tín hiệu tích cực trong quý đầu năm 2024',
'https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=800&h=400&fit=crop',
12, true, NOW(), NOW(), 1, 4),

('Phong trào sống xanh tại các thành phố lớn',
'Phong trào sống xanh đang lan rộng tại các thành phố lớn của Việt Nam. Nhiều người dân đã thay đổi lối sống để bảo vệ môi trường và ứng phó với biến đổi khí hậu.

Các hoạt động phổ biến:
- Sử dụng túi vải thay túi nilon
- Đi xe đạp hoặc phương tiện công cộng
- Tái chế rác thải
- Sử dụng năng lượng tái tạo

Nhiều tổ chức và cá nhân đã tích cực tham gia các chương trình bảo vệ môi trường, tạo ra một cộng đồng xanh bền vững.',
'Phong trào sống xanh đang lan rộng tại các thành phố lớn Việt Nam',
'https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800&h=400&fit=crop',
19, true, NOW(), NOW(), 2, 5);

-- Thêm bình luận
INSERT INTO comments (content, is_approved, created_at, news_id, user_id) VALUES
('Bài viết rất hay và cập nhật! AI thực sự đang thay đổi thế giới.', true, NOW(), 1, 2),
('Tôi rất quan tâm đến xu hướng AI trong giáo dục. Có thể chia sẻ thêm không?', true, NOW(), 1, 3),
('World Cup 48 đội sẽ rất thú vị! Mong chờ được xem.', true, NOW(), 2, 2),
('STEM là tương lai của giáo dục. Rất tốt khi Việt Nam đang áp dụng.', true, NOW(), 3, 3),
('Thị trường chứng khoán có vẻ khả quan. Cần nghiên cứu kỹ trước khi đầu tư.', true, NOW(), 4, 2),
('Sống xanh là trách nhiệm của mỗi người. Cảm ơn bài viết!', true, NOW(), 5, 3),
('AI sẽ thay thế con người không? Tôi hơi lo lắng.', false, NOW(), 1, 2),
('World Cup 2026 ở đâu tổ chức vậy?', true, NOW(), 2, 2);
