package com.fy.Netty.DubboRPC.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; //上下文
    private String result; //返回的结果
    private String para;//客户端调用方法时，传入的参数

    //与服务器创建连接之后 就会被调用
    //创建连接之后 只会触发一次
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    //收到服务器的数据后 调用方法
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); //唤醒线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用，发送数据给服务器 等待被唤醒
    //channelRead 和 call 方法 是同步的
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        //等待
        wait();
        //服务器放回的结果
        return result;
    }
    public void setPara(String para){
        this.para = para;
    }
}
