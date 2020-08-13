package com.fy.Netty.hreatBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String str = null;
            switch (event.state()){
                case ALL_IDLE:
                    str = "读写空闲";
                    break;
                case READER_IDLE:
                    str = "读空闲";
                    break;
                case WRITER_IDLE:
                    str = "写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时时间"+str);
            System.out.println("系统正在处理....");
        }
    }
}
