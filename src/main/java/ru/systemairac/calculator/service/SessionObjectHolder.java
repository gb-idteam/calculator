package ru.systemairac.calculator.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {
    private long idProject;
    private long idCalculation;
    private long idTechData;
    private long idHumidifier;

    public SessionObjectHolder() {
        System.out.println("Session object created!");
    }

}
