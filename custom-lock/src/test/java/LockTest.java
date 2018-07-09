import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 1:53 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class LockTest {


    private volatile int count;

    @Test
    public void testSynchronizedLock() throws Exception {
        ExecutorService executorService =
                Executors.newFixedThreadPool(10);
        final SynchronizedLock lock = new SynchronizedLock();
        count = 0;
        int n = 10000;
        for (int i = 0; i < n; ++i) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        ++count;
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }
        Thread.sleep(2008);
        System.out.println(count);

        Assert.assertTrue(n == count);
    }
}
