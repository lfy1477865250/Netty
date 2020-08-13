package com.fy.Nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOSelectorServer {
    public static void main(String[] args) throws Exception {
        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个端口 进行监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector关心事件 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //等待客户端连接
        while (true){
            if(selector.select(1000) == 0){
                System.out.println("无连接，服务器等待1秒");
                continue;
            }
            //如果返回的>0，就获取到相关的selectionKey集合
            //1.如果返回的>0， 表示已经获取到关注的事件
            //2. selector. selectedKeys() 返回关注事件的集合
            //通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历Set<SelectionKey> 使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                System.out.println("key="+key);
                //根据key对相关事件进行处理
                if(key.isAcceptable()){
                    //如果是OP_ACCEPT 有新的客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("第一次连接");
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){
                    //如果发生 OP_READ
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获得该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端"+new String(buffer.array()));
                }
                //手动移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
