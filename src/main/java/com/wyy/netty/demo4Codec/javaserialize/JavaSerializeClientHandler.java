package com.wyy.netty.demo4Codec.javaserialize;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class JavaSerializeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //channel建立之后,向服务端发送消息,需要注意的是这里写入的消息是完整的UserInfo对象
        //因为后续会被ObjectEncoder进行编码处理
        UserInfo user = new UserInfo("zhangsan",10000,"hello@163.com","13911111111","remark info");
        ctx.writeAndFlush(user);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
