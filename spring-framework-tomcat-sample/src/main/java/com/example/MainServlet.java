package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.service.GreetingService;
import com.example.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.io.*;
import java.text.SimpleDateFormat;
//import java.util.*;

import com.example.service.*;
import com.example.config.*;

//@WebServlet("/greeting")
//@Controller
//public class HelloWorldServlet extends HttpServlet {
public class MainServlet extends HttpServlet {

    @Autowired
    //private HelloWorldService helloWorldService;
    private GreetingService greetingService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //resp.getWriter().write(helloWorldService.getMessage());
        //resp.getWriter().write(greetingService.getGreeting("World"));
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        GreetingService greetingService = context.getBean(GreetingService.class);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println(greetingService.getGreeting("Spring Framework World"));
        //out.println("Hello World for Spring Framework");
    }
}
