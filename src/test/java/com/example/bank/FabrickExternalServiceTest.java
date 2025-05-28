package com.example.bank;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.FabrickPaymentDto;
import com.example.bank.dto.fabrick.FabrickTransactionsDto;
import com.example.bank.dto.fabrick.GenericResponseDto;
import com.example.bank.dto.request.PaymentRequestBody;
import com.example.bank.exception.FabrickException;
import com.example.bank.external.api.FabrickExternalService;
import com.example.bank.util.FabrickConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FabrickExternalServiceTest {

    @InjectMocks
    private FabrickExternalService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private FabrickConstants fabrickConstants;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuildRequestEntityShouldSetCorrectHeaders() {
        String apiKey = "apiKey";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";

        HttpHeaders headers = service.buildRequestEntity(apiKey, authSchema, timeZone);

        assertThat(headers.getFirst("Api-Key")).isEqualTo("apiKey");
        assertThat(headers.getFirst("X-Time-Zone")).isEqualTo("Europe/Rome");
        assertThat(headers.getFirst("Auth-Schema")).isEqualTo("S2S");
        assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetBalanceShouldReturnsBalanceDto() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/balance";

        FabrickBalanceDto balanceDto = new FabrickBalanceDto();
        GenericResponseDto<FabrickBalanceDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setPayload(balanceDto);

        ResponseEntity<GenericResponseDto<FabrickBalanceDto>> response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickBalanceDto>>>any())
        ).thenReturn(response);

        FabrickBalanceDto result = service.getBalance(userId, apiKey, authSchema, timeZone);

        assertThat(result).isEqualTo(balanceDto);
    }

    @Test
    void testGetBalanceShouldThrowFabrickException() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/balance";

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickBalanceDto>>>any())
        ).thenThrow(new RestClientException("Connection error"));

        assertThatThrownBy(() -> service.getBalance(userId, apiKey, authSchema, timeZone))
                .isInstanceOf(FabrickException.class)
                .hasMessageContaining("Unexpected error calling Fabrick API");
    }

    @Test
    void testCreateTransferShouldSucceed() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/payments/money-transfers";

        PaymentRequestBody body = new PaymentRequestBody();
        FabrickPaymentDto paymentDto = new FabrickPaymentDto();
        GenericResponseDto<FabrickPaymentDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setPayload(paymentDto);

        ResponseEntity<GenericResponseDto<FabrickPaymentDto>> response =
                new ResponseEntity<>(genericResponse, HttpStatus.OK);

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickPaymentDto>>>any(),
                eq(body)
        )).thenReturn(response);

        assertThatCode(() -> service.createTransfer(userId, apiKey, authSchema, timeZone, body))
                .doesNotThrowAnyException();
    }

    @Test
    void testCreateTransferShouldThrowFabrickExceptionOnRestClientError() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/payments/money-transfers";

        PaymentRequestBody body = new PaymentRequestBody();

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickPaymentDto>>>any(),
                eq(body)
        )).thenThrow(new ResourceAccessException("Connection error"));

        assertThatThrownBy(() -> service.createTransfer(userId, apiKey, authSchema, timeZone, body))
                .isInstanceOf(FabrickException.class)
                .hasMessageContaining("Error during connection to Fabrick API");
    }

    @Test
    void getTransactionsShouldReturnTransactionsDto() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        String url = "https://api.test/" + userId + "/transactions?fromAccountingDate=" + fromDate + "&toAccountingDate=" + toDate;

        FabrickTransactionsDto transactionsDto = new FabrickTransactionsDto();
        GenericResponseDto<FabrickTransactionsDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setPayload(transactionsDto);

        ResponseEntity<GenericResponseDto<FabrickTransactionsDto>> response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickTransactionsDto>>>any())
        ).thenReturn(response);

        FabrickTransactionsDto result = service.getTransactions(userId, apiKey, authSchema, timeZone, fromDate, toDate);

        assertThat(result).isEqualTo(transactionsDto);
    }

    @Test
    void getTransactionsShouldThrowFabrickException() {
        String apiKey = "apiKey";
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);
        String url = "https://api.test/" + userId + "/transactions?fromAccountingDate=" + fromDate + "&toAccountingDate=" + toDate;

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickTransactionsDto>>>any())
        ).thenThrow(new RestClientException("API unavailable"));

        assertThatThrownBy(() -> service.getTransactions(userId, apiKey, authSchema, timeZone, fromDate, toDate))
                .isInstanceOf(FabrickException.class)
                .hasMessageContaining("Unexpected error calling Fabrick API");
    }
}