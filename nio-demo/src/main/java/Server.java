import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-14
 * Time: 6:39 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Server {


    private ServerSocketChannel serverSocketChannel;
    private SelectorProvider selectorProvider;
    private Selector selector;
    private int port;
    private EventLoop childEventLoop;


    public Server(int port, EventLoop eventLoop) {
        this.selectorProvider = SelectorProvider.provider();
        this.port = port;
        this.childEventLoop = eventLoop;

    }

    public void open() {
        try {
            serverSocketChannel = selectorProvider.openServerSocketChannel();
            selector = selectorProvider.openSelector();
            serverSocketChannel.bind(new InetSocketAddress(port), 1 << 12);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT |
                    SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start() throws Exception {
        for (; ; ) {
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                }
                iterator.remove();
            }
        }
    }
}
