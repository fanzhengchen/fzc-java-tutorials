import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 5:05 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {


    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ThreadPool threadPool = new ThreadPool(2);

        threadPool.execute(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread() + " aaaa");
            }
        });
        for (int i = 0; i < 10; ++i) {
            final int j = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(j + " " + Thread.currentThread());
                }
            });
        }
        System.out.println(Thread.currentThread());
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
