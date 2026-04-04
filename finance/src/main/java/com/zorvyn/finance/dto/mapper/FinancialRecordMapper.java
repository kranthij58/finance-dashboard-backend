package com.zorvyn.finance.dto.mapper;

import com.zorvyn.finance.dto.request.CreateRecordRequest;
import com.zorvyn.finance.dto.response.FinancialRecordResponse;
import com.zorvyn.finance.model.FinancialRecord;

public class FinancialRecordMapper {
    public static FinancialRecord toEntity(CreateRecordRequest request) {
        return FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .date(request.getDate())
                .description(request.getDescription())
                .build();
    }

    public static FinancialRecordResponse toResponse(FinancialRecord financialRecord) {
        return FinancialRecordResponse.builder()
                .id(financialRecord.getId())
                .amount(financialRecord.getAmount())
                .type(financialRecord.getType())
                .category(financialRecord.getCategory())
                .date(financialRecord.getDate())
                .description(financialRecord.getDescription())
                .createdBy(financialRecord.getCreatedBy().getEmail())
                .build();
    }
}
