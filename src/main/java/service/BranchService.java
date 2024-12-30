package service;

import dto.BranchDto;
import model.Branch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import repositories.BranchRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;

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
    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Branch not found"));
        return convertToDTO(branch);
    }

    @Override
    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<BranchDto> getBranchesByParentId(Long parentId) {
        return branchRepository.findByParentId(parentId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Branch not found"));
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
        branchRepository.deleteById(id);
    }
    private BranchDto convertToDTO(Branch branch) {
        BranchDto branchDTO = new BranchDto();
        branchDTO.setId(branch.getId());
        branchDTO.setName(branch.getName());
        branchDTO.setParentId(branch.getParentBranch() != null ? branch.getParentBranch().getId() : null);
        branchDTO.setCreatedAt(branch.getCreatedAt());
        branchDTO.setUpdatedAt(branch.getUpdatedAt());
        return branchDTO;
    }
}
