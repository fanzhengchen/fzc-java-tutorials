import io.reactivex.internal.subscriptions.ScalarSubscription;
import org.reactivestreams.Subscriber;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 2:23 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class FlowableJust<T> extends Flowable<T> {

    private T value;

    public FlowableJust(final T value) {
        this.value = value;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new ScalarSubscription<T>(subscriber, value));
    }
}
