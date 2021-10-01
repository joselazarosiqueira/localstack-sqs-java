package com.jlsj;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.net.URI;

public class SqsConsumerTest {


    public static void main(String[] args) {

        SqsClient client = SqsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:4566"))
                .build();

        String queueUrl = "http://localhost:4566/queue/teste";

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .waitTimeSeconds(5)
                .maxNumberOfMessages(5)
                .build();

        while (true) {
            client.receiveMessage(receiveMessageRequest)
                    .messages()
                    .forEach( message -> {
                        System.out.println(message.body());
                    });
        }
    }
}
