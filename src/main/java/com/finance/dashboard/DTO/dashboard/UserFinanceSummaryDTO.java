package com.finance.dashboard.DTO.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFinanceSummaryDTO {

    public String username;
    public Double totalIncome;
    public Double totalExpense;
    public Double netBalance;
}