package com.sample;
    import jakarta.servlet.http.*;
    import jakarta.servlet.ServletException;
    import java.io.*;
    import java.text.SimpleDateFormat;
    import java.util.*;
    public class GreetingServlet extends HttpServlet {
      private String message;
      public void init() {
        message = "Hello! This app runs on Tomcat !";
      }
       public void doGet(HttpServletRequest request, HttpServletResponse 
response) throws IOException {
        Map < String, String > map = new HashMap < String, String > ();
        map.put("Message", message);
        map.put("Time", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
        String displayMessage = "{\"Message\":\"" + map.get("Message") + "\",\"Time\":\""+map.get("Time")+"\"}";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(displayMessage);
      }
    }
