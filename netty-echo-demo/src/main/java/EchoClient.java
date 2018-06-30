import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
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
public class EchoClient {


    final NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
    final Bootstrap bootstrap;
    final InetSocketAddress address;


    public EchoClient(InetSocketAddress address) {
        this.address = address;
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new EchoClientHandler());
                    }
                });
    }


    public void connect() {
        try {
            bootstrap.connect(address).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.schedule(new Runnable() {
                @Override
                public void run() {
                    connect();
                }
            }, 3, TimeUnit.SECONDS);
        }
    }
}
