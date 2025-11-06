package edu.iuh.fit.se.project_selenium.controller;

import edu.iuh.fit.se.project_selenium.model.News;
import edu.iuh.fit.se.project_selenium.model.User;
import edu.iuh.fit.se.project_selenium.service.NewsService;
import edu.iuh.fit.se.project_selenium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home(Model model, 
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> newsPage = newsService.getAllPublishedNews(pageable);
        
        model.addAttribute("newsPage", newsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", newsPage.getTotalPages());
        
        // Lấy tin tức mới nhất và phổ biến nhất
        List<News> latestNews = newsService.getLatestNews(5);
        List<News> popularNews = newsService.getPopularNews(5);
        
        model.addAttribute("latestNews", latestNews);
        model.addAttribute("popularNews", popularNews);
        
        return "index";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam String keyword, 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "6") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<News> searchResults = newsService.searchNews(keyword, pageable);
        
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", searchResults.getTotalPages());
        
        return "search-results";
    }
    
    @GetMapping("/news/{id}")
    public String viewNews(@PathVariable Long id, Model model) {
        News news = newsService.getNewsById(id).orElse(null);
        if (news == null) {
            return "error/404";
        }
        
        // Tăng lượt xem
        newsService.incrementViewCount(id);
        
        model.addAttribute("news", news);
        return "news-detail";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
