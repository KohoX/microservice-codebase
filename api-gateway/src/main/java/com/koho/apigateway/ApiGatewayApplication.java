package com.koho.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/**
	 * builder.routes()  -->  returns RouteLocatorBuilder.Builder
	 * .route(...)       -->  returns Route.AsyncBuilder (represents one route in progress)
	 * .build()          -->  returns RouteLocator (represents ALL built routes)
	 * @param builder
	 * @return
	 */
	@Bean
	public RouteLocator kohoBankRouteConfig(RouteLocatorBuilder builder){

		return builder.routes()
				// Account service
				.route("accounts_route", r -> r
						.path("/koho/account/**")
						.filters(f -> f.rewritePath("/koho/account/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNT-SERVICE"))

				// Loan service
				.route("loans_route", r -> r
						.path("/koho/loan/**")
						.filters(f -> f.rewritePath("/koho/loan/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time",LocalDateTime.now().toString()))
						.uri("lb://LOAN-SERVICE"))

				// Card service
				.route("cards_route", r -> r
						.path("/koho/card/**")
						.filters(f -> f.rewritePath("/koho/card/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time",LocalDateTime.now().toString()))
						.uri("lb://CARD-SERVICE"))

				.build();
	}

}
