package com.hli.camel.boot.route;

import com.hli.camel.boot.entity.AccountDetail;
import com.hli.camel.boot.process.JsonToObjectProcessor;
import com.hli.camel.boot.process.ObjectToJsonProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class IntegrationRoute extends RouteBuilder {

    @Autowired
    private Environment env;

    @Override
    public void configure() {

        // Create a new instance of BindyFixedLengthDataFormat with AccountDetail.class as the parameter
        DataFormat bindyFixedLength = new BindyFixedLengthDataFormat(AccountDetail.class);

        // Set the directory path for the request files
        String requestDir = env.getProperty("camel.sourcefolder");

        // Set the directory path for the response files
        String responseDir = env.getProperty("camel.destinationfolder");

        // 1. JSON as input -> convert to fixed length -> sent fixed length to IBM MQ
        // Configure the route for processing JSON files from the request directory
        from("direct:JSON_TO_FIXED_LENGTH")
                .pollEnrich("file:" + requestDir + "?noop=true")
//        fromF("file:%s", requestDir)
                .routeId("JSON_TO_MQ_ROUTE")
                .convertBodyTo(String.class) // Convert the file content to a String
                .process(new JsonToObjectProcessor<>(AccountDetail.class)) // Process the JSON content to convert it to an AccountDetail object
                .marshal(bindyFixedLength) // Marshal the AccountDetail object to a fixed-length format using BindyFixedLengthDataFormat
                .log("${body}") // Log the marshaled body
                .to("jms:DEV.QUEUE.1?exchangePattern=InOnly") // Send the marshaled body to the JMS queue named "DEV.QUEUE.1"
                .end();

        //2. Fixed length from MQ -> convert to object -> unmarshal -> write to file
        // Configure the route for processing messages from the JMS queue
        from("direct:FIXED_LENGTH_TO_JSON")
                .pollEnrich("jms:DEV.QUEUE.1?exchangePattern=InOnly")
//        from("jms:DEV.QUEUE.1")
                .routeId("MQ_TO_JSON_ROUTE")
                .convertBodyTo(String.class) // Convert the message body to a String
                .unmarshal(bindyFixedLength) // Unmarshal the message body from the fixed-length format to an AccountDetail object
                .log("${body}") // Log the unmarshalled body
                .process(new ObjectToJsonProcessor<>(AccountDetail.class)) // Process the AccountDetail object to convert it to JSON
                .toF("file:%s", responseDir) // Write the JSON content to a file in the response directory
                .end();
    }
}
