package com.wyy.netty.demo2chatsocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyCharClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new MyChatClientInitializer());

            ChannelFuture future = b.connect(HOST, PORT).sync();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                future.channel().writeAndFlush(br.readLine()+"\r\n");
            }
//            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
