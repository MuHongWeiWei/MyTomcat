package com.fly.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BBServlet implements Servlet{
    @Override
    public void init() {
        System.out.println("bbServlet...init");
    }

    @Override
    public void service(InputStream ips, OutputStream ops) throws IOException {
        System.out.println("bbServlet...service");
        ops.write("I am from BBServlet!".getBytes());
        ops.flush();
    }

    @Override
    public void destroy() {

    }
}
