package io.pivotal.spring.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private GreetingService greetingService;

    @RequestMapping("/")
    public String hello() {
        return String.format("%s World!", greetingService.getGreeting());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
