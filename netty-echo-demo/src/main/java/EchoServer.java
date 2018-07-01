import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 3:37 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class EchoServer {

    final ServerBootstrap serverBootstrap;
    final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    final NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);

    final int port;

    public static void main(String[] args){
        EchoServer server = new EchoServer(30000);
        server.bind();
    }

    public EchoServer(int port) {
        this.port = port;
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new EchoServerHandler());
                    }
                });
    }

    public void bind() {
        try {
            serverBootstrap.bind(port)
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
