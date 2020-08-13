package com.fy.Netty.inboundhandlerandoutboundhandler;

import com.fy.Netty.hreatBeat.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入解码器
        pipeline.addLast(new MyByteToLongDecode());
        //加入编码器
        pipeline.addLast(new MyByteToLongEncode());
        //加入事件处理
        pipeline.addLast(new MyServerHandler());
    }
}
