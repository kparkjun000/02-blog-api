package com.example.blogapi.service;

import com.example.blogapi.entity.Category;
import com.example.blogapi.entity.Post;
import com.example.blogapi.entity.PostStatus;
import com.example.blogapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 모든 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    /**
     * 페이지네이션으로 게시글 조회
     */
    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    
    /**
     * ID로 게시글 조회
     */
    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    
    /**
     * 발행된 게시글만 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public List<Post> getPublishedPosts() {
        return postRepository.findPublishedPostsOrderByCreatedAtDesc();
    }
    
    /**
     * 발행된 게시글 페이지네이션 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public Page<Post> getPublishedPosts(Pageable pageable) {
        return postRepository.findPublishedPostsOrderByCreatedAtDesc(pageable);
    }
    
    /**
     * 상태별 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getPostsByStatus(PostStatus status) {
        return postRepository.findByStatus(status);
    }
    
    /**
     * 상태별 게시글 페이지네이션 조회
     */
    @Transactional(readOnly = true)
    public Page<Post> getPostsByStatus(PostStatus status, Pageable pageable) {
        return postRepository.findByStatus(status, pageable);
    }
    
    /**
     * 카테고리별 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }
    
    /**
     * 카테고리별 게시글 페이지네이션 조회
     */
    @Transactional(readOnly = true)
    public Page<Post> getPostsByCategory(Long categoryId, Pageable pageable) {
        return postRepository.findByCategoryId(categoryId, pageable);
    }
    
    /**
     * 작성자별 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getPostsByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }
    
    /**
     * 제목으로 게시글 검색
     */
    @Transactional(readOnly = true)
    public List<Post> searchPostsByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }
    
    /**
     * 내용으로 게시글 검색
     */
    @Transactional(readOnly = true)
    public List<Post> searchPostsByContent(String content) {
        return postRepository.findByContentContainingIgnoreCase(content);
    }
    
    /**
     * 게시글 생성
     */
    public Post createPost(Post post) {
        // 카테고리가 지정된 경우 유효성 검사
        if (post.getCategory() != null && post.getCategory().getId() != null) {
            if (!categoryService.categoryExists(post.getCategory().getId())) {
                throw new IllegalArgumentException("존재하지 않는 카테고리입니다. ID: " + post.getCategory().getId());
            }
        }
        
        return postRepository.save(post);
    }
    
    /**
     * 게시글 수정
     */
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
        
        // 카테고리가 지정된 경우 유효성 검사
        if (postDetails.getCategory() != null && postDetails.getCategory().getId() != null) {
            if (!categoryService.categoryExists(postDetails.getCategory().getId())) {
                throw new IllegalArgumentException("존재하지 않는 카테고리입니다. ID: " + postDetails.getCategory().getId());
            }
        }
        
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setSummary(postDetails.getSummary());
        post.setAuthor(postDetails.getAuthor());
        post.setStatus(postDetails.getStatus());
        post.setCategory(postDetails.getCategory());
        
        return postRepository.save(post);
    }
    
    /**
     * 게시글 상태 변경
     */
    public Post updatePostStatus(Long id, PostStatus status) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
        
        post.setStatus(status);
        return postRepository.save(post);
    }
    
    /**
     * 게시글 삭제
     */
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
        
        postRepository.delete(post);
    }
    
    /**
     * 특정 기간 내 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getPostsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return postRepository.findByCreatedAtBetween(startDate, endDate);
    }
    
    /**
     * 카테고리와 상태로 게시글 조회
     */
    @Transactional(readOnly = true)
    public List<Post> getPostsByCategoryAndStatus(Long categoryId, PostStatus status) {
        return postRepository.findByCategoryIdAndStatus(categoryId, status);
    }
}
