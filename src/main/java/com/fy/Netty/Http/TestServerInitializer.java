package com.fy.Netty.Http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管内加入处理器
        ChannelPipeline pipeline = ch.pipeline();
        //添加处理的http的编码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加handle
        pipeline.addLast("MyTestHttpServerHandle",new TestHttpServerHandle());
    }
}
