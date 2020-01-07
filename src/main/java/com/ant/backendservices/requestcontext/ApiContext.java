package com.ant.backendservices.requestcontext;

import com.ant.backendservices.rest.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApiContext {

    @Autowired
    ExecutionContext executionContext;

    private ExecutionContext currentContext() {
        return executionContext;
    }

    public void setUserId(String userId) {
        currentContext().setUserId(userId);
    }

    public String getUserId() {
        return executionContext.getUserId();
    }

    public void setRequestId(String requestId) {
        currentContext().setRequestId(requestId);
    }

    public String getRequestId() {
        return executionContext.getRequestId();
    }

    public void setRequestStartTime(Date startTime) {
        currentContext().setRequestStartTime(startTime);
    }

    public Date getRequestStartTime() {
        return executionContext.getRequestStartTime();
    }
}
