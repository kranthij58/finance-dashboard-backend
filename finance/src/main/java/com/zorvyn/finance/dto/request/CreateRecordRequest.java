package com.zorvyn.finance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecordRequest {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Daate is required")
    private LocalDate date;

    private String description;
}