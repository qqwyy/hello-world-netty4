package com.wyy.netty.sample2chatsocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class MyChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
          ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
            ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new MyChatClientHandler());
    }
}
