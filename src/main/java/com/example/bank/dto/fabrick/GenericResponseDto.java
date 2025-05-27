package com.example.bank.dto.fabrick;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GenericResponseDto<T> {
    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private ArrayList<ErrorDto> errors;
    @JsonProperty("payload")
    private T payload;
}