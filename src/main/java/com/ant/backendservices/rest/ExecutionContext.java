package com.ant.backendservices.rest;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExecutionContext {
    private String userId;
    private String requestId;
    private Date requestStartTime;
}
