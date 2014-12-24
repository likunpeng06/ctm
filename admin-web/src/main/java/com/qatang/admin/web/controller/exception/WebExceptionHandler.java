package com.qatang.admin.web.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qatang
 * @since 2014-12-19 15:28
 */
@ControllerAdvice
public class WebExceptionHandler {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Exception异常类处理
     * @param exception 异常
     * @return 跳转视图
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(HttpServletRequest request, Exception exception) {
        logger.error(exception.getMessage(), exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("redirect:/error");
        return mav;
    }
}