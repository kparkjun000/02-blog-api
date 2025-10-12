package com.example.blogapi.service;

import com.example.blogapi.entity.Category;
import com.example.blogapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * 모든 카테고리 조회
     */
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    /**
     * ID로 카테고리 조회
     */
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    /**
     * 이름으로 카테고리 조회
     */
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    /**
     * 카테고리 생성
     */
    public Category createCategory(Category category) {
        // 이름 중복 확인
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다: " + category.getName());
        }
        return categoryRepository.save(category);
    }
    
    /**
     * 카테고리 수정
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));
        
        // 이름 변경 시 중복 확인
        if (!category.getName().equals(categoryDetails.getName()) && 
            categoryRepository.existsByName(categoryDetails.getName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다: " + categoryDetails.getName());
        }
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        return categoryRepository.save(category);
    }
    
    /**
     * 카테고리 삭제
     */
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));
        
        categoryRepository.delete(category);
    }
    
    /**
     * 이름으로 카테고리 검색
     */
    @Transactional(readOnly = true)
    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * 카테고리 존재 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean categoryExists(Long id) {
        return categoryRepository.existsById(id);
    }
    
    /**
     * 카테고리명 존재 여부 확인
     */
    @Transactional(readOnly = true)
    public boolean categoryNameExists(String name) {
        return categoryRepository.existsByName(name);
    }
}
