# FIX LOGIN - Hướng dẫn nhanh

## Vấn đề: Login fail với admin/123456

## Giải pháp:

### Cách 1: Restart Application (ĐỀ XUẤT)
1. Stop app hiện tại (Ctrl+C trong terminal đang chạy app)
2. Start lại: `mvn spring-boot:run`
3. Đợi app load xong (thấy "Started ProjectSeleniumApplication")
4. Test lại login

### Cách 2: Insert user trực tiếp vào Database
```sql
USE newsportal;
DELETE FROM users WHERE username='admin';
INSERT INTO users (username, password, role, created_at, is_active) 
VALUES ('admin', '123456', 'ADMIN', NOW(), true);
```

### Cách 3: Dùng Register để tạo account mới
1. Vào http://localhost:8081/register
2. Đăng ký account mới
3. Login với account đó

### Cách 4: Kiểm tra Database
```bash
# Connect to MariaDB
mysql -u root -p22102004 -h localhost -P 3309

# Check database
USE newsportal;
SELECT * FROM users;
```

## Sau khi fix xong:
- Chạy lại test: `mvn test -Dtest="LoginTest#testSuccessfulLogin"`
- Browser sẽ tự động mở và show các thao tác! 🎉

