package com.finance.dashboard.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalystDashboardDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;

    private Map<String, Double> categorySummary;
    private Map<String, Double> monthlyTrend;

    private List<UserFinanceSummaryDTO> userSummary;
}
