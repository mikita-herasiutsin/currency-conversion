package com.nherasiutsin.currencyconversion.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", request.path());
        Throwable error = getError(request);
        HttpStatus errorStatus = determineHttpStatus(error);
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("error", errorStatus.getReasonPhrase());
        errorAttributes.put("message", determineMessage(error));
        return errorAttributes;
    }

    private String determineMessage(Throwable error) {
        if (error instanceof WebExchangeBindException) {
            return ((WebExchangeBindException) error).getBindingResult()
                                                     .getAllErrors()
                                                     .stream()
                                                     .map(e -> e.getObjectName() + ": " + e.getDefaultMessage())
                                                     .collect(Collectors.joining("\n"));
        }
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getReason();
        }
        return error.getMessage();
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatus();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
