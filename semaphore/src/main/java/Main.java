import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-04
 * Time: 5:57 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {


    public static void main(String[] args) {
        try {
            new Main().main();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    Semaphore semaphore = new Semaphore(4);

    void main() throws Exception {

        int n = 10;
        final CountDownLatch latch = new CountDownLatch(1);
        Thread[] threads = new Thread[n];
        ConcurrentSkipListSet<Integer> have = new ConcurrentSkipListSet<>();

        AtomicInteger cnt = new AtomicInteger(4);

        for (int i = 0; i < n; ++i) {
            have.add(i);
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    for (; ; ) {
                        try {
                            semaphore.acquire();
                            cnt.decrementAndGet();
                            System.out.println(Thread.currentThread().getName() + " :start " + cnt.get());
                            Thread.sleep(20000);

                            cnt.incrementAndGet();
                            System.out.println(Thread.currentThread().getName() + " :end " + cnt.get());
                            semaphore.release();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
            threads[i].setName("thread " + i);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        // latch.await();
    }
}
