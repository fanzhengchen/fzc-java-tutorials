import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 10:23 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private volatile int count;
    private Object[] items;
    private ReenterLock lock;

    public ArrayBlockingQueue() {
        count = 0;
        items = new Object[12];
        lock = new ReenterLock();
    }


    @Override
    public T pop() {
        lock.lock();
        try {
            return count == 0 ? null : (T) items[--count];
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void push(T t) {
        lock.lock();
        try {
            if (count == items.length) {
                Object[] newItems = new Object[count << 1];
                System.arraycopy(items, 0, newItems, 0, count);
                items = newItems;
            }
            items[count++] = t;
        }finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }
}
