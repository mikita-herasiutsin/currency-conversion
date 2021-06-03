package com.nherasiutsin.currencyconversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequestDTO implements Serializable {

    @NotEmpty(message = "from must not be empty")
    private String from;

    @NotEmpty(message = "from must not be empty")
    private String to;

    @NotNull(message = "from must not be null")
    private BigDecimal amount;

}
