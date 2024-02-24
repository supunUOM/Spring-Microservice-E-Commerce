package com.dcbf.apigateway.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalExceptionPayload {
    private String timeStamp;
    private String errorMessage;
    private HttpStatus httpStatus;
    private String errorUrl;
}
