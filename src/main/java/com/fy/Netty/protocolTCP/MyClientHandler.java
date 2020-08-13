package com.fy.Netty.protocolTCP;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收数据 并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("------客户端收到的信息---------");
        System.out.println("长度："+len+"内容"+new String(content, Charset.forName("utf-8")));
        System.out.println("服务器接收消息包数量="+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("抛出异常"+cause.getMessage());
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <= 10; i++) {
            String str = "Fyyyyy";
            byte[] content = str.getBytes(Charset.forName("utf-8"));
            int length = str.getBytes(Charset.forName("utf-8")).length;

            //创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);

            ctx.writeAndFlush(messageProtocol);
        }
    }

}
