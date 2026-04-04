package com.finance.dashboard.serviceImplementation;

import com.finance.dashboard.DTO.financeRecord.FinanceRecordUpdateDTO;
import com.finance.dashboard.DTO.financeRecord.RecordRequestDTO;
import com.finance.dashboard.DTO.financeRecord.RecordResponseDTO;
import com.finance.dashboard.entity.financeRecord.FinanceRecord;
import com.finance.dashboard.entity.user.Role;
import com.finance.dashboard.entity.user.Users;
import com.finance.dashboard.exception.AccessDeniedException;
import com.finance.dashboard.exception.ResourceNotFound;
import com.finance.dashboard.repository.FinanceRecordRepo;
import com.finance.dashboard.repository.UserRepo;
import com.finance.dashboard.service.RecordService;
import com.finance.dashboard.specification.FinanceRecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private FinanceRecordRepo recordRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public RecordResponseDTO createRecord(RecordRequestDTO recordRequestDTO) {



        FinanceRecord record = FinanceRecord.builder()
                .amount(recordRequestDTO.getAmount())
                .date(recordRequestDTO.getDate())
                .note(recordRequestDTO.getNote())
                .type(recordRequestDTO.getType())
                .category(recordRequestDTO.getCategory())
                .createdAt(LocalDateTime.now())
                .user(getLoggedInUser()).build();

        FinanceRecord savedRecord = recordRepo.save(record);

        return mapToDto(savedRecord);

    }

    @Override
    public List<RecordResponseDTO> getRecordsOfUser() {

        List<RecordResponseDTO> recordResponseDTOS = recordRepo.findByUserId(getLoggedInUser().getId())
                .stream()
                .map(record -> mapToDto(record))
                .toList();

        return recordResponseDTOS;
    }

    @Override
    public void deletePermanently(Long id) {

        Users loggedInUser = getLoggedInUser();

        FinanceRecord record = recordRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Record doesn't exist with this id"));

        boolean isOwner = record.getUser().getId().equals(loggedInUser.getId());
        boolean isAdmin = loggedInUser.getRole() == Role.ADMIN;

        if(!isOwner || !isAdmin){
            throw new AccessDeniedException
                    ("You are not allowed to delete financial records. Only Owner of record and Admin can update");
        }

        recordRepo.delete(record);
    }

    @Override
    public RecordResponseDTO updateRecord(Long id, FinanceRecordUpdateDTO financeRecordUpdateDTO) {

        Users loggedInUser = getLoggedInUser();

        FinanceRecord record = recordRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Record doesn't exist with this id"));

        boolean isOwner = record.getUser().getId().equals(loggedInUser.getId());
        boolean isAdmin = loggedInUser.getRole() == Role.ADMIN;

        if(!isOwner || !isAdmin){
            throw new AccessDeniedException
                    ("You are not allowed to update financial records. Only Owner of record and Admin can update");
        }

        record.setAmount(financeRecordUpdateDTO.getAmount());
        record.setNote(financeRecordUpdateDTO.getNote());
        record.setCategory(financeRecordUpdateDTO.getCategory());
        record.setType(financeRecordUpdateDTO.getType());
        record.setUpdatedAt(LocalDateTime.now());

        FinanceRecord updatedRecord =  recordRepo.save(record);
        return mapToDto(updatedRecord);
    }

    public List<RecordResponseDTO> filterRecords(
            String type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {

        Users user = getLoggedInUser();

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isAnalyst = user.getRole() == Role.ANALYST;

        Specification<FinanceRecord> spec =
                FinanceRecordSpecification.filterRecords(
                        type,
                        category,
                        startDate,
                        endDate,
                        user.getId(),
                        isAdmin || isAnalyst
                );

        List<FinanceRecord> records = recordRepo.findAll(spec);

        return records.stream()
                .map(record -> mapToDto(record))
                .toList();
    }

    public RecordResponseDTO mapToDto(FinanceRecord record){

        RecordResponseDTO recordResponseDTO = RecordResponseDTO.builder()
                .id(record.getRecordId())
                .amount(record.getAmount())
                .date(record.getDate())
                .type(record.getType().name())
                .category(record.getCategory())
                .note(record.getNote())
                .username(record.getUser().getUserName())
                .createdAt(record.getCreatedAt())
                .build();

        return recordResponseDTO;
    }

    public Users getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Users user = userRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User not found"));

        return user;
    }

}
