package com.cardinalts.coachusa.orbit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StreamLambdaHandler implements RequestStreamHandler {

	private static Logger logger = LoggerFactory.getLogger(StreamLambdaHandler.class);

	private static final Logger LOGGER = LoggerFactory.getLogger(StreamLambdaHandler.class);
	private SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
	
	public StreamLambdaHandler() throws ContainerInitializationException {
		long startTime = Instant.now().toEpochMilli();
		handler = new SpringBootProxyHandlerBuilder().defaultProxy().asyncInit(startTime)
				.springBootApplication(OrbitCustomerSupportProfileApplication.class).buildAndInitialize();
	}

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

		try {
			 ObjectMapper mapper = new ObjectMapper();
			AwsProxyRequest request = mapper.readValue(inputStream, AwsProxyRequest.class);
			if (request.getMultiValueHeaders().isEmpty()) {
				System.out.println("Scheduler Triggered.......");
				outputStream.close();
			} else {
				System.out.println("API Triggered.......");
				long duration=new Date().getTime();
				AwsProxyResponse resp = handler.proxy(request, context);
				mapper.writeValue(outputStream, resp);
				outputStream.close();
				// just in case it wasn't closed by the mapper
				duration=new Date().getTime()-duration;
				LOGGER.info("Time taken to execute handleRequest:=>"+duration);
			};
			
		} catch (Exception e) {
			logger.error("exception...handleRequest............" + e.getMessage());

		}

	}
}
