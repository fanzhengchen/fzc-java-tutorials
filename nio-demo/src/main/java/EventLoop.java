import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-14
 * Time: 6:45 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class EventLoop implements Executor {

    Thread thread;
    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
    BlockingQueue<Runnable> delay = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
    EventLoop next;
    Selector selector;

    SocketChannel socketChannel;


    public EventLoop() {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() throws Exception {
        SelectorProvider provider = SelectorProvider.provider();
        selector = provider.openSelector();
    }

    private void select() {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();

        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {

            }

            if (key.isWritable()) {

            }

            iterator.remove();
        }
    }

    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    safeRun(queue.poll());
                }
            }
        });
        thread.start();
    }

    public EventLoop next() {
        return next;
    }

    public void setNext(EventLoop eventLoop) {
        this.next = eventLoop;
    }


    @Override
    public void execute(Runnable command) {
        queue.offer(command);
    }

    private void safeRun(Runnable runnable) {
        try {
            Optional.ofNullable(runnable)
                    .ifPresent(r -> r.run());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
