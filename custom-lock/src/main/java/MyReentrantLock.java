import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-11
 * Time: 10:46 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class MyReentrantLock {


    private final Sync sync;


    public MyReentrantLock(boolean isFair) {

        sync = isFair ? new FairSync() : new NonFairSync();
    }


    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryRelease(int arg) {

            Thread current = Thread.currentThread();
            if (current != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            int c = getState();
            int remain = c - 1;

            setState(remain);

            if (remain == 0) {
                setExclusiveOwnerThread(null);
                return true;
            }

            return false;
        }


    }

    private static class FairSync extends Sync {

        @Override
        protected boolean tryAcquire(int arg) {

            Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() && compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (getExclusiveOwnerThread() == current) {
                setState(c + 1);
                return true;
            }
            return false;
        }
    }

    private static class NonFairSync extends Sync {
        @Override
        protected boolean tryAcquire(int arg) {
            Thread current = Thread.currentThread();

            int c = getState();


            if (c == 0) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (getExclusiveOwnerThread() == current) {
                setState(c + 1);
                return true;
            }
            return false;
        }
    }

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

}
