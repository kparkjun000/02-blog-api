package com.example.blogapi.repository;

import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    /**
     * 상태별 게시글 조회
     */
    List<Post> findByStatus(PostStatus status);
    
    /**
     * 상태별 게시글 페이지네이션 조회
     */
    Page<Post> findByStatus(PostStatus status, Pageable pageable);
    
    /**
     * 카테고리별 게시글 조회
     */
    List<Post> findByCategoryId(Long categoryId);
    
    /**
     * 카테고리별 게시글 페이지네이션 조회
     */
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);
    
    /**
     * 작성자별 게시글 조회
     */
    List<Post> findByAuthor(String author);
    
    /**
     * 제목에 특정 문자열이 포함된 게시글 검색
     */
    List<Post> findByTitleContainingIgnoreCase(String title);
    
    /**
     * 내용에 특정 문자열이 포함된 게시글 검색
     */
    List<Post> findByContentContainingIgnoreCase(String content);
    
    /**
     * 작성자와 상태로 게시글 조회
     */
    List<Post> findByAuthorAndStatus(String author, PostStatus status);
    
    /**
     * 발행된 게시글만 조회 (최신순)
     */
    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' ORDER BY p.createdAt DESC")
    List<Post> findPublishedPostsOrderByCreatedAtDesc();
    
    /**
     * 발행된 게시글 페이지네이션 조회 (최신순)
     */
    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' ORDER BY p.createdAt DESC")
    Page<Post> findPublishedPostsOrderByCreatedAtDesc(Pageable pageable);
    
    /**
     * 특정 기간 내에 생성된 게시글 조회
     */
    @Query("SELECT p FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Post> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                    @Param("endDate") LocalDateTime endDate);
    
    /**
     * 카테고리와 상태로 게시글 조회
     */
    List<Post> findByCategoryIdAndStatus(Long categoryId, PostStatus status);
}
