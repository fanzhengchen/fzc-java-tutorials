/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-10
 * Time: 10:22 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public interface BlockingQueue<T> {

    T pop();

    void push(T t);

    boolean isEmpty();

    int size();


}
