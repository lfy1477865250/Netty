package com.fy.Netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //出战的编码器
        pipeline.addLast(new MyByteToLongEncode());
        //入站的解码器
        pipeline.addLast(new MyByteToLongDecode());
        pipeline.addLast(new MyClientHandler());
    }
}
