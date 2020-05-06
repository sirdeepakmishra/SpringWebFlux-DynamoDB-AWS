package com.cardinalts.coachusa.orbit.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface OrbitCustomerSupportProfileService {
	
	 Mono<ServerResponse> listCustomers(ServerRequest serverRequest);
	 Mono<ServerResponse> createCustomer(ServerRequest serverRequest) ;
	 Mono<ServerResponse> deleteCustomer(ServerRequest serverRequest) ;
	 Mono<ServerResponse> getCustomer(ServerRequest serverRequest) ;
	 Mono<ServerResponse> updateCustomer(ServerRequest serverRequest);

}
