package com.finance.dashboard.controller.financeRecord;


import com.finance.dashboard.DTO.financeRecord.FinanceRecordUpdateDTO;
import com.finance.dashboard.DTO.financeRecord.RecordRequestDTO;
import com.finance.dashboard.DTO.financeRecord.RecordResponseDTO;
import com.finance.dashboard.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/create")
    public ResponseEntity<?> createRecord(@Valid @RequestBody
                                          RecordRequestDTO recordRequestDTO){

        RecordResponseDTO record = recordService.createRecord(recordRequestDTO);

        return new ResponseEntity<>(
                record, HttpStatus.CREATED);
    }

    @GetMapping("/loggedInUser")
    public ResponseEntity<?> getRecordsByUser(){

        List<RecordResponseDTO> records = recordService.getRecordsOfUser();
        return new ResponseEntity<>(
                records, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VIEWER')")
    public ResponseEntity<?> permanentDelete(@PathVariable Long id){

        recordService.deletePermanently(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VIEWER')")
    public ResponseEntity<?> updateRecord(@PathVariable Long id,
                                          @Valid @RequestBody
                                              FinanceRecordUpdateDTO financeRecordUpdateDTO ){

        RecordResponseDTO updatedRecord = recordService.updateRecord(id, financeRecordUpdateDTO);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterRecords(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                recordService.filterRecords(type, category, startDate, endDate)
        );
    }
}
