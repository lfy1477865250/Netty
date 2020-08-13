package com.fy.Bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Bio方式
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //创建一个线程池
        //如果有客户端连接 就创建一个线程 与之通信

        //创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建serversocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务启动了");
        while (true){
            //监听 等待客户端连接
           final Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            //创建一个线程 与之通信
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handle(accept);
                }
            });
        }
    }
    //编写一个handle方法
    public static void handle(Socket socket) {
        try {
            System.out.println("线程id:"+Thread.currentThread().getId()+"  线程名字"+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //获取字节流
            InputStream inputStream = socket.getInputStream();
            //循环读取客服端发来的请求
            while (true){
                System.out.println("线程id:"+Thread.currentThread().getId()+"  线程名字"+Thread.currentThread().getName());
                //一byte的形式、大小读取放在缓存区
                int read = inputStream.read(bytes);
                if(read!=-1){
                    //输出客户端发的数据
                    //转成String形式输出
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
