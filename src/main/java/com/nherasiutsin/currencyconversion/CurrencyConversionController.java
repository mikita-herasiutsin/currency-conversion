package com.nherasiutsin.currencyconversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/currency")
public class CurrencyConversionController {

    @ApiIgnore
    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("index");
    }

    @PostMapping("/convert")
    public Mono<ConversionResponseDTO> validateAddress(@RequestBody @Valid ConversionRequestDTO request) {
        return Mono.just(new ConversionResponseDTO(request.getFrom(), request.getTo(), request.getAmount(), new BigDecimal("23.23")));
    }

}
