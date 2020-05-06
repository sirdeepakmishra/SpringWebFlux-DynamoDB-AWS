package com.cardinalts.coachusa.orbit.route;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.cardinalts.coachusa.orbit.service.OrbitCustomerSupportProfileService;


@Configuration
public class OrbitCustomerSupportProfileRoute {

	
	  private OrbitCustomerSupportProfileService customerService;

	    public OrbitCustomerSupportProfileRoute(OrbitCustomerSupportProfileService customerService) {
	        this.customerService = customerService;
	    }
	
	    @Bean
	    RouterFunction<ServerResponse> customers() {
	        return route(GET("/customers"), customerService::listCustomers)
	                .andRoute(POST("/customers"), customerService::createCustomer)
	                .andRoute(GET("/customers/{id}"), customerService::getCustomer)
	                .andRoute(PUT("/customers/{id}"), customerService::updateCustomer)
	                .andRoute(DELETE("/customers/{id}"), customerService::deleteCustomer);
	    }
	
}
