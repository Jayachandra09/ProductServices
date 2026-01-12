package dev.jay.productservices.advices;

import dev.jay.productservices.dtos.ErrorDto;
import dev.jay.productservices.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
//        Creating function for Product not found exception
//    If this controller ever throws a ProductNotFound exception for any reason don't throw the exception as it is
//    (Controller Advice) this is not a good method to show the exception to client...Java will throw the all the reson for exception
//    Instead we are calling this method to what actually we want to show the exception
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException exception) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
