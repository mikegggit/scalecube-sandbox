package com.notatracer.sandbox.scalecube.service;

import reactor.core.publisher.Mono;

public class DefaultGreetingService implements GreetingsService {
    @Override
    public Mono<Greeting> sayHello(String name) {
        return Mono.just(new Greeting("Nice to meet you " + name + " and welcome to ScaleCube"));
    }
}