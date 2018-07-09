import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


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

    private static final int MAX_DELAY = 1 << 29;

    final NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
    final Bootstrap bootstrap;
    final InetSocketAddress address;
    final AtomicInteger connectDelaySeconds = new AtomicInteger(1);

    public static void main(String[] args){
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EchoClient client = new EchoClient(new InetSocketAddress(host,port));
        client.connect();
    }


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
//                        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
//                        pipeline.addLast("decoder", new StringDecoder());
//                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new EchoClientHandler());
                    }
                });
    }


    public void connect() {
        try {
            bootstrap.connect(address).sync().addListener(future -> {
                if(future.isSuccess()){
                    connectDelaySeconds.set(1);
                }
            });
        } catch (InterruptedException e) {
            log.error("disconnected");
            e.printStackTrace();
        } finally {
            int oldValue = connectDelaySeconds.intValue();
            int newValue = Math.min(oldValue << 1, MAX_DELAY);
            connectDelaySeconds.compareAndSet(oldValue,newValue);
            workerGroup.schedule(new Runnable() {
                @Override
                public void run() {
                    log.error("try reconnect {}",connectDelaySeconds.get());
                    connect();
                }
            }, newValue, TimeUnit.SECONDS);
        }
    }
}
