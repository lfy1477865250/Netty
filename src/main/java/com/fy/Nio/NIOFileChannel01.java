package com.fy.Nio;

import com.sun.org.apache.bcel.internal.util.ClassPath;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello,Netty!";
        //创建一个输出流File
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\file01.txt");

        //通过fileOutputStream获取对应的FileChannel
        //这个fileChannel 真实类型是 FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入不byteBuffer
        byteBuffer.put(str.getBytes());

        //对byteBuffer进行反转
        byteBuffer.flip();

        //从channel写入
        channel.write(byteBuffer);
    }
}
