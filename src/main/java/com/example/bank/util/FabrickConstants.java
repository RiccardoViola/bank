package com.example.bank.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FabrickConstants {
    @Value("${fabrick.api.key}")
    private String fabrickApiKey;
    @Value("${fabrick.base.url}")
    private String fabrickBaseUrl;
}