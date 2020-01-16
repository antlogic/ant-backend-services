package com.ant.backendservices.requestfilter;

import com.ant.backendservices.constants.ServiceConstants;
import com.ant.backendservices.requestcontext.ApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Autowired
    private ApiContext apiContext;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        try {
            setHeaders(requestWrapper);
            filterChain.doFilter(servletRequest,servletResponse);
            log.info("Finished filter for request :: {} requestId :: {} time taken :: {}ms", requestWrapper.getRequestURI(),
                    apiContext.getRequestId(), Duration.between(apiContext.getRequestStartTime(), Instant.now()).toMillis());
        } catch (Exception ex) {
            log.error("Unable to run filter for request :: {} :: {}", requestWrapper.getRequestURI(), ex);
        }
    }

    void setHeaders(HttpServletRequest request) {
        apiContext.setRequestStartTime(Instant.now());
        String userId = request.getHeader(ServiceConstants.USER_ID);
        if (StringUtils.isEmpty(userId)) {
            userId = "ANON";
        }
        apiContext.setUserId(userId);
        String requestId = request.getHeader(ServiceConstants.REQUEST_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        apiContext.setRequestId(requestId);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }
}
