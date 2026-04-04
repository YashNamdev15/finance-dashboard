package com.finance.dashboard.DTO.financeRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordResponseDTO {

    private Long id;
    private Double amount;
    private String category;
    private LocalDate date;
    private String note;
    private String type;

    private String username;

    private LocalDateTime createdAt;
}
