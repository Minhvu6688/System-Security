package service;

import dto.BranchDto;

import java.util.List;

public interface IBranchService {

    BranchDto createBranch(BranchDto branchDto);

    BranchDto getBranchById(Long id);

    List<BranchDto> getAllBranches();
    List<BranchDto> getBranchesByParentId(Long parentId);

    BranchDto updateBranch(Long id, BranchDto branchDto);

    void deleteBranch(Long id);
}
