package com.example.bank.external.api;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.GenericResponseDto;
import com.example.bank.exception.FabrickException;
import com.example.bank.util.FabrickConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabrickExternalService {

    private final RestTemplate restTemplate;
    private final FabrickConstants fabrickConstants;

    private HttpEntity<String> buildRequestEntity(String authSchema, String timeZone){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", fabrickConstants.getFabrickApiKey());
        headers.set("X-Time-Zone", timeZone);
        headers.set("Auth-Schema", authSchema);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }

    public FabrickBalanceDto getBalance(String userId, String authSchema, String timeZone) {
        try {
            String url = fabrickConstants.getFabrickBaseUrl() + "/" + userId + "/balance";
            HttpEntity<String> entity = buildRequestEntity(authSchema, timeZone);
            log.info("Requested url {}", url);

            ResponseEntity<GenericResponseDto<FabrickBalanceDto>> response = this.restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {});
            log.info("Request done");

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getPayload();
            } else {
                log.warn("Failed to retrieve balance. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
                throw new FabrickException("Returned error from Fabrick API");
            }
        } catch (Exception e) {
            throw new FabrickException("Unexpected error calling Fabrick API");
        }
    }
}