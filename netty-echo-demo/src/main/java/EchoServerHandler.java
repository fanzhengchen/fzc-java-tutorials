import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

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
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("server channel active");
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        log.info("server channel read {}", msg);

        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(msg);
            }
        }, 5, TimeUnit.SECONDS);
    }
}
