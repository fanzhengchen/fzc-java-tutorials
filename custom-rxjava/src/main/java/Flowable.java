import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 2:22 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public abstract class Flowable<T> implements Publisher<T> {


    /**
     * Publisher 订阅 subscriber的方法，会在{@link Flowable}的子类里面真正执行
     *
     * @param subscriber
     */
    protected abstract void subscribeActual(Subscriber<? super T> subscriber);


    public void subscribe(Subscriber<? super T> s) {
        subscribe((FlowableSubscriber<? super T>) s);
    }

    public void subscribe(FlowableSubscriber<? super T> s) {
        subscribeActual(s);
    }


    public static <T> Flowable<T> just(T item) {
        return new FlowableJust<T>(item);
    }

    public <R> Flowable<R> map(Function<? super T, ? extends R> mapper) {
        return new FlowableMap<T, R>(this, mapper);
    }

    public Disposable subscribe(Consumer<? super T> onNext){
        return subscribe(onNext, Functions.ON_ERROR_MISSING,
                Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    public Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
                                Action onComplete, Consumer<? super Subscription> onSubscribe){

        LambdaSubscriber<T> ls = new LambdaSubscriber<T>(onNext, onError, onComplete, onSubscribe);

        subscribe(ls);
        return ls;
    }
}
