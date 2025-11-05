package edu.iuh.fit.se.project_selenium.service;

import edu.iuh.fit.se.project_selenium.model.Comment;
import edu.iuh.fit.se.project_selenium.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    public List<Comment> getApprovedCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsIdAndIsApprovedTrueOrderByCreatedAtAsc(newsId);
    }
    
    public List<Comment> getAllCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsIdOrderByCreatedAtAsc(newsId);
    }
    
    public List<Comment> getPendingComments() {
        return commentRepository.findByIsApprovedFalseOrderByCreatedAtDesc();
    }
    
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public List<Comment> getLatestComments() {
        return commentRepository.findLatestComments();
    }
    
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
    
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    
    public void approveComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment commentEntity = comment.get();
            commentEntity.setIsApproved(true);
            commentRepository.save(commentEntity);
        }
    }
    
    public void rejectComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment commentEntity = comment.get();
            commentEntity.setIsApproved(false);
            commentRepository.save(commentEntity);
        }
    }
    
    public long getApprovedCommentsCountByNewsId(Long newsId) {
        return commentRepository.countByNewsIdAndIsApprovedTrue(newsId);
    }
}
