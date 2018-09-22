package com.demo.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.model.User;
import com.demo.service.UserService;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	UserService userService;
	
	public Mono<ServerResponse> create(ServerRequest request) {
		final Mono<User> userPublisher = request.bodyToMono(User.class);
		return ok().contentType(APPLICATION_JSON)
				.body(fromPublisher(userPublisher.flatMap(obj -> userService.createUser(obj)), User.class));
	}

	public Mono<ServerResponse> findOne(ServerRequest request) {
		final Integer id = Integer.parseInt(request.pathVariable("id"));
		final Mono<User> userPublisher = userService.findOne(id);
		return ok().contentType(APPLICATION_JSON).body(fromPublisher(userPublisher, User.class))
				.switchIfEmpty(notFound().build());
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON).body(fromPublisher(userService.findAll(), User.class));
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		final Mono<User> userPublisher = request.bodyToMono(User.class);
		return ok().contentType(APPLICATION_JSON)
				.body(fromPublisher(userPublisher.flatMap(obj -> userService.update(obj)), User.class))
				.switchIfEmpty(notFound().build());
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		final Integer id = Integer.parseInt(request.pathVariable("id"));
		return noContent().build(userService.delete(id));
	}

}
