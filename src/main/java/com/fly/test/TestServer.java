package com.fly.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    //  定義一個變量,存放服務端WebContent目錄的絕對路徑
    public static String WEB_ROOT = System.getProperty("user.dir") + "\\" + "WebContent";
    //  定義靜態變量,用於存放本次請求的靜態頁面名稱
    private static String url = "";

    public static void main(String[] args) throws IOException {
//      創建ServerSocket,監聽本機的8080端口,等待來自客戶端的請求
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream ips = null;
        OutputStream ops = null;
        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                //獲取到客戶端對應的Socket
                socket = serverSocket.accept();
                //獲取到輸入流對象
                ips = socket.getInputStream();
                //獲取到輸出流對象
                ops = socket.getOutputStream();
                //獲取HTTP協議的請求部分,擷取客戶端要訪問的資源名稱,將這個資源名稱賦值給url
                parse(ips);
                //發送靜態資源
                sendStaticResource(ops);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //釋放資源
            if (ips != null) {
                ips.close();
            }
            if (ops != null) {
                ips.close();
            }
            if (socket != null) {
                ips.close();
            }
        }
    }

    private static void parse(InputStream ips) throws IOException {
        //定義一個變量,存放HTTP協議請求部分數據
        StringBuffer content = new StringBuffer();
        //定義一個數組,存放HTTP協議請求部分數據
        byte[] buffer = new byte[2048];
        //定義一個變量i,代表讀取數據到數組中之後,數據量的大小
        int i = -1;
        //讀取客戶端發送過來的數據,將數據讀取到字串數組buffer中,i代表讀取數據量的大小 311字節
        i = ips.read(buffer);
        //遍歷字節數組,將數組中的數據追加到content變量中
        for (int j = 0; j < i; j++) {
            content.append((char) buffer[j]);
        }
        //打印HTTP協議請求部分數據
        System.out.println(content);
        //擷取客戶端要請求的資源路徑demo.html,複製給url
        parseUrl(content.toString());
    }

    private static void parseUrl(String content) {
        //定義2個變量,存放請求行的2個空格位置
        int index1, index2;
        //獲取http請求部分第1個空格位置
        index1 = content.indexOf(" ");
        index2 = content.indexOf(" ", index1 + 1);
        //獲取http請求部分第2個空格位置
        if (index2 > index1) {
            //擷取字符串獲取到本次請求資源的名稱
            url = content.substring(index1 + 2, index2);
        }

        //打印本次請求靜態資源名稱
        System.out.println(url);
    }

    private static void sendStaticResource(OutputStream ops) throws IOException{
        //定義一個字節數組,用於存放本次請求的靜態資源demo01.html內容
        byte[] bytes = new byte[2048];
        //定義一個文件輸入流,用戶獲取靜態資源demo01.html中的內容
        FileInputStream fis = null;
        try {
            //創建文件對象File,代表本次要請求的資源demo01.html
            File file = new File(WEB_ROOT, url);
            //如果文件存在
            if (file.exists()) {
                //向客戶端輸出HTTP協議的回應行/回應頭
                ops.write("HTTP/1.1 200 OK\n".getBytes());
                ops.write("Server:apache-Coyote/1.1\n".getBytes());
                ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                ops.write("\n".getBytes());
                //獲取到文件輸入流對象
                fis = new FileInputStream(file);
                //讀取靜態資源demo01.html中的內容到數組中
                int ch = fis.read(bytes);
                while (ch != -1) {
                    //將讀取到數組中的內容通過輸出流送到客戶端
                    ops.write(bytes, 0, ch);
                    ch = fis.read(bytes);
                }
            } else {
                //如果文件不存在
                //向客戶端回應文件不存在消息
                ops.write("HTTP/1.1 404 not found\n".getBytes());
                ops.write("Server:apache-Coyote/1.1\n".getBytes());
                ops.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                ops.write("\n".getBytes());
                String errorMessage = "file not found";
                ops.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //釋放文件輸入流對象
            if (fis != null) {
                fis.close();
            }
        }
    }
}
