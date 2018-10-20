package com.wyy.netty.demo4Codec.stringmessage.delimiterbase;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class DelimiterBaseClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = Logger.getLogger(DelimiterBaseClientHandler.class.getName());
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取服务端返回的数据,并打印到控制台
        String content =  msg;
        System.out.println("received from server:" + content + " counter:" + counter.addAndGet(1));
    }

    //发生异常,关闭链路
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
