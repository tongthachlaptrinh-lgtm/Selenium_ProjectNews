package edu.iuh.fit.se.project_selenium.controller;

import edu.iuh.fit.se.project_selenium.model.News;
import edu.iuh.fit.se.project_selenium.model.User;
import edu.iuh.fit.se.project_selenium.service.NewsService;
import edu.iuh.fit.se.project_selenium.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalNews = newsService.getTotalPublishedNewsCount();
        List<News> latestNews = newsService.getLatestNews(10);
        
        model.addAttribute("totalNews", totalNews);
        model.addAttribute("latestNews", latestNews);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/news")
    public String manageNews(Model model) {
        List<News> allNews = newsService.getNewsByAuthor(getCurrentUserId());
        model.addAttribute("newsList", allNews);
        return "admin/news";
    }
    
    @GetMapping("/news/add")
    public String addNewsForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/add-news";
    }
    
    @PostMapping("/news/add")
    public String addNews(@ModelAttribute News news,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) authentication.getPrincipal();
        news.setAuthor(currentUser);
        
        newsService.saveNews(news);
        
        redirectAttributes.addFlashAttribute("success", "Bài viết đã được thêm thành công!");
        return "redirect:/admin/news";
    }
    
    @GetMapping("/news/edit/{id}")
    public String editNewsForm(@PathVariable Long id, Model model) {
        News news = newsService.getNewsById(id).orElse(null);
        if (news == null) {
            return "error/404";
        }
        
        model.addAttribute("news", news);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/edit-news";
    }
    
    @PostMapping("/news/edit/{id}")
    public String updateNews(@PathVariable Long id,
                            @ModelAttribute News news,
                            RedirectAttributes redirectAttributes) {
        
        News existingNews = newsService.getNewsById(id).orElse(null);
        if (existingNews == null) {
            return "error/404";
        }
        
        existingNews.setTitle(news.getTitle());
        existingNews.setContent(news.getContent());
        existingNews.setImageUrl(news.getImageUrl());
        existingNews.setSummary(news.getSummary());
        existingNews.setCategory(news.getCategory());
        
        newsService.updateNews(existingNews);
        
        redirectAttributes.addFlashAttribute("success", "Bài viết đã được cập nhật thành công!");
        return "redirect:/admin/news";
    }
    
    @PostMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsService.deleteNews(id);
        redirectAttributes.addFlashAttribute("success", "Bài viết đã được xóa thành công!");
        return "redirect:/admin/news";
    }
    
    private Long getCurrentUserId() {
        // Trong thực tế, bạn sẽ lấy từ SecurityContext
        return 1L; // Tạm thời return 1
    }
}
