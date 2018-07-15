import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

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
    private ExecutorService executorService =
            Executors.newFixedThreadPool(10);
   // ReentrantLock lock = new ReentrantLock(true);

    MyReentrantLock lock = new MyReentrantLock(true);
    @Test
    public void testSynchronizedLock() throws Exception {

        //final SynchronizedLock lock = new SynchronizedLock();
        count = 0;
        int n = 1000000;
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
        Thread.sleep(6008);
        System.out.println(count);

        Assert.assertTrue(n == count);
    }

    @Test
    public void testReenterLock() throws Exception {
        final ReenterLock lock = new ReenterLock();
        count = 0;
        Runnable x = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    count += 100;
                    System.out.println("count " + count);
                } finally {
                    lock.unlock();
                }
            }
        };
        Runnable y = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    count += 1000;
                    System.out.println("count " + count);
                    x.run();
                } finally {
                    lock.unlock();
                }
            }
        };
        y.run();
        System.out.println(count);
    }


    @Test
    public void testMyBlockingQueue() throws Exception {
        ArrayBlockingQueue<Integer> queue =
                new ArrayBlockingQueue<>();
        int n = 100;
        for (int i = 0; i < n; ++i) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    queue.push(j);
                }
            });
        }
        Thread.sleep(2000);
        Assert.assertEquals(queue.size(), n);

        while (!queue.isEmpty()){
            System.out.println(queue.pop());
        }
    }

    @Test
    public void nonFairCheck(){


        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    test();
                }

            }
        });

        threadA.setName("A");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    test();
                }
            }
        });

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    test();
                }
            }
        });

        threadC.setName("C");
        threadB.setName("B");
        threadA.start();
        threadB.start();
        threadC.start();
    }

    void test() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread());
        }finally {
            lock.unlock();
        }
    }
}
