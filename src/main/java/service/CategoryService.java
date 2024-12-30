package service;

import dto.CategoryDto;
import model.Branch;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BranchRepository;
import repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(categoryDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            category.setBranch(branch);
        }
        if (categoryDto.getParentId() != null) {
            Category parent = categoryRepository.findById(categoryDto.getParentId()).orElse(null);
            category.setParentCategory(parent);
        }
        category = categoryRepository.save(category);
        return convertToDTO(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        if (categoryDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(categoryDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            category.setBranch(branch);
        }
        if (categoryDto.getParentId() != null) {
            Category parent = categoryRepository.findById(categoryDto.getParentId()).orElse(null);
            category.setParentCategory(parent);
        }
        category = categoryRepository.save(category);
        return convertToDTO(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return convertToDTO(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCategoriesByBranchId(Long branchId) {
        return categoryRepository.findByBranchId(branchId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private CategoryDto convertToDTO(Category category) {
        CategoryDto categoryDTO = new CategoryDto();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setBranchId(category.getBranch().getId());
        categoryDTO.setParentId(category.getParentCategory() != null ? category.getParentCategory().getId() : null);
        categoryDTO.setCreatedAt(category.getCreatedAt());
        categoryDTO.setUpdatedAt(category.getUpdatedAt());
        return categoryDTO;
    }
}
