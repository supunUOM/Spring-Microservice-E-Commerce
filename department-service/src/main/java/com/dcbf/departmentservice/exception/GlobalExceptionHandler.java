package com.dcbf.departmentservice.exception;


import com.dcbf.departmentservice.payload.GlobalExceptionPayload;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {



    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<GlobalExceptionPayload> deviceSavingException(HttpServletRequest request, DepartmentNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        GlobalExceptionPayload globalExceptionPayload = GlobalExceptionPayload.builder()
                .errorUrl(request.getRequestURL().toString())
                .errorMessage(ex.getMessage())
                .httpStatus(httpStatus)
                .timeStamp(new Date().toString())
                .build();
        return new ResponseEntity<>(globalExceptionPayload, httpStatus);
    }


}
