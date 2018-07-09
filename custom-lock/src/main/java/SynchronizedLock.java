import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 1:53 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class SynchronizedLock extends AbstractQueuedSynchronizer {


    public void lock() {
        acquire(1);
    }

    public void unlock() {
        release(1);
    }


    @Override
    protected boolean tryAcquire(int arg) {
        return getState() == 0 &&
                compareAndSetState(0, arg);
    }


    @Override
    protected boolean tryRelease(int arg) {
        return getState() == arg &&
                compareAndSetState(arg, 0);
    }
}
