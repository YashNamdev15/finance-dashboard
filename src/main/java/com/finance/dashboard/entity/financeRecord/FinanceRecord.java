package com.finance.dashboard.entity.financeRecord;

import com.finance.dashboard.entity.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FinanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private Double amount;
    private String category;

    // Date when amount is credited or debited
    private LocalDate date;

    private String note;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // Date when record is saved in system
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
