package com.example.bank;

import com.example.bank.dto.fabrick.FabrickBalanceDto;
import com.example.bank.dto.fabrick.GenericResponseDto;
import com.example.bank.exception.FabrickException;
import com.example.bank.external.api.FabrickExternalService;
import com.example.bank.util.FabrickConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        when(fabrickConstants.getFabrickApiKey()).thenReturn("api-key");

        HttpEntity<String> entity = service.buildRequestEntity(authSchema, timeZone);

        HttpHeaders headers = entity.getHeaders();
        assertThat(headers.getFirst("Api-Key")).isEqualTo("api-key");
        assertThat(headers.getFirst("X-Time-Zone")).isEqualTo("Europe/Rome");
        assertThat(headers.getFirst("Auth-Schema")).isEqualTo("S2S");
        assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetBalanceShouldReturnsBalanceDto() {
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/balance";

        FabrickBalanceDto balanceDto = new FabrickBalanceDto();
        GenericResponseDto<FabrickBalanceDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setPayload(balanceDto);

        ResponseEntity<GenericResponseDto<FabrickBalanceDto>> response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(fabrickConstants.getFabrickApiKey()).thenReturn("api-key");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickBalanceDto>>>any())
        ).thenReturn(response);

        FabrickBalanceDto result = service.getBalance(userId, authSchema, timeZone);

        assertThat(result).isEqualTo(balanceDto);
    }

    @Test
    void testGetBalanceShouldThrowFabrickException() {
        String userId = "12345";
        String authSchema = "S2S";
        String timeZone = "Europe/Rome";
        String url = "https://api.test/" + userId + "/balance";

        when(fabrickConstants.getFabrickBaseUrl()).thenReturn("https://api.test");
        when(fabrickConstants.getFabrickApiKey()).thenReturn("api-key");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<GenericResponseDto<FabrickBalanceDto>>>any())
        ).thenThrow(new RestClientException("Connection error"));

        assertThatThrownBy(() -> service.getBalance(userId, authSchema, timeZone))
                .isInstanceOf(FabrickException.class)
                .hasMessageContaining("Unexpected error calling Fabrick API");
    }
}

