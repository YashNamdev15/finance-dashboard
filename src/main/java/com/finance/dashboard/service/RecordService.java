package com.finance.dashboard.service;

import com.finance.dashboard.DTO.financeRecord.FinanceRecordUpdateDTO;
import com.finance.dashboard.DTO.financeRecord.RecordRequestDTO;
import com.finance.dashboard.DTO.financeRecord.RecordResponseDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface RecordService {

    RecordResponseDTO createRecord(RecordRequestDTO recordRequestDTO);

    List<RecordResponseDTO> getRecordsOfUser();

    void deletePermanently(Long id);

    RecordResponseDTO updateRecord(Long id, @Valid FinanceRecordUpdateDTO financeRecordUpdateDTO);

    List<RecordResponseDTO> filterRecords(
            String type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    );
}
