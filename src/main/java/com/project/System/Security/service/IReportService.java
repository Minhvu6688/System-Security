package com.project.System.Security.service;

import com.project.System.Security.dto.ReportDto;

import java.util.List;

public interface IReportService {
    ReportDto createReport(ReportDto reportDto);
    ReportDto updateReport(Integer id, ReportDto reportDto);
    void deleteReport(Integer id);
    ReportDto getReportById(Integer id);
    List<ReportDto> getAllReports();
    List<ReportDto> getReportsByBranchId(Integer branchId);
}
