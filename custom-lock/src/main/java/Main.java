import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.sql.SQLOutput;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 1:52 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) {

        new Main().main();
    }

    MyReentrantLock lock = new MyReentrantLock(true);

    void main() {


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
