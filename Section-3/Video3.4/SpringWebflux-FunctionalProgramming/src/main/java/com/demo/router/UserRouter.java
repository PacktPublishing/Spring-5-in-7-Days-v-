package com.demo.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.handler.UserHandler;

@Configuration
public class UserRouter {
	
	@Bean
	public RouterFunction<ServerResponse> routingPlan(UserHandler userHandler) {
		return RouterFunctions.route(GET("/{id}").and(accept(APPLICATION_JSON)), userHandler::findOne)
				.andRoute(GET("/").and(accept(APPLICATION_JSON)), userHandler::findAll)
				.andRoute(POST("/create").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						userHandler::create)
				.andRoute(PUT("/update").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						userHandler::update)
				.andRoute(DELETE("/delete/{id}"), userHandler::delete);
	}
}
