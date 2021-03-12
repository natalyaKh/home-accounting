package smilyk.homeacc.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;



@RestControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(value = {HomeaccException.class})
    public ResponseEntity<Object> handleUserServiceException(HomeaccException ex, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //	обработка всех Exception
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(new Date(),
                ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> hangleValidatorException(MethodArgumentNotValidException ex, WebRequest request){
        BindingResult result = ex.getBindingResult();
        String message = "";
        for (ObjectError error : result.getAllErrors()) {
            String field = error instanceof FieldError ? ((FieldError) error).getField() :
                error.getObjectName();
            message = error.getDefaultMessage();
        }
        ErrorMessage errorMessage = new ErrorMessage(new Date(),
            message);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.SEE_OTHER);
    }

}
