package com.wyy.netty.demo4Codec.stringmessage.delimiterbase;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DelimiterBaseClient {

    //字符串分隔符
    private static final String delimiter_tag = "@#";

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new DelimiterBaseClient().connect(port, "127.0.0.1");
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
                            ByteBuf delimiter = Unpooled.copiedBuffer(delimiter_tag.getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new StringDecoder());
                            //配置客户端处理网络I/O事件的类
                            ch.pipeline().addLast(new DelimiterBaseClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();

            //循环发送100次
            for (int i = 0; i < 100; i++) {
                String content = "你好,Netty!" + delimiter_tag;
//                byte[] req = content.getBytes();
//                ByteBuf messageBuffer = Unpooled.buffer(req.length);
//                messageBuffer.writeBytes(req);

                //向服务端发送数据
                ChannelFuture channelFuture = f.channel().writeAndFlush(content);
                channelFuture.syncUninterruptibly();
            }
            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅退出,释放NIO线程组
            group.shutdownGracefully();
        }
    }
}
