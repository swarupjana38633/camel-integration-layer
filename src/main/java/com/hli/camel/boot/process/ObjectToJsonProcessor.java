package com.hli.camel.boot.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ObjectToJsonProcessor<T> implements Processor {

    private Class<T> clazz;

    public ObjectToJsonProcessor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(exchange.getIn().getBody(clazz));
        String trimmed = json.replaceAll(" *\" *", "\"");
        exchange.getMessage().setBody(trimmed);
    }
}
