import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 3:36 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        final int port = 30000;
        EchoClient client = new EchoClient(new InetSocketAddress("localhost", port));
        EchoServer server = new EchoServer(port);
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("server bind");
                server.bind();
            }
        }).start();
        log.info("client connect");
        client.connect();
    }
}
