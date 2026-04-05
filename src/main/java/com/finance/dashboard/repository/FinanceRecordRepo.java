package com.finance.dashboard.repository;

import com.finance.dashboard.entity.financeRecord.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinanceRecordRepo extends JpaRepository<FinanceRecord, Long>,
        JpaSpecificationExecutor<FinanceRecord> {

    List<FinanceRecord> findByUserId(Long userId);

    // TOTAL INCOME OF A USER
    @Query("""
    SELECT SUM(r.amount)
    FROM FinanceRecord r
    WHERE r.type = 'INCOME'
    AND (:userId IS NULL OR r.user.id = :userId)
    """)
    Double getTotalIncome(@Param("userId") Long userId);

    // TOTAL EXPENSE OF A USER
    @Query("""
    SELECT SUM(r.amount)
    FROM FinanceRecord r
    WHERE r.type = 'EXPENSE'
    AND (:userId IS NULL OR r.user.id = :userId)
    """)
    Double getTotalExpense(@Param("userId") Long userId);

    // CATEGORY SUMMARY
    @Query("""
    SELECT r.category, SUM(r.amount)
    FROM FinanceRecord r
    WHERE r.type = 'EXPENSE'
    AND (:userId IS NULL OR r.user.id = :userId)
    GROUP BY r.category
    """)
    List<Object[]> getCategorySummary(@Param("userId") Long userId);

    // MONTHLY TREND
    @Query("""
    SELECT FUNCTION('MONTH', r.date), SUM(r.amount)
    FROM FinanceRecord r
    WHERE (:userId IS NULL OR r.user.id = :userId)
    GROUP BY FUNCTION('MONTH', r.date)
    """)
    List<Object[]> getMonthlyTrend(@Param("userId") Long userId);

    // USER-WISE INCOME
    @Query("""
    SELECT r.user.userName, SUM(r.amount)
    FROM FinanceRecord r
    WHERE r.type = 'INCOME'
    GROUP BY r.user.userName
    """)
    List<Object[]> getIncomePerUser();

    // USER-WISE EXPENSE
    @Query("""
    SELECT r.user.userName, SUM(r.amount)
    FROM FinanceRecord r
    WHERE r.type = 'EXPENSE'
    GROUP BY r.user.userName
    """)
    List<Object[]> getExpensePerUser();
}

