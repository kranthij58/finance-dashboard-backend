package com.zorvyn.finance.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinancialRecordResponse {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String category;
    private LocalDate date;
    private String description;
    private String createdBy;

}
