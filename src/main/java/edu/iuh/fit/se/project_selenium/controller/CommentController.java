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
        
        // Kiểm tra đăng nhập
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để bình luận");
            return "redirect:/login";
        }
        
        // Kiểm tra nội dung bình luận
        if (content == null || content.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Nội dung bình luận không được để trống");
            return "redirect:/news/" + newsId;
        }

        // Kiểm tra bài viết có tồn tại không
        News news = newsService.getNewsById(newsId).orElse(null);
        if (news == null) {
            redirectAttributes.addFlashAttribute("error", "Bài viết không tồn tại");
            return "redirect:/";
        }
        
        // Lấy thông tin user từ authentication
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin người dùng");
            return "redirect:/login";
        }

        // Tạo và lưu bình luận
        Comment comment = new Comment();
        comment.setContent(content.trim());
        comment.setNews(news);
        comment.setUser(user);
        comment.setIsApproved(true); // Tự động duyệt bình luận

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
