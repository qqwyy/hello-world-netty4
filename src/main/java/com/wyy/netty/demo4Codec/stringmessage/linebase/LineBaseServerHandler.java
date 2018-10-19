package com.wyy.netty.demo4Codec.stringmessage.linebase;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class LineBaseServerHandler extends SimpleChannelInboundHandler<String> {
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String content =  msg;
        System.out.println("received from client:" + content + " counter:" + counter.addAndGet(1));
        //将数据重新发送到客户端
        content += "\n";

//      如果没有指定 ch.pipeline().addLast(new StringEncoder()); 则发送需要使用byteBuf
//      ByteBuf echo = Unpooled.copiedBuffer(content.getBytes());
//        ctx.writeAndFlush(echo);
        ctx.writeAndFlush(content);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
