package com.example.blogapi.repository;

import com.example.blogapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 이름으로 카테고리 찾기
     */
    Optional<Category> findByName(String name);
    
    /**
     * 이름으로 카테고리 존재 여부 확인
     */
    boolean existsByName(String name);
    
    /**
     * 이름에 특정 문자열이 포함된 카테고리 검색
     */
    List<Category> findByNameContainingIgnoreCase(String name);
    
    /**
     * 특정 카테고리의 게시글 수 조회
     */
    @Query("SELECT COUNT(p) FROM Post p WHERE p.category.id = :categoryId")
    Long countPostsByCategoryId(@Param("categoryId") Long categoryId);
}
