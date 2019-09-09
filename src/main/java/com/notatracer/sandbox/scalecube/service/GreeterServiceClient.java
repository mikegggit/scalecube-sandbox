package com.notatracer.sandbox.scalecube.service;

import io.scalecube.services.Microservices;
import io.scalecube.services.discovery.ScalecubeServiceDiscovery;
import io.scalecube.services.transport.protostuff.ProtostuffCodec;
import io.scalecube.services.transport.rsocket.RSocketServiceTransport;

public class GreeterServiceClient {
    public static void main(String[] args) {
        GreeterServiceClient client = new GreeterServiceClient();
        client.start();
    }

    private void start() {

        System.out.println("aaaaaaaaa");
        Microservices seed =
                Microservices.builder()
                        .discovery(ScalecubeServiceDiscovery::new)
                        .transport(() -> new RSocketServiceTransport().headersCodec(new ProtostuffCodec()))
                        .startAwait();

        System.out.println("xxxxxxxxxxxxxxx");
//        Microservices.Builder discovery = Microservices.builder().discovery(ScalecubeServiceDiscovery::new);
        Microservices.Builder discovery = Microservices.builder().discovery(ScalecubeServiceDiscovery::new);
        // Create service proxy
        GreetingsService service = seed.call().api(GreetingsService.class);

        // Execute the services and subscribe to service events
        service.sayHello("joe").subscribe(consumer -> System.out.println(consumer.message()));
        seed.onShutdown().block();
    }
}
