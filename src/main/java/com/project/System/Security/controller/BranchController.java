package com.project.System.Security.controller;

import com.project.System.Security.dto.BranchDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.System.Security.service.IBranchService;

import java.util.List;

@RestController
@RequestMapping("/api/branches") //http://localhost:8080/api/branches
public class BranchController {

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    private IBranchService iBranchService;

    //Tạo branch
    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto) {
        // Kiểm tra thông tin nhận được
// Xử lý tạo branch
        logger.info("Received request to create branch: {}", branchDto);
        BranchDto createdBranch = iBranchService.createBranch(branchDto);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
////
//        logger.info("API Called: createBranch");
//        BranchDto createdBranch = iBranchService.createBranch(branchDto);
//        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED); // Trả về 201

//        logger.info("API Called: createBranch");
//        BranchDto mockBranch = new BranchDto();
//        mockBranch.setId(1L);
//        mockBranch.setName("Mock Branch");
//        return new ResponseEntity<>(mockBranch, HttpStatus.CREATED);
    }
    // Lấy branch theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        logger.info("Received request to get branch by ID: {}", id);
        BranchDto branchDto = iBranchService.getBranchById(id);
//        return new ResponseEntity<>(branchDto, HttpStatus.OK);
        if (branchDto != null) {
            logger.info("Branch found: {}", branchDto);
            return new ResponseEntity<>(branchDto, HttpStatus.OK);
        }
        logger.warn("Branch not found with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // trả về 404
    }
    // Lấy tất cả branches
    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        logger.info("Received request to get all branches");
        List<BranchDto> branches = iBranchService.getAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
    // Lấy branches theo parent ID
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<BranchDto>> getBranchesByParentId(@PathVariable Long parentId) {
        logger.info("Received request to get branches by parent ID: {}", parentId);
        List<BranchDto> branches = iBranchService.getBranchesByParentId(parentId);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
    // Cập nhật branch
    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto branchDto) {
        logger.info("Received request to update branch with ID: {}", id);
        BranchDto updatedBranch = iBranchService.updateBranch(id, branchDto);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        logger.info("Received request to delete branch with ID: {}", id);
        iBranchService.deleteBranch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

