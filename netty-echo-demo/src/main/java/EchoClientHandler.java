import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 3:37 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ByteBuf byteBuf = Unpooled.buffer(1<<12);
        byteBuf.writeBytes("netty-echo-demo".getBytes());
        log.info("channel active {}", ctx);
        ctx.writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        log.info("channel read {}", msg.toString());
        ctx.executor()
                .schedule(new Runnable() {
                    @Override
                    public void run() {
                        ctx.write(msg);
                    }
                }, 10, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
        log.info("channel read complete");
    }
}
