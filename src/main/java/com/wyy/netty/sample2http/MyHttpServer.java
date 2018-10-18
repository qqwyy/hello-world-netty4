package com.wyy.netty.sample2http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class MyHttpServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
//                            // 服务端，对请求解码
//                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
//                            // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
//                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
//                            // 服务端，对响应编码
//                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
//                            // 块写入处理器
//                            ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                            // 自定义服务端处理器

                            ch.pipeline().addLast("httpServerCodec", new HttpServerCodec());
                            ch.pipeline().addLast("fileServerHandler", new MyHttpServerHandler());

                        }
                    });
            ChannelFuture channeFuture = serverBootstrap.bind(8899).sync();
            System.out.println("服务器启动！");
            channeFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
