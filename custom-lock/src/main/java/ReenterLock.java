import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 2:03 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class ReenterLock extends AbstractQueuedSynchronizer {


    public void lock() {
        acquire(1);
    }

    public void unlock() {
        release(1);
    }


    @Override
    protected boolean tryRelease(int arg) {
        Thread current = Thread.currentThread();
        int state = getState();
        int nextValue = state - arg;
        if (current != getExclusiveOwnerThread()) {
            throw new IllegalMonitorStateException("lock unlock pair");
        }

        setState(nextValue);
        if(nextValue == 0) {
            setExclusiveOwnerThread(null);
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryAcquire(int arg) {
        Thread current = Thread.currentThread();
        int state = getState();
        if (state > 0 && getExclusiveOwnerThread() == current) {
//            if (compareAndSetState(state, state + arg)) {
//                return true;
//            }
            setState(state + arg);
            return true;
        } else if (state == 0 && compareAndSetState(0, arg)) {
            setExclusiveOwnerThread(current);
            return true;
        }
        return false;
    }
}
