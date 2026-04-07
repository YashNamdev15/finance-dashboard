package com.finance.dashboard.serviceImplementation;

import com.finance.dashboard.DTO.dashboard.AdminDashboardDTO;
import com.finance.dashboard.DTO.dashboard.AnalystDashboardDTO;
import com.finance.dashboard.DTO.dashboard.UserFinanceSummaryDTO;
import com.finance.dashboard.DTO.dashboard.ViewerDashboardDTO;
import com.finance.dashboard.entity.user.Users;
import com.finance.dashboard.repository.FinanceRecordRepo;
import com.finance.dashboard.repository.UserRepo;
import com.finance.dashboard.service.DashboardHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class DashboardHelperImpl implements DashboardHelper {

    @Autowired
    private FinanceRecordRepo recordRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ViewerDashboardDTO loadViewerDashboard(Users user) {

        Long userId = user.getId();

        Double income = recordRepo.getTotalIncome(userId);
        Double expense = recordRepo.getTotalExpense(userId);

        income = (income == null) ? 0 : income;
        expense = (expense == null) ? 0 : expense;

        Map<String, Double> category = convertToMap(
                recordRepo.getCategorySummary(userId)
        );

        return ViewerDashboardDTO.builder()
                .totalIncome(income)
                .totalExpenses(expense)
                .netBalance(income - expense)
                .categorySummary(category)
                .build();
    }


    @Override
    public AnalystDashboardDTO loadAnalystDashboard(Users user) {

        Double income = recordRepo.getTotalIncome(null);
        Double expense = recordRepo.getTotalExpense(null);

        income = (income == null) ? 0 : income;
        expense = (expense == null) ? 0 : expense;

        Map<String, Double> category = convertToMap(
                recordRepo.getCategorySummary(null)
        );

        Map<String, Double> trend = convertToMap(
                recordRepo.getMonthlyTrend(null)
        );

        List<UserFinanceSummaryDTO> userSummary = getUserWiseSummary();

        return AnalystDashboardDTO.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(income - expense)
                .categorySummary(category)
                .monthlyTrend(trend)
                .userSummary(userSummary)
                .build();
    }

    @Override
    public AdminDashboardDTO loadAdminDashboard(Users user) {

        AnalystDashboardDTO base = loadAnalystDashboard(user);

        Long totalUsers = userRepo.count();

        return AdminDashboardDTO.builder()
                .totalIncome(base.getTotalIncome())
                .totalExpense(base.getTotalExpense())
                .netBalance(base.getNetBalance())
                .categorySummary(base.getCategorySummary())
                .monthlyTrend(base.getMonthlyTrend())
                .userSummary(base.getUserSummary())
                .totalUsers(totalUsers)
                .build();
    }

    private List<UserFinanceSummaryDTO> getUserWiseSummary() {

        List<Object[]> incomeData = recordRepo.getIncomePerUser();
        List<Object[]> expenseData = recordRepo.getExpensePerUser();

        Map<String, Double> incomeMap = incomeData.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Double) obj[1]
                ));

        Map<String, Double> expenseMap = expenseData.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (Double) obj[1]
                ));

        // combine users
        return incomeMap.keySet().stream()
                .map(username -> {

                    Double inc = incomeMap.getOrDefault(username, 0.0);
                    Double exp = expenseMap.getOrDefault(username, 0.0);

                    return UserFinanceSummaryDTO.builder()
                            .username(username)
                            .totalIncome(inc)
                            .totalExpense(exp)
                            .netBalance(inc - exp)
                            .build();
                })
                .toList();
    }

    private Map<String, Double> convertToMap(List<Object[]> data) {
        return data.stream()
                .collect(Collectors.toMap(
                        obj -> String.valueOf(obj[0]),
                        obj -> (Double) obj[1]
                ));
    }
}
