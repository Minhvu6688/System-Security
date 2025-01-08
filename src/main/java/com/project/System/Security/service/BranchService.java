package com.project.System.Security.service;

import com.project.System.Security.dto.BranchDto;
import com.project.System.Security.model.Branch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.BranchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;

    private static final Logger logger = LoggerFactory.getLogger(BranchService.class);

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        if (branchDto.getParentId() != null) {
            Branch parent = branchRepository.findById(branchDto.getParentId()).orElse(null);
            branch.setParentBranch(parent);
        }
        branch = branchRepository.save(branch);
        return convertToDTO(branch);

//        // Logic để tạo branch
//        branchDto.setId(1L); // Ví dụ: set ID sau khi tạo
//        return branchDto;
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("Branch not found with ID: " + id);
                    return new IllegalArgumentException("Branch not found with id " + id);
                });
        return convertToDTO(branch);
    }

    @Override
    public List<BranchDto> getAllBranches() {
        try {
            List<Branch> branches = branchRepository.findAll();
            if (branches == null) {
                logger.error("Không thể truy xuất chi nhánh từ cơ sở dữ liệu.");
                return new ArrayList<>();
            }
            logger.info("Retrieved {} branches from the database", branches.size());
            return branches.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Lỗi khi lấy tất cả chi nhánh", e);
            throw new RuntimeException("Không thể lấy tất cả chi nhánh.", e);
        }

    }

    @Override
    public List<BranchDto> getBranchesByParentId(Long parentId) {
//        Branch parentBranch = new Branch();
//        parentBranch.setId(parentId);
//        return branchRepository.findByParentBranchId(parentBranch.getId()).stream().map(this::convertToDTO).collect(Collectors.toList());
        try {
            Branch parentBranch = new Branch();
            parentBranch.setId(parentId);
            List<Branch> branches = branchRepository.findByParentBranchId(parentBranch.getId());
            logger.info("Retrieved {} branches for parent ID: {}", branches.size(), parentId);
            return branches.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Lỗi khi lấy chi nhánh theo parentId", e);
            throw new RuntimeException("Không thể lấy chi nhánh theo parent ID.", e);
        }
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        branch.setName(branchDto.getName());
        if (branchDto.getParentId() != null) {
            Branch parent = branchRepository.findById(branchDto.getParentId()).orElse(null);
            branch.setParentBranch(parent);
        }
        branch = branchRepository.save(branch);
        return convertToDTO(branch);
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        branchRepository.deleteById(id);
    }
//    private BranchDto convertToDTO(Branch branch) {
//        BranchDto branchDTO = new BranchDto();
//        branchDTO.setId(branch.getId());
//        branchDTO.setName(branch.getName());
//        branchDTO.setParentId(branch.getParentBranch() != null ?
//                branch.getParentBranch().getId() : null);
//        branchDTO.setCreatedAt(branch.getCreatedAt());
//        branchDTO.setUpdatedAt(branch.getUpdatedAt());
//        return branchDTO;
//    }

    private BranchDto convertToDTO(Branch branch) {
        if (branch == null) {
            logger.error("Branch is null, cannot convert to DTO");
            return null;
        }
        BranchDto branchDTO = new BranchDto();
        branchDTO.setId(branch.getId());
        branchDTO.setName(branch.getName());
        if (branch.getParentBranch() != null) {
            branchDTO.setParentId(branch.getParentBranch().getId());
        } else {
            branchDTO.setParentId(null);
        }
        branchDTO.setCreatedAt(branch.getCreatedAt());
        branchDTO.setUpdatedAt(branch.getUpdatedAt());
        return branchDTO;
    }
}
