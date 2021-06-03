package com.nherasiutsin.currencyconversion;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/currency")
public class CurrencyConversionController {

    @Autowired
    private CurrencyService currencyService;

    /**
     * Required by Heroku.
     *
     * @return dummy response
     */
    @ApiIgnore
    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("index");
    }

    @ApiOperation(
        value = "Convert currency",
        notes = "From and to currencies have format like EUR, USD, etc."
    )
    @PostMapping("/convert")
    public Mono<ConversionResponseDTO> convert(@RequestBody @Valid ConversionRequestDTO request) {
        return currencyService.convert(request);
    }

}
