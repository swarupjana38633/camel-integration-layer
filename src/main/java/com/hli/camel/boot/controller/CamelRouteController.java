package com.hli.camel.boot.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamelController {

    @Autowired
    ProducerTemplate producerTemplate;

    @GetMapping(value = "/jsonToMQ", produces = "text/plain")
    public String jsonToMQ() {
        return producerTemplate.requestBody("direct:JSON_TO_FIXED_LENGTH", "", String.class);
    }

    @GetMapping(value = "/mqToJson", produces = "text/plain")
    public String mqToJson() {
        return producerTemplate.requestBody("direct:FIXED_LENGTH_TO_JSON", "", String.class);
    }
}
