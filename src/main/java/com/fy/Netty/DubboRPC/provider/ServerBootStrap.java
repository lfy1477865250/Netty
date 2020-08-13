package com.fy.Netty.DubboRPC.provider;

import com.fy.Netty.DubboRPC.netty.NettyServer;

//会启动服务提供者 就是nettyServer
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7001);
    }
}
