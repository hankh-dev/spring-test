package com.example.springtest.restservice;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Value("${server.port}")
    private String port;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("GET /greeting, port: " + port);
        Instant beforeTime_1 = Instant.now();  // 코드 실행 전에 시간 받아오기
        Executor executor = Executors.newSingleThreadExecutor();
        Instant afterTime_1 = Instant.now();
        long diffTime_1 = Duration.between(beforeTime_1, afterTime_1).toMillis(); // 두 개의 실행 시간
        System.out.println("실행 시간_1(ms): " + diffTime_1);

        Instant beforeTime_2 = Instant.now();  // 코드 실행 전에 시간 받아오기
        CompletableFuture.runAsync(() -> {
            try {
                for(int i = 0; i < 100000; i++) {
                    System.out.println("Hello world!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);
        Instant afterTime_2 = Instant.now();
        long diffTime_2 = Duration.between(beforeTime_2, afterTime_2).toMillis(); // 두 개의 실행 시간
        System.out.println("실행 시간_2(ms): " + diffTime_2);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}

