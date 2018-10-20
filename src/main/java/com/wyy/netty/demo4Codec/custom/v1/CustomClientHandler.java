package com.wyy.netty.demo4Codec.custom.v1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class CustomClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 100; i++) {
            //channel建立之后,向服务端发送消息,需要注意的是这里写入的消息是完整的UserInfo对象
            UserInfo user = new UserInfo("zhangsan",10000,"hello@163.com","13911111111","remark info");
            ctx.writeAndFlush(user);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
