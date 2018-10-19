package com.wyy.netty.demo4Codec.stringmessage.linebase;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class LineBaseClient {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new LineBaseClient().connect(port, "127.0.0.1");
    }

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //设置LineBasedFrameDecoder处理器
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            //设置StringDecoder处理器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new LineBaseClientHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();
            //循环发送100次
            for (int i = 0; i < 100; i++) {
                //构造客户端发送的数据ByteBuf对象
                String content = "你好,Netty!\n";

//                如果没有指定 ch.pipeline().addLast(new StringEncoder()); 则发送需要使用byteBuf
//                byte[] req = content.getBytes();
//                ByteBuf messageBuffer = Unpooled.buffer(req.length);
//                messageBuffer.writeBytes(req);

                //向服务端发送数据
                ChannelFuture channelFuture = f.channel().writeAndFlush(content);
                channelFuture.syncUninterruptibly();
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
