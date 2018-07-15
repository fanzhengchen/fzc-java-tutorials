import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-15
 * Time: 8:17 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class SyncLock {
    private Sync sync;


    class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            Thread current = Thread.currentThread();
            int state = getState();
            if (current != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);

            return true;
        }


    }

    public SyncLock() {
        sync = new Sync();
    }

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

}
