package com.ant.backendservices.interceptors;

import com.ant.backendservices.constants.ServiceConstants;
import com.ant.backendservices.requestcontext.ApiContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ApiContext apiContext;

    @PostConstruct
    public void init() {
        //Do nothing
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(ServiceConstants.REQUEST_ID, apiContext.getRequestId());
        MDC.put(ServiceConstants.USER_ID, apiContext.getUserId());
        response.addHeader(ServiceConstants.REQUEST_ID, apiContext.getRequestId());
        log.info("Received request from {} with requestId {}", apiContext.getUserId(), apiContext.getRequestId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(ServiceConstants.REQUEST_ID);
        MDC.remove(ServiceConstants.USER_ID);
    }
}
