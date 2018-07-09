

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-02
 * Time: 4:55 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        final ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();


        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds != null) {
                    ThreadInfo[] threadInfos = mxBean.getThreadInfo(threadIds);
                    for (ThreadInfo info : threadInfos) {
                        System.err.println(info.getBlockedCount() + " " + info.getThreadId());
                    }
                }

            }
        }, 0, 5, TimeUnit.SECONDS);

        System.out.println("llll");
        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void main() throws Exception {

        final ReentrantLock firstLock = new ReentrantLock();
        final ReentrantLock secondLock = new ReentrantLock();
        Condition firstCondition = firstLock.newCondition();
        Condition secondCondition = secondLock.newCondition();

//        Thread firstThread = createThread(lock1, lock2);
//        Thread secondThread = createThread(lock2, lock1);

        Thread firstThread = createThread(firstLock, firstCondition, secondLock, secondCondition);
        Thread secondThread = createThread(secondLock, secondCondition, firstLock, firstCondition);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
        System.out.println("end dead lock failed!!!");
    }

    private static Thread createThread(final ReentrantLock firstLock, final Condition firstCondition,
                                       final ReentrantLock secondLock, final Condition secondCondition) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                firstLock.lock();
                try {
                    firstCondition.await(3, TimeUnit.SECONDS);
                    secondLock.lock();
                    try {
                        secondCondition.signal();
                    } finally {
                       secondLock.unlock();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    firstLock.unlock();
                }
            }
        });
    }


    private static Thread createThread(final Object lock1, final Object lock2) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("lock " + lock1);
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {
                        System.out.println("lock " + lock2);
                    }

                }
            }
        });
    }
}
