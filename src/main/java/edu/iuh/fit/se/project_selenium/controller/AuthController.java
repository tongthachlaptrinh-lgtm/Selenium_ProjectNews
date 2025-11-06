package edu.iuh.fit.se.project_selenium.controller;

import edu.iuh.fit.se.project_selenium.model.User;
import edu.iuh.fit.se.project_selenium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login-simple";
    }

    // -------------------- REGISTER --------------------
    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "auth/register-simple"; // Trang có layout
    }

    // Dành cho test Selenium hoặc demo nhanh (không layout)
    @GetMapping("/register-simple")
    public String registerSimplePage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "auth/register-simple";
    }

    // -------------------- HANDLE REGISTER POST --------------------
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirect) {
        try {
            // Kiểm tra username đã tồn tại
            if (userService.existsByUsername(user.getUsername())) {
                redirect.addFlashAttribute("error", "❌ Tên đăng nhập đã tồn tại!");
                redirect.addFlashAttribute("user", user);
                return "redirect:/register";
            }

            // Kiểm tra mật khẩu xác nhận
            if (!user.getPassword().equals(confirmPassword)) {
                redirect.addFlashAttribute("error", "⚠️ Mật khẩu xác nhận không khớp!");
                redirect.addFlashAttribute("user", user);
                return "redirect:/register";
            }

            // Mã hóa và lưu
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);

            redirect.addFlashAttribute("success", "🎉 Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";

        } catch (Exception e) {
            redirect.addFlashAttribute("error", "❗ Có lỗi xảy ra: " + e.getMessage());
            redirect.addFlashAttribute("user", user);
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}