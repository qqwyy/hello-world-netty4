package com.wyy.netty.demo4Codec.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class EchoClientHandler extends SimpleChannelInboundHandler {
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取服务端返回的数据buf
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        //将服务端返回的byte数组转换成字符串,并打印到控制台
        String body = new String(req, "UTF-8");
//        System.out.println("receive data from server :" + body);
        System.out.println("received from server:" + body + " counter:" + counter.addAndGet(1));
    }

    //发生异常,关闭链路
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
