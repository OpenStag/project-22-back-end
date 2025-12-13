package com.openstage.ticketbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// Generic Response DTO for consistent API responses
public class ResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
}
