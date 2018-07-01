import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 5:05 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class ThreadPool {


    private ThreadFactory threadFactory;
    private int nThreads;
    private Thread[] threads;
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    public ThreadPool(int nThreads) {

        threadFactory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                return thread;
            }
        };
        threads = new Thread[nThreads];

        for (int i = 0; i < nThreads; ++i) {
            threads[i] = threadFactory.newThread(new Runnable() {
                public void run() {
                    for (; ; ) {
                        Runnable task = queue.poll();
                        if (task != null) {
                            task.run();
                        }
                    }
                }
            });
            threads[i].setName("fzc Thread " + i);
            threads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        queue.offer(runnable);
    }
}
