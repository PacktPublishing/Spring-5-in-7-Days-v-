package com.demo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PublisherExample {
	static void fluxSubscriber() {
		Flux<Integer> flux1 = Flux.range(4, 10);

		List<String> iterable = Arrays.asList("Hello", "World", "!");
		Flux<String> flux2 = Flux.fromIterable(iterable);

		flux1.subscribe(i -> System.out.println(i));
		flux2.subscribe(i -> System.out.println(i));

		Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(500)).zipWith(flux2,
				(i, item) -> "item " + i + ": " + item);

		intervalFlux.subscribe(System.out::println);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static void monoSubscriber() {
		Mono<String> noData = Mono.empty();
		Mono<String> data = Mono.just("Hello");
		data.subscribe(i -> System.out.println(i), error -> System.err.println("Error " + error), () -> {
			System.out.println("Done");
		});
	}
	
	static void callbacksSementic(){
		List<String> iterable = Arrays.asList("Hello", "World", "!");
		Flux<String> flux = Flux.fromIterable(iterable);
		flux 
        .switchIfEmpty(getDefaultResults()) 
        .take(1)
        .subscribe(i -> System.out.println(i)); 
	}

	private static Publisher<? extends String> getDefaultResults() {
		return Mono.empty();
	}
}
