package com.fly.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AAServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("aaServlet...init");
    }

    @Override
    public void service(InputStream ips, OutputStream ops) throws IOException {
        System.out.println("aaServlet...service");
        ops.write("I am from AAServlet!".getBytes());
        ops.flush();
    }

    @Override
    public void destroy() {

    }
}
