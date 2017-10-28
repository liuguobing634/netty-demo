package netty.less7;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import netty.common.Bind;


public class SubReqServer {

    public void bind(int port) throws Exception {
        Bind.bind(port, ChannelOption.SO_BACKLOG, 100, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                ch.pipeline().addLast(new SubReqServerHandler());
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new SubReqServer().bind(8080);
    }

}
