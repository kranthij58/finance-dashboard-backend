package com.zorvyn.finance.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRecordRequest {
    private BigDecimal amount;
    private String type;
    private String category;
    private LocalDate date;
    private String description;
}
