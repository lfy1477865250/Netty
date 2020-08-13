package com.fy.Nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scatting:将数据写入到buffer时，可以采用buffer数组，依次写入【分散】
 * Gathering:从buffer读取数据时，可以采用buffer数据，依次读 【聚合】
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        //使用ServerSocketChannel socketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口并且启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        
        //等待客户端链接
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("客户端已连接!!");
        int messageLength = 8;

        //循环读取
        while (true){
            long byteRead = 0;
            while (byteRead<messageLength){
                long l = socketChannel.read(byteBuffers);
                System.out.println("l的大小"+l);
                //累计读取的字节数
                byteRead += l;
                //使用流打印 看看buffer的position limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position() + ", limit="+buffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
            //将buffer输出
            long byteWrite = 0;
            while (byteWrite<messageLength){
                long w = socketChannel.write(byteBuffers);
                System.out.println("w的大小"+w);
                byteWrite += w;
            }
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead = "+byteRead+" byteWrite = "+ byteWrite + " messageLength = "+messageLength);
        }


    }
}
