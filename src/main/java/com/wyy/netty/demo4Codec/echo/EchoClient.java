package com.wyy.netty.demo4Codec.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 此例子可以展示粘包和半包问题
 */
public class EchoClient {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new EchoClient().connect(port, "127.0.0.1");
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
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();

            for (int i = 0; i < 100; i++) {
                //构造客户端发送的数据ByteBuf对象
                byte[] req = "你好,Netty!".getBytes();
                ByteBuf messageBuffer = Unpooled.buffer(req.length);
                messageBuffer.writeBytes(req);

                //向服务端发送数据
                ChannelFuture channelFuture = f.channel().writeAndFlush(messageBuffer);
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
