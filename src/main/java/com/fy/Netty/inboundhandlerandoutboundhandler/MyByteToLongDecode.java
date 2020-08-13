package com.fy.Netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecode extends ByteToMessageDecoder {
    /**
     *
     * @param ctx 上下文对象
     * @param in  入站的ByteBuf
     * @param out list集合 将解码器后的数据传给下一个handle
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
        System.out.println("解码完成");
    }
}
