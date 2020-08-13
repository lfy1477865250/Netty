package com.fy.Netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

//处理器
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {
    //读取客户端发送过来的信息

    /**
     *ctx 上下文对象,含有 管道pipeline,通道channle 地址
     * msg:客户端发来的对象
     */
/*    @Override
    public void channelRead(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("server ctx:"+ctx);
        //将msg转成一个ByteBuf(netty提供)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息:"+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址:"+ctx.channel().remoteAddress());
    }*/

    protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
        System.out.println("客户端发送的数据 id="+msg.getId()+"名字="+msg.getName());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓冲并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
