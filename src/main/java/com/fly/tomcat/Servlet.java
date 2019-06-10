package com.fly.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//所有服務端的JAVA小程序要實現的接口
public interface Servlet {
    //初始化
    public void init();
    //服務
    public void service(InputStream ips, OutputStream ops) throws IOException;
    //銷毀
    public void destroy();
}
