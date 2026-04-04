package com.finance.dashboard.specification;

import com.finance.dashboard.entity.financeRecord.FinanceRecord;
import com.finance.dashboard.entity.financeRecord.Type;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FinanceRecordSpecification {

    public static Specification<FinanceRecord> filterRecords(
            String type,
            String category,
            LocalDate startDate,
            LocalDate endDate,
            Long userId,
            boolean isAdmin
    ) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            // 🔐 Ownership (viewer ke liye)
            if (!isAdmin) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("user").get("id"), userId));
            }

            if (type != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("type"), Type.valueOf(type)));
            }

            if (category != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("category"), category));
            }

            if (startDate != null && endDate != null) {
                predicate = cb.and(predicate,
                        cb.between(root.get("date"), startDate, endDate));
            }

            return predicate;
        };
    }
}