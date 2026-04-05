package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.mapper.FinancialRecordMapper;
import com.zorvyn.finance.dto.request.CreateRecordRequest;
import com.zorvyn.finance.dto.request.UpdateRecordRequest;
import com.zorvyn.finance.dto.response.FinancialRecordResponse;
import com.zorvyn.finance.exception.ResourceNotFoundException;
import com.zorvyn.finance.model.FinancialRecord;
import com.zorvyn.finance.model.User;
import com.zorvyn.finance.repository.FinancialRecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordService {

    private final FinancialRecordRepository recordRepo;
    private final UserRepository userRepo;

    public RecordService(FinancialRecordRepository recordRepo, UserRepository userRepo) {
        this.recordRepo = recordRepo;
        this.userRepo = userRepo;
    }

    private User getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public FinancialRecordResponse createRecord(CreateRecordRequest request) {
        User user = getLoggedInUser();
        FinancialRecord record = FinancialRecordMapper.toEntity(request);
        record.setCreatedBy(user);
        recordRepo.save(record);
        return FinancialRecordMapper.toResponse(record);
    }

    public FinancialRecordResponse updateRecord(UpdateRecordRequest request, Long recordId) {
        FinancialRecord record = recordRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordId));

        if (request.getAmount() != null) record.setAmount(request.getAmount());
        if (request.getType() != null) record.setType(request.getType());
        if (request.getCategory() != null) record.setCategory(request.getCategory());
        if (request.getDate() != null) record.setDate(request.getDate());
        if (request.getDescription() != null) record.setDescription(request.getDescription());

        recordRepo.save(record);
        return FinancialRecordMapper.toResponse(record);
    }

    public void deleteRecord(Long recordId) {
        FinancialRecord record = recordRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordId));
        record.setDeletedAt(LocalDateTime.now());
        recordRepo.save(record);
    }

    public FinancialRecordResponse getRecord(Long recordId) {
        FinancialRecord record = recordRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordId));

        if (record.getDeletedAt() != null) {
            throw new ResourceNotFoundException("Record not found with id: " + recordId);
        }
        return FinancialRecordMapper.toResponse(record);
    }

    public List<FinancialRecordResponse> getAllRecords() {
        return recordRepo.findByDeletedAtIsNull()
                .stream()
                .map(FinancialRecordMapper::toResponse)
                .toList();
    }

    public List<FinancialRecordResponse> filterByType(String type) {
        return recordRepo.findByType(type)
                .stream()
                .filter(r -> r.getDeletedAt() == null)
                .map(FinancialRecordMapper::toResponse)
                .toList();
    }

    public List<FinancialRecordResponse> filterByCategory(String category) {
        return recordRepo.findByCategory(category)
                .stream()
                .filter(r -> r.getDeletedAt() == null)
                .map(FinancialRecordMapper::toResponse)
                .toList();
    }

    public List<FinancialRecordResponse> filterByDate(LocalDate date) {
        return recordRepo.findByDate(date)
                .stream()
                .filter(r -> r.getDeletedAt() == null)
                .map(FinancialRecordMapper::toResponse)
                .toList();
    }
}
