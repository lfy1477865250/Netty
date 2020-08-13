package com.fy.Nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

//聊天服务端
public class groupChatServer {
    private ServerSocketChannel listenChannel;
    private Selector selector;
    private static final int PORT = 6666;

    //初始化方法
    public groupChatServer() {
        try {
            //获取到监听器
            listenChannel = ServerSocketChannel.open();
            //得到选择器
            selector = Selector.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            //把listen注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //监听
    public void listen(){
        try {
            //循环处理
            while(true){
                int count = selector.select();
                if(count>0){
                    //得到selectedKeys集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出SelectionKey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if(key.isAcceptable()){
                            SocketChannel accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector,SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress()+"上线");
                        }
                        if (key.isReadable()){
                            readData(key);
                        }
                        //手动移除当前的selectionKey，防止重复操作
                        iterator.remove();
                    }
                }else{
                    //System.out.println("等待......");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发生异常处理....
        }
    }
    //读取客户端信息
    private void readData(SelectionKey key){
        //取到相关联的channel
        SocketChannel socketChannel = null;
        try {
            socketChannel = ((SocketChannel) key.channel());
            socketChannel.configureBlocking(false);
            //创建buffer

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if(count > 0){
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                System.out.println(" form  客户端 "+ msg);
                //向其他用户转发消息
                sendInfoToOtherClients(msg,socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线了");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //转发消息给其他用户
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException{
        System.out.println("消息转发中");
        for(SelectionKey key : selector.keys()){
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel!=self){
                //强转
                SocketChannel dest = (SocketChannel) targetChannel;
                //获取buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        groupChatServer groupChatServer = new groupChatServer();
        groupChatServer.listen();
    }
}
