package com.finance.dashboard.DTO.dashboard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewerDashboardDTO {

    public Double totalIncome;
    public Double totalExpenses;
    private Double netBalance;
    private Map<String, Double> categorySummary;
}
