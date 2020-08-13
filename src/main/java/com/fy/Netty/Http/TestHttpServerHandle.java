package com.fy.Netty.Http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * httpObject 客户端与服务器相互的数据被封装成httpObject
 */
public class TestHttpServerHandle extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            //判断msg是不是httpRequest
        if(msg instanceof HttpRequest){
            System.out.println("msg 类型是"+msg.getClass());
            System.out.println("客户端地址"+ctx.channel().remoteAddress());
            //每一个浏览器对应一个hashcode 浏览器每次刷新都会更新hashcode
            System.out.println("pipeline的hashcode   "+ctx.pipeline().hashCode());
            System.out.println("TestHttpServerHandle的hashcode   "+this.hashCode());
            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("不对favicon.ico进行响应!");
                return;
            }
            //回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
            //构造一个http应用 既httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //把response返回
            ctx.writeAndFlush(response);
        }
    }
}
