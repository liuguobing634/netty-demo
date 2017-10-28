package netty.less6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.concurrent.ExecutorService;


/**
 * Created by 刘国兵 on 2017/10/21.
 */
public class HttpServerHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 对请求解码
        ch.pipeline().addLast("http-request-decoder", new HttpRequestDecoder());
        // 防止粘包和拆包
        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
        // 对响应编码
        ch.pipeline().addLast("http-response-encoder",new HttpResponseEncoder());
        // 分块传输
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 传入内容的分析与回应，如果有比较耗时的任务，可以将其放到单独的线程池中
        ch.pipeline().addLast(new DefaultEventExecutorGroup(150),"server-handler", new InnerHttpServerHandler());
    }

    private class InnerHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


        private ExecutorService service;

        public InnerHttpServerHandler() {
//            this.service = Executors.newCachedThreadPool();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            // 对每一个请求都用新线程去处理
            // 内部有使用线程池的
            System.out.println(Thread.currentThread());
            if (!msg.decoderResult().isSuccess()) {
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                return;
            }
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes("hello".getBytes());
            if (msg.uri().contains("/wait")) {
                Thread.sleep(60000);
            }
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, buf))
                    .addListeners(ChannelFutureListener.CLOSE);

        }
    }
}
