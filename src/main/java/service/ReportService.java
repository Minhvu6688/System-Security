package service;

import dto.ReportDto;
import model.Branch;
import model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BranchRepository;
import repositories.ReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public ReportDto createReport(ReportDto reportDto) {
        Report report = new Report();
        report.setContent(reportDto.getContent());
        if (reportDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(reportDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            report.setBranch(branch);
        }
        report = reportRepository.save(report);
        return convertToDTO(report);
    }

    @Override
    public ReportDto updateReport(Integer id, ReportDto reportDto) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setContent(reportDto.getContent());
        if (reportDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(reportDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            report.setBranch(branch);
        }
        report = reportRepository.save(report);
        return convertToDTO(report);
    }

    @Override
    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }

    @Override
    public ReportDto getReportById(Integer id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        return convertToDTO(report);
    }

    @Override
    public List<ReportDto> getAllReports() {
            return reportRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
        }

    @Override
    public List<ReportDto> getReportsByBranchId (Integer branchId){
        return reportRepository.findByBranchId(branchId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ReportDto convertToDTO(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setReportId(report.getReportId());
        reportDto.setContent(report.getContent());
        reportDto.setBranchId(report.getBranch().getId());
        reportDto.setCreatedAt(report.getCreatedAt());
        return reportDto;
    }
}

