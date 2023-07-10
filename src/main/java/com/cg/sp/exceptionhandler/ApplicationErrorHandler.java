package com.cg.sp.exceptionhandler;

import com.cg.sp.model.response.ErrorBody;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationErrorHandler {
    @Value("${constants.input_msg}")
    private String inputMsg;
    @Value("${constants.invalid_ID_msg}")
    private String invalidIdMsg;
    @Value("${constants.invalid_date_range_msg}")
    private String invalidDateRangeMsg;
    @Value("${constants.no_data_found}")
    private String no_data_found;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorBody> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fetch -> fetch.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorBody errorBody = new ErrorBody();
        errorBody.setError_data(inputMsg);
        errorBody.setError_time(LocalDateTime.now().toString());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorBody> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorList = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList());

        ErrorBody errorBody = new ErrorBody();
        errorBody.setError_data(inputMsg);
        errorBody.setError_time(LocalDateTime.now().toString());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorBody> handleInvalidIDException(InvalidInputException invalidIDException) {

        ErrorBody errorBody = new ErrorBody();
        List<String> errorList = new ArrayList<>();
        errorBody.setError_data(invalidIdMsg);
        errorBody.setError_time(LocalDateTime.now().toString());

        errorList.add(invalidIDException.getMessage());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateRange.class)
    public ResponseEntity<ErrorBody> handleInvalidDateRangeException(InvalidDateRange invalidDateRange) {

        ErrorBody errorBody = new ErrorBody();
        List<String> errorList = new ArrayList<>();
        errorBody.setError_data(invalidDateRangeMsg);
        errorBody.setError_time(LocalDateTime.now().toString());

        errorList.add(invalidDateRange.getMessage());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorBody> handleRequestBodyException(InvalidRequestException requestBodyException) {
        ErrorBody errorBody = new ErrorBody();
        List<String> errorList = new ArrayList<>();
        errorBody.setError_data(inputMsg);
        errorBody.setError_time(LocalDateTime.now().toString());

        errorList.add(requestBodyException.getMessage());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSubscription.class)
    public ResponseEntity<ErrorBody> handleInvalidHeaderException(InvalidSubscription invalidSubscription) {
        ErrorBody errorBody = new ErrorBody();
        List<String> errorList = new ArrayList<>();
        errorBody.setError_data(inputMsg);
        errorBody.setError_time(LocalDateTime.now().toString());

        errorList.add(invalidSubscription.getMessage());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoDataFound.class)
    public ResponseEntity<ErrorBody> handleNoDateFoundException(NoDataFound noDataFound) {
        ErrorBody errorBody = new ErrorBody();
        List<String> errorList = new ArrayList<>();
        errorBody.setError_data(no_data_found);
        errorBody.setError_time(LocalDateTime.now().toString());

        errorList.add(noDataFound.getMessage());
        errorBody.setError_details(errorList);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
