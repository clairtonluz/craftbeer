package com.beerhouse.application.config.handlers.error;

import com.beerhouse.domain.core.exceptions.BadRequestException;
import com.beerhouse.domain.core.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseErrorVO handleNotFoundException(HttpServletRequest req, Exception e) {
        return new ResponseErrorVO(e.getLocalizedMessage(), req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseErrorVO handleHttpMessageNotReadableException(HttpServletRequest req, Exception e) {
        return new ResponseErrorVO(e.getLocalizedMessage(), req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseErrorVO handleBadRequestException(HttpServletRequest req, Exception e) {
        return new ResponseErrorVO(e.getLocalizedMessage(), req.getRequestURI());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseErrorVO handleException(HttpServletRequest req, Exception e) {
        String queryString = req.getQueryString() == null ? "" : "?" + req.getQueryString();
        String url = req.getRequestURI() + queryString;
        String messageLog = String.format("ERROR: %s %s %s",
                req.getMethod(),
                url,
                req.getRemoteAddr()
        );
        logger.log(Level.SEVERE, messageLog);
        logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        return new ResponseErrorVO(e.getLocalizedMessage(), req.getRequestURI());
    }
}
