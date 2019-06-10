package com.fly.test;

import java.io.*;
import java.net.*;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStream ips = null;
        OutputStream ops = null;
        try {
//          1.建立一個Socket對象,連結itcast.cn域名的80端口
            socket = new Socket("www.itcast.cn",80);
//          2.獲取到輸入流對象
            ips = socket.getInputStream();
//          3.獲取到輸出流對象
            ops = socket.getOutputStream();
//          4.將HTTP協議的請求部分發送到服務端 /subject/about/index.html
            ops.write("GET /subject/about/index.html HTTP/1.1\n".getBytes());
            ops.write("HOST:www.itcast.cn\n".getBytes());
            ops.write("\n".getBytes());
//          5.讀取來自服務端的數據打印到控制台
            int i = ips.read();
            while (i != -1) {
                System.out.print((char)i);
                i = ips.read();
            }
//          6.釋放資源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ips) {
                ips.close();
            }
            if (null != ops) {
                ops.close();
            }
            if (null != socket) {
                socket.close();
            }
        }
    }
}
