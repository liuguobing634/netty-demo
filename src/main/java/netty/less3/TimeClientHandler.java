package netty.less3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by 刘国兵 on 2017/10/15.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("aaa");
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }
}
