package com.wyy.netty.sample1socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MySocketClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
//      ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//      ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
//      ch.pipeline().addLast(new LengthFieldPrepender(4));
        ch.pipeline().addLast("decoder", new StringDecoder());//new StringDecoder(CharsetUtil.UTF_8)
        ch.pipeline().addLast("encoder", new StringEncoder());
        ch.pipeline().addLast(new MySocketServerHandler());
    }

}

