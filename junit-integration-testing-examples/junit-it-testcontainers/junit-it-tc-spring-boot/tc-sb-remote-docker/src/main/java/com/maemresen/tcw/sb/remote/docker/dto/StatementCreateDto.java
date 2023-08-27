package com.maemresen.tcw.sb.remote.docker.dto;

import com.maemresen.tcw.sb.remote.docker.entity.enums.Currency;
import com.maemresen.tcw.sb.remote.docker.entity.enums.StatementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StatementCreateDto {
    private String description;

    @NotNull(message = "Amount cannot be null")
    @Positive
    private Double amount;

    @NotNull(message = "Currency cannot be null")
    private Currency currency;

    @NotNull(message = "Type cannot be null")
    private StatementType type;

    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    private String category;
}
