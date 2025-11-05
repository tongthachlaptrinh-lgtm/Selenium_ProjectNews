package edu.iuh.fit.se.project_selenium.service;

import edu.iuh.fit.se.project_selenium.model.News;
import edu.iuh.fit.se.project_selenium.model.User;
import edu.iuh.fit.se.project_selenium.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    
    @Autowired
    private NewsRepository newsRepository;
    
    public Page<News> getAllPublishedNews(Pageable pageable) {
        return newsRepository.findByIsPublishedTrueOrderByCreatedAtDesc(pageable);
    }
    
    public Page<News> searchNews(String keyword, Pageable pageable) {
        return newsRepository.searchByKeyword(keyword, pageable);
    }
    
    public Page<News> getNewsByCategory(Long categoryId, Pageable pageable) {
        return newsRepository.findByCategoryId(categoryId, pageable);
    }
    
    public List<News> getLatestNews(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return newsRepository.findLatestNews(pageable);
    }
    
    public List<News> getPopularNews(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return newsRepository.findPopularNews(pageable);
    }
    
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }
    
    public News saveNews(News news) {
        return newsRepository.save(news);
    }
    
    public News updateNews(News news) {
        return newsRepository.save(news);
    }
    
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
    
    public List<News> getNewsByAuthor(Long authorId) {
        return newsRepository.findByAuthorIdOrderByCreatedAtDesc(authorId);
    }
    
    public long getTotalPublishedNewsCount() {
        return newsRepository.countByIsPublishedTrue();
    }
    
    public void incrementViewCount(Long newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        if (news.isPresent()) {
            News newsEntity = news.get();
            newsEntity.setViewCount(newsEntity.getViewCount() + 1);
            newsRepository.save(newsEntity);
        }
    }
}
