package com.cardinalts.coachusa.orbit.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.cardinalts.coachusa.orbit.mapper.CustomerMapper;
import com.cardinalts.coachusa.orbit.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.UUID;

@Repository
public class CustomerRepository {

	
    private DynamoDbAsyncClient dynamoDbAsyncClient;
    private String customerTable;
    String transaction_name="CustomerPortal";

    public CustomerRepository(DynamoDbAsyncClient dynamoDbAsyncClient,
                              @Value("${application.dynamodb.customer_table}") String customerTable) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
        this.customerTable = customerTable;
    }

    public Flux<Customer> listCustomers() {

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(customerTable)
                .build();

        return Mono.fromCompletionStage(dynamoDbAsyncClient.scan(scanRequest))
                .map(scanResponse -> scanResponse.items())
                .map(CustomerMapper::fromList)
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Customer> createCustomer(Customer customer) {

        customer.setId(UUID.randomUUID().toString());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(customerTable)
                .item(CustomerMapper.toMap(customer))
                .build();

        return Mono.fromCompletionStage(dynamoDbAsyncClient.putItem(putItemRequest))
                .map(putItemResponse -> putItemResponse.attributes())
                .map(attributeValueMap -> customer);
    }

    public Mono<String> deleteCustomer(String id) {
        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .tableName(customerTable)
                .key(Map.of("id", AttributeValue.builder().s(id).build()))
                .build();

        return Mono.fromCompletionStage(dynamoDbAsyncClient.deleteItem(deleteItemRequest))
                .map(deleteItemResponse -> deleteItemResponse.attributes())
                .map(attributeValueMap -> id);
    }

    
   
    public Mono<Customer> getCustomer(String id) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(customerTable)
                .key(Map.of("id", AttributeValue.builder().s(id).build(), "transaction_name", AttributeValue.builder().s(transaction_name).build()))
                .build();

        return Mono.fromCompletionStage(dynamoDbAsyncClient.getItem(getItemRequest))
                .map(getItemResponse -> getItemResponse.item())
                .map(CustomerMapper::fromMap);
    }

    public Mono<String> updateCustomer(String id, Customer customer) {

        customer.setId(id);
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(customerTable)
                .item(CustomerMapper.toMap(customer))
                .build();

        return Mono.fromCompletionStage(dynamoDbAsyncClient.putItem(putItemRequest))
                .map(updateItemResponse -> id);
    }
}
