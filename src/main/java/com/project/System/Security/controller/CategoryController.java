package com.project.System.Security.controller;


import com.project.System.Security.dto.BranchDto;
import com.project.System.Security.dto.CategoryDto;
import com.project.System.Security.service.ICategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private ICategoryService iCategoryService;

    //Tạo category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Received request to create category: {}", categoryDto);
        CategoryDto createdCategory = iCategoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    // Lấy category theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        logger.info("Received request to get category by ID: {}", id);
        CategoryDto categoryDto = iCategoryService.getCategoryById(id);
        if (categoryDto != null) {
            logger.info("Category found: {}", categoryDto);
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        logger.warn("Category not found with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // trả về 404
    }
    // Lấy tất cả categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        logger.info("Received request to get all categories");
        List<CategoryDto> categories = iCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    // Lấy categories theo branch ID
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByBranchId(@PathVariable Long branchId) {
        logger.info("Received request to get categories by branch ID: {}", branchId);
        List<CategoryDto> categories = iCategoryService.getCategoriesByBranchId(branchId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    // update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        logger.info("Received request to update category with ID: {}", id);
        CategoryDto updatedCategory = iCategoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Received request to delete category with ID: {}", id);
        iCategoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

