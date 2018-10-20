package com.wyy.netty.demo4Codec.stringmessage.fixedlength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;


public class FixedLengthServerHandler extends SimpleChannelInboundHandler<String> {

    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);


    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //接收客户端发送的字符串,并打印到控制台
        String content =  msg;
        System.out.println("received from client:" + content + " counter:" + counter.addAndGet(1));

        //将数据重新发送到客户端
//        ByteBuf echo = Unpooled.copiedBuffer(content.getBytes());
//        ctx.writeAndFlush(echo);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
