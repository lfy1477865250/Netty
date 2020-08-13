package com.fy.Netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandle extends ChannelInboundHandlerAdapter {
    //当通道就绪就会触发此事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("Fyyyy").build();
        ctx.writeAndFlush(student);
//        System.out.println("client :"+ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server!!", CharsetUtil.UTF_8));
    }
    //当通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转成一个ByteBuf(netty提供)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器发送的消息:"+buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址:"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
