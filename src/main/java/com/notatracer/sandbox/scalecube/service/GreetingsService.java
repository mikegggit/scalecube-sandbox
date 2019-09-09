package com.notatracer.sandbox.scalecube.service;

import io.scalecube.services.annotations.Service;
import io.scalecube.services.annotations.ServiceMethod;
import reactor.core.publisher.Mono;

@Service("io.scalecube.Greetings")
public interface GreetingsService {
    /**
     * Call this method to be greeted by the this ScaleCube service.
     *
     * @param name name of the caller
     * @return service greeting
     */
    @ServiceMethod("sayHello")
    Mono<Greeting> sayHello(String name);
}