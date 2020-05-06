package com.cardinalts.coachusa.orbit.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
@Configuration
public class BaseConfig {
	
//	protected DynamoDB dynamoDB = null;
//	protected AmazonDynamoDB amazonDynamoDB  = null;
//	
//	public  DynamoDBMapper getDynamoDBMapper(String table) {
//		DynamoDBMapper dbMapper=new DynamoDBMapper(this.amazonDynamoDB,dynamoDBMapperConfig(table));
//		return dbMapper;
//	}
//	public DynamoDBMapperConfig dynamoDBMapperConfig(String table) {
//		
//		return new DynamoDBMapperConfig.Builder()
//	            .withTableNameOverride(TableNameOverride.withTableNameReplacement(table))
//	            .build();
//	}
	
	
	String access="AKIASKTE47IWV72WJGAG";
	String secret="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";
	AwsCredentialsProvider creds = StaticCredentialsProvider.create(AwsBasicCredentials.create(access, secret));
	
    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(@Value("${application.dynamodb.endpoint}") String dynamoDBEndpoint
         ) {
        return DynamoDbAsyncClient.builder()
               // .endpointOverride(URI.create("dynamodb.us-east-1.amazonaws.com"))
                .credentialsProvider(creds)
                //.credentialsProvider(new AWSCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }
    
//    @Bean
//	public AmazonDynamoDB amazonDynamoDB() {
//		String secretJson=getSecret();
//		String awsAccessKey = getString(secretJson, "access-key");
//		String awsSecretKey = getString(secretJson, "secret-key");
//		String awsDynamoDBEndPoint = getString(secretJson, "end-point.url");
//		
//		return AmazonDynamoDBClientBuilder.standard()
//				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, awsRegion))
//				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
//				.build();
//	}

}
