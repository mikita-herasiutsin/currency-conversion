package com.nherasiutsin.currencyconversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponseDTO {

    private String from;

    private String to;

    private BigDecimal amount;

    private BigDecimal converted;

}
