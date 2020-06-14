package com.example.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Configuration
public class AmazonDynamoDBClient {

    private Logger logger = LoggerFactory.getLogger(AmazonDynamoDBClient.class);

    private AmazonDynamoDB ddb;

    private DynamoDBMapper dynamoDBMapper;

    private DynamoDB dynamoDB;

    @PostConstruct
    private void init() throws IOException {
        String accessKeyId = FileUtils.readFileToString(new File("/stuff/secrets/accessKeyId"), StandardCharsets.UTF_8);
        String secretAccessKey = FileUtils.readFileToString(new File("/stuff/secrets/secretAccessKey"), StandardCharsets.UTF_8);

        BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyId, secretAccessKey);

        ddb = AmazonDynamoDBClientBuilder.standard()
                .withRegion("eu-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();

        dynamoDBMapper = new DynamoDBMapper(ddb);
        dynamoDB = new DynamoDB(ddb);

        logger.info("Amazon DynamoDB initialized");
    }

    @PreDestroy
    private void destroy() {
        ddb.shutdown();
        dynamoDB.shutdown();
    }

}
