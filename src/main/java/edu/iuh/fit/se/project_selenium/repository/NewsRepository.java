package edu.iuh.fit.se.project_selenium.repository;

import edu.iuh.fit.se.project_selenium.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    // Tìm tin tức đã xuất bản
    Page<News> findByIsPublishedTrueOrderByCreatedAtDesc(Pageable pageable);
    
    // Tìm tin tức theo từ khóa trong tiêu đề hoặc nội dung
    @Query("SELECT n FROM News n WHERE n.isPublished = true AND " +
           "(LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY n.createdAt DESC")
    Page<News> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // Tìm tin tức theo danh mục
    @Query("SELECT n FROM News n WHERE n.isPublished = true AND n.category.id = :categoryId ORDER BY n.createdAt DESC")
    Page<News> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    
    // Tìm tin tức theo tác giả
    List<News> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
    
    // Tìm tin tức mới nhất
    @Query("SELECT n FROM News n WHERE n.isPublished = true ORDER BY n.createdAt DESC")
    List<News> findLatestNews(Pageable pageable);
    
    // Đếm số lượng tin tức đã xuất bản
    long countByIsPublishedTrue();
    
    // Tìm tin tức phổ biến nhất (theo lượt xem)
    @Query("SELECT n FROM News n WHERE n.isPublished = true ORDER BY n.viewCount DESC")
    List<News> findPopularNews(Pageable pageable);
}
