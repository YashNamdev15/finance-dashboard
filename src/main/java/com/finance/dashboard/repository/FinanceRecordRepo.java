package com.finance.dashboard.repository;

import com.finance.dashboard.entity.financeRecord.FinanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FinanceRecordRepo extends JpaRepository<FinanceRecord, Long>,
        JpaSpecificationExecutor<FinanceRecord> {

    List<FinanceRecord> findByUserId(Long userId);

}
