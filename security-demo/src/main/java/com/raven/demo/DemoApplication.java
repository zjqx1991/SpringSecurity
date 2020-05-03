package com.raven.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.raven")
public class DemoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class, args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
