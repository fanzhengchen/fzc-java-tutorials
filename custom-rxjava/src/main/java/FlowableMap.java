
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 2:40 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class FlowableMap<T, U> extends Flowable<U> {


    private Function<? super T, ? extends U> mapper;
    private Flowable<T> source;

    public FlowableMap(Flowable<T> src, Function<? super T, ? extends U> mapper) {
        this.source = src;
        this.mapper = mapper;
    }


    @Override
    protected void subscribeActual(Subscriber<? super U> subscriber) {
        source.subscribe(new MapSubscriber<T, U>(subscriber, mapper));
    }

    final static class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {

        final Function<? super T, ? extends U> mapper;

        /**
         * Construct a BasicFuseableSubscriber by wrapping the given subscriber.
         *
         * @param actual the subscriber, not null (not verified)
         */
        public MapSubscriber(Subscriber<? super U> actual, Function<? super T, ? extends U> mapper) {
            super(actual);
            this.mapper = mapper;
        }

        public int requestFusion(int mode) {
            return 0;
        }

        public U poll() throws Exception {
            return null;
        }

        public void onNext(T t) {
            try {
                U u = mapper.apply(t);

                actual.onNext(u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
