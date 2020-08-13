package com.fy.Netty.protocolTCP;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收数据 并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("------服务器收到的信息---------");
        System.out.println("长度："+len+"内容"+new String(content, Charset.forName("utf-8")));
        System.out.println("服务器接收消息包数量="+(++count));

        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int length = responseContent.getBytes("utf-8").length;

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(responseContent.getBytes("utf-8"));

        ctx.writeAndFlush(messageProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
