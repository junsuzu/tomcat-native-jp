package com.example.config;

import com.example.service.GreetingService;
//import com.example.service.OutputService;
//import com.example.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {

    /*
    @Value("Hello")
    private String greeting;

    @Autowired
    private GreetingService greetingService;
    
    /*
    @Autowired
    private TimeService timeService;
    */

    /*
    @Bean
    public TimeService timeService(){
        return new TimeService(true);
    }

    @Bean
    public OutputService outputService(){
        return new OutputService(greetingService, timeService);
    }
    */

    @Bean
    public GreetingService greetingService(){
        return new GreetingService("Hello");
    }

}
