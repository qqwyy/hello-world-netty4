package com.wyy.netty.sample2chatsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

public class MyChatServerHandler  extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel ch = ctx.channel();
        channelGroup.forEach(h->{
            if(ch!=h){
                h.writeAndFlush(ch.remoteAddress()+"-->"+ch.localAddress()+" 广播发送的消息："+msg+"\r\n");
            }else{
                ch.writeAndFlush("【自己】发送的消息："+msg+"\r\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        channelGroup.writeAndFlush("-->事件：客户端 新加入连接："+ch.remoteAddress()+"\r\n");
        channelGroup.add(ch);
//        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        channelGroup.writeAndFlush("-->事件：客户端 断开连接："+ch.remoteAddress()+" 现有数量："+channelGroup.size()+"个\r\n");
//        channelGroup.remove(ch);
//        channelGroup.add(ch);
//        super.handlerRemoved(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
//        channelGroup.writeAndFlush("-->事件：客户端上线："+ch.remoteAddress()+"\r\n");
        System.out.println("-->事件：客户端上线："+ch.remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
//        channelGroup.writeAndFlush("-->事件：客户端下线："+ch.remoteAddress()+"\r\n");
        System.out.println("-->事件：客户端下线："+ch.remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
