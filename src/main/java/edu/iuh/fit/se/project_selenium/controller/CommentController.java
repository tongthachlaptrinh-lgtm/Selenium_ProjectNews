package edu.iuh.fit.se.project_selenium.controller;

import edu.iuh.fit.se.project_selenium.model.Comment;
import edu.iuh.fit.se.project_selenium.model.News;
import edu.iuh.fit.se.project_selenium.model.User;
import edu.iuh.fit.se.project_selenium.service.CommentService;
import edu.iuh.fit.se.project_selenium.service.NewsService;
import edu.iuh.fit.se.project_selenium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/news/{newsId}/comment")
    public String addComment(@PathVariable Long newsId,
                            @RequestParam String content,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để bình luận");
            return "redirect:/login";
        }
        
        News news = newsService.getNewsById(newsId).orElse(null);
        if (news == null) {
            redirectAttributes.addFlashAttribute("error", "Bài viết không tồn tại");
            return "redirect:/";
        }
        
        User user = (User) authentication.getPrincipal();
        
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setNews(news);
        comment.setUser(user);
        
        commentService.saveComment(comment);
        
        redirectAttributes.addFlashAttribute("success", "Bình luận đã được gửi thành công!");
        return "redirect:/news/" + newsId;
    }
    
    @GetMapping("/admin/comments")
    public String manageComments(Model model) {
        List<Comment> pendingComments = commentService.getPendingComments();
        model.addAttribute("pendingComments", pendingComments);
        return "admin/comments";
    }
    
    @PostMapping("/admin/comments/{commentId}/approve")
    public String approveComment(@PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        commentService.approveComment(commentId);
        redirectAttributes.addFlashAttribute("success", "Bình luận đã được duyệt");
        return "redirect:/admin/comments";
    }
    
    @PostMapping("/admin/comments/{commentId}/reject")
    public String rejectComment(@PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        commentService.rejectComment(commentId);
        redirectAttributes.addFlashAttribute("success", "Bình luận đã bị từ chối");
        return "redirect:/admin/comments";
    }
    
    @PostMapping("/admin/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        commentService.deleteComment(commentId);
        redirectAttributes.addFlashAttribute("success", "Bình luận đã được xóa");
        return "redirect:/admin/comments";
    }
}
