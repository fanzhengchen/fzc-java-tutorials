import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-15
 * Time: 8:25 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class TestLock {

    int cnt = 0;
    SyncLock syncLock = new SyncLock();

    @Test
    public void testSync() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        int n = (int) (1e6);

        cnt = 0;
        for (int i = 0; i < n; ++i) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    syncLock.lock();
                    try {
                        ++cnt;
                    } finally {
                        syncLock.unlock();
                    }

                }
            });
        }
        Thread.sleep(7000);
        System.out.println(cnt);
        Assert.assertEquals(cnt, n);
    }
}
