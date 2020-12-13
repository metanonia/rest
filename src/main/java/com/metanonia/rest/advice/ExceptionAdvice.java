package com.metanonia.rest.advice;

import com.metanonia.rest.advice.exception.UserNotFoundException;
import com.metanonia.rest.response.CommonResult;
import com.metanonia.rest.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
        log.info(e.toString());
        return responseService.getFailResult(
                Integer.parseInt(messageSource.getMessage("userNotFound.code", null, LocaleContextHolder.getLocale())),
                messageSource.getMessage("userNotFound.msg", null, LocaleContextHolder.getLocale())
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info("XXX:"+e.toString());
        return responseService.getFailResult(
                Integer.parseInt(messageSource.getMessage("unKnown.code", null, LocaleContextHolder.getLocale())),
                messageSource.getMessage("unKnown.msg", null, LocaleContextHolder.getLocale())
        );
    }
}
