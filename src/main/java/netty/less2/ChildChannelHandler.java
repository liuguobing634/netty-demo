package netty.less2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * Created by 刘国兵 on 2017/10/15.
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
        sc.pipeline().addLast(new StringDecoder());
        sc.pipeline().addLast(new TimeServerHandler());
    }
}
