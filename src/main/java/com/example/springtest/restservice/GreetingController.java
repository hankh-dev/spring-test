package com.example.springtest.restservice;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Executor executor = Executors.newFixedThreadPool(30);

        CompletableFuture.runAsync(() -> {
            try {
                for(int i = 0; i < 100; i++) {
                    System.out.println("Hello world!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}

