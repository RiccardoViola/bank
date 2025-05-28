package com.example.bank.external.api;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.FabrickPaymentDto;
import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.dto.fabrick.GenericResponseDto;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.exception.FabrickException;
import com.example.bank.util.FabrickConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabrickExternalService {

    private final RestTemplate restTemplate;
    private final FabrickConstants fabrickConstants;

    public HttpHeaders buildRequestEntity(String apiKey, String authSchema, String timeZone){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", apiKey);
        headers.set("X-Time-Zone", timeZone);
        headers.set("Auth-Schema", authSchema);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public FabrickBalanceDto getBalance(String userId, String apiKey, String authSchema, String timeZone){
        try {
            String url = fabrickConstants.getFabrickBaseUrl() + "/" + userId + "/balance";
            HttpHeaders headers = buildRequestEntity(apiKey, authSchema, timeZone);
            log.info("Requested url {}", url);

            ResponseEntity<GenericResponseDto<FabrickBalanceDto>> response = this.restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {});
            log.info("Request done");

            if (response.getBody() == null || response.getBody().getPayload() == null) {
                log.warn("Failed to retrieve balance. Body: {}", response.getBody());
                throw new FabrickException("Returned wrong body from Fabrick API");
            }

            return response.getBody().getPayload();
        } catch (HttpStatusCodeException e) {
            throw new FabrickException("Error " + e.getStatusCode() + " calling Fabrick API");
        } catch (ResourceAccessException e) {
            throw new FabrickException("Error during connection to Fabrick API");
        } catch (RestClientException e) {
            throw new FabrickException("Unexpected error calling Fabrick API");
        } catch (Exception e) {
            log.warn("Exception {}", e.getMessage());
            throw new FabrickException("Unexpected error");
        }
    }

    public void createTransfer(String userId, String apiKey, String authSchema, String timeZone, PaymentRequestBody body){
        try {
            String url = fabrickConstants.getFabrickBaseUrl() + "/" + userId + "/payments/money-transfers";
            HttpHeaders headers = buildRequestEntity(apiKey, authSchema, timeZone);
            log.info("Requested url {}", url);
            log.info("Reqeust body {}", body);

            ResponseEntity<GenericResponseDto<FabrickPaymentDto>> response = this.restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    new ParameterizedTypeReference<>() {},
                    body);
            log.info("Request done");

            if (response.getBody() == null || response.getBody().getPayload() == null){
                log.warn("Failed to create the transfer. Body: {}", response.getBody());
                throw new FabrickException("Returned wrong body from Fabrick API");
            }
        } catch (HttpStatusCodeException e) {
            log.warn("response {}", e.getResponseBodyAs(new ParameterizedTypeReference<GenericResponseDto<FabrickPaymentDto>>() {}));
            throw new FabrickException("Error " + e.getStatusCode() + " calling Fabrick API");
        } catch (ResourceAccessException e) {
            throw new FabrickException("Error during connection to Fabrick API");
        } catch (RestClientException e) {
            throw new FabrickException("Unexpected error calling Fabrick API");
        } catch (Exception e) {
            log.warn("Exception {}", e.getMessage());
            throw new FabrickException("Unexpected error");
        }
    }

    public FabrickTransactionsDto getTransactions(String userId, String apiKey, String authSchema, String timeZone, LocalDate fromAccountingDate, LocalDate toAccountingDate){
        try {
            String baseUrl = fabrickConstants.getFabrickBaseUrl() + "/" + userId + "/transactions";
            String queryParams = "?fromAccountingDate=" + fromAccountingDate + "&toAccountingDate=" + toAccountingDate;
            String url = baseUrl + queryParams;
            HttpHeaders headers = buildRequestEntity(apiKey, authSchema, timeZone);
            log.info("Requested url {}", url);

            ResponseEntity<GenericResponseDto<FabrickTransactionsDto>> response = this.restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {});
            log.info("Request done");

            if (response.getBody() == null || response.getBody().getPayload() == null){
                log.warn("Failed to retrieve transactions. Body: {}", response.getBody());
                throw new FabrickException("Returned wrong body from Fabrick API");
            }

            return response.getBody().getPayload();
        } catch (HttpStatusCodeException e) {
            log.warn("response {}", e.getResponseBodyAs(new ParameterizedTypeReference<GenericResponseDto<FabrickTransactionsDto>>() {}));
            throw new FabrickException("Error " + e.getStatusCode() + " calling Fabrick API");
        } catch (ResourceAccessException e) {
            throw new FabrickException("Error during connection to Fabrick API");
        } catch (RestClientException e) {
            throw new FabrickException("Unexpected error calling Fabrick API");
        } catch (Exception e) {
            log.warn("Exception {}", e.getMessage());
            throw new FabrickException("Unexpected error");
        }
    }
}