package com.hli.camel.boot.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JsonToObjectProcessor<T> implements Processor {

    private Class<T> clazz;

    public JsonToObjectProcessor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        T t = mapper.readValue(exchange.getIn().getBody(String.class), clazz);
        exchange.getMessage().setBody(t);
    }
}
