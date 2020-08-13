package com.fy.Netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        //客户端的事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup();
        //客户端启动对象
        Bootstrap bootstrap = new Bootstrap();

        //设置相关参数
        try {
            bootstrap.group(group) //设置线程组
                .channel(NioSocketChannel.class) //设置客户端通道的实现类
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //加入编码器
                        ch.pipeline().addLast("encode",new ProtobufEncoder());
                        ch.pipeline().addLast(new NettyClientHandle());  //加入处理器
                    }
                });
            System.out.println("客户端 ok");
            //启动客户端去连接服务器端
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 7000).sync();
            cf.channel().closeFuture().sync(); //给关闭通道进行监听
        } finally {
            group.shutdownGracefully();
        }


    }
}
