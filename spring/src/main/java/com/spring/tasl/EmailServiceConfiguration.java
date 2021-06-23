package com.spring.tasl;

import com.spring.services.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailServiceConfiguration {

    private final JournalService service;
    @Autowired
    public EmailServiceConfiguration(JournalService service) {
        this.service = service;
    }
}
