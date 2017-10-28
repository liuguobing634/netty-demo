package netty.less2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by 刘国兵 on 2017/10/15.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter  = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("The time server receive order : " + body + "; the counter is : " + ++ counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD REQUEST";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resq = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resq);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
