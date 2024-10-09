package com.bank.config;

import com.bank.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8080")) // DynamoDB Local
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public void createTableIfNotExists(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        DynamoDbTable<User> userTable = dynamoDbEnhancedClient.table("Users", TableSchema.fromBean(User.class));

        try {
            dynamoDbEnhancedClient.dynamoDbClient().describeTable(DescribeTableRequest.builder().tableName("Users").build());
            System.out.println("Table already exists!");
        } catch (ResourceNotFoundException e) {
            System.out.println("Table does not exist, creating now...");
            CreateTableRequest createTableRequest = CreateTableRequest.builder()
                    .tableName("Users")
                    .keySchema(k -> k.attributeName("documentNumber").keyType("HASH"))
                    .attributeDefinitions(a -> a.attributeName("documentNumber").attributeType("S"))
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L).writeCapacityUnits(5L).build())
                    .build();
            dynamoDbEnhancedClient.dynamoDbClient().createTable(createTableRequest);
            System.out.println("Table created!");
        }
    }
}
