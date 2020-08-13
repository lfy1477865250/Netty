package com.fy.Nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapperByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1:FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2:可以直接修改的起始位置
         * 参数3：映射内存的大小，即1.txt的多少字节映射到内存
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0,(byte) 'H');
        map.put(1,(byte) '6');

        randomAccessFile.close();
        System.out.println("修改成功...");
    }
}
