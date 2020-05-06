package com.cardinalts.coachusa.orbit.service.impl;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.cardinalts.coachusa.orbit.model.Customer;
import com.cardinalts.coachusa.orbit.repository.CustomerRepository;
import com.cardinalts.coachusa.orbit.service.OrbitCustomerSupportProfileService;

import reactor.core.publisher.Mono;
@Service
public class OrbitCustomerSupportProfileServiceImpl implements OrbitCustomerSupportProfileService {
	
	private CustomerRepository customerRepository;

    public OrbitCustomerSupportProfileServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<ServerResponse> listCustomers(ServerRequest serverRequest) {
        return customerRepository.listCustomers()
                .collect(Collectors.toList())
                .flatMap(customers -> ServerResponse.ok().body(BodyInserters.fromValue(customers)));
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Customer.class)
                .flatMap(customer -> customerRepository.createCustomer(customer))
                .flatMap(customer -> ServerResponse.created(URI.create("/customers/" + customer.getId())).build());
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return customerRepository.deleteCustomer(id)
                .flatMap(customer -> ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getCustomer(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return customerRepository.getCustomer(id)
                .flatMap(customer -> ServerResponse.ok().body(BodyInserters.fromValue(customer)));
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return serverRequest.bodyToMono(Customer.class)
                .flatMap(customer -> customerRepository.updateCustomer(id, customer))
                .flatMap(customer -> ServerResponse.ok().build());
    }

}
