package com.fy.Nio;

import java.nio.IntBuffer;

public class NIOServer {
    public static void main(String[] args) {
        //创建一个IntBuffer 大小为2 既可以存放5个Int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //intBuffer.capacity()表示大小
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }

        //将buffer进行转换 读写切换
        intBuffer.flip();

        //输出
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
