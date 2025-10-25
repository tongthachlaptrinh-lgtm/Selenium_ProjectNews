package edu.iuh.fit.se.project_selenium.repository;

import edu.iuh.fit.se.project_selenium.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // Tìm bình luận theo tin tức và đã được duyệt
    List<Comment> findByNewsIdAndIsApprovedTrueOrderByCreatedAtAsc(Long newsId);
    
    // Tìm tất cả bình luận theo tin tức (bao gồm chưa duyệt)
    List<Comment> findByNewsIdOrderByCreatedAtAsc(Long newsId);
    
    // Tìm bình luận chưa được duyệt
    List<Comment> findByIsApprovedFalseOrderByCreatedAtDesc();
    
    // Tìm bình luận theo người dùng
    List<Comment> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Đếm số bình luận đã duyệt của một tin tức
    long countByNewsIdAndIsApprovedTrue(Long newsId);
    
    // Tìm bình luận mới nhất
    @Query("SELECT c FROM Comment c WHERE c.isApproved = true ORDER BY c.createdAt DESC")
    List<Comment> findLatestComments();
}
