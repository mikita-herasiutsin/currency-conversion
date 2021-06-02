package com.nherasiutsin.currencyconversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponseDTO {

    @NotEmpty(message = "from must not be empty")
    private String from;

    @NotEmpty(message = "to must not be empty")
    private String to;

    @NotNull(message = "amount must not be null")
    private BigDecimal amount;

    @NotNull(message = "converted must not be null")
    private BigDecimal converted;

}
