import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-06-30
 * Time: 2:20 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) {
        Flowable.just("2")
                .map(new Function<String, Integer>() {
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s);
                    }
                })
                .map(new Function<Integer, Integer>() {
                    public Integer apply(Integer integer) throws Exception {
                        return integer << 2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }
}
