package com.notatracer.sandbox.scalecube.service;

import io.scalecube.services.Microservices;
import io.scalecube.services.discovery.ScalecubeServiceDiscovery;
import io.scalecube.services.transport.rsocket.RSocketServiceTransport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GreetingServiceDriver implements Runnable {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        GreetingServiceDriver driver = new GreetingServiceDriver();
        driver.start();
    }

    private void start() {
        executorService.execute(this);
    }

    @Override
    public void run() {
        Microservices seed =
                Microservices.builder()
                        .discovery(ScalecubeServiceDiscovery::new)
                        .transport(RSocketServiceTransport::new)
                        .startAwait();

        // Construct a ScaleCube node which joins the cluster hosting the Greeting Service
        Microservices ms =
                Microservices.builder()
                        .discovery(
                                serviceEndpoint ->
                                        new ScalecubeServiceDiscovery(serviceEndpoint)
                                                .options(
                                                        opts ->
                                                                opts.membership(
                                                                        cfg -> cfg.seedMembers(seed.discovery().address()))))
                        .transport(RSocketServiceTransport::new)
                        .services(new DefaultGreetingService())
                        .startAwait();
    }
}
