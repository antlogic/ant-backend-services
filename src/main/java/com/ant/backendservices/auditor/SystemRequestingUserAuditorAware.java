package com.ant.backendservices.auditor;

import com.ant.backendservices.requestcontext.ApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SystemRequestingUserAuditorAware implements AuditorAware<String> {

    @Autowired
    private ApiContext apiContext;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(apiContext.getUserId());
    }

}
