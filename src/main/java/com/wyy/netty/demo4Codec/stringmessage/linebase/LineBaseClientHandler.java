package com.wyy.netty.demo4Codec.stringmessage.linebase;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.atomic.AtomicInteger;

public class LineBaseClientHandler extends SimpleChannelInboundHandler {
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String content = (String) msg;
        System.out.println("received from server:" + content + " counter:" + counter.addAndGet(1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
