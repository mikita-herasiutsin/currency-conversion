package com.nherasiutsin.currencyconversion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyConversionControllerTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void happyPath() {
        BigDecimal amount = new BigDecimal(2);
        String from = "EUR";
        String to = "USD";

        ResponseEntity<ConversionResponseDTO> response = restTemplate.postForEntity(
            "/currency/convert",
            new ConversionRequestDTO(from, to, amount),
            ConversionResponseDTO.class
        );

        assertThat(response.getStatusCode(), is(OK));
        assertThat(response.getBody(), not(nullValue()));
        assertThat(response.getBody().getFrom(), is(from));
        assertThat(response.getBody().getTo(), is(to));
        assertThat(response.getBody().getAmount(), is(amount));
        assertThat(response.getBody().getConverted(), not(nullValue()));
    }

    @Test
    public void shouldReturnErrorMessageForInvalidInput() {
        ResponseEntity<String> response = restTemplate.postForEntity(
            "/currency/convert",
            new ConversionRequestDTO("EUR", null, new BigDecimal(2)),
            String.class
        );

        assertThat(response.getStatusCode(), is(BAD_REQUEST));
        assertThat("Couldn't find validation error message", response.getBody().contains("from must not be empty"), is(true));
    }

    @Test
    public void shouldReturnErrorMessageIfConversionFailed() {
        ResponseEntity<String> response = restTemplate.postForEntity(
            "/currency/convert",
            new ConversionRequestDTO("23fds", "USD", new BigDecimal(2)),
            String.class
        );

        assertThat(response.getStatusCode(), is(NOT_FOUND));
        assertThat("Couldn't find validation error message", response.getBody().contains("There are no providers available"), is(true));
    }

}