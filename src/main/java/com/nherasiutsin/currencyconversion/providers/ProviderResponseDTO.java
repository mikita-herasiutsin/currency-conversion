package com.nherasiutsin.currencyconversion.providers;

import lombok.Data;

import java.util.Map;

@Data
public class ProviderResponseDTO {

    private Map<String, String> rates;

}
