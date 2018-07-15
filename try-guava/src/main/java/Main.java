import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-11
 * Time: 4:41 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        list.parallelStream()
                .forEach(integer -> {
                    System.out.println(integer + " " + Thread.currentThread());
                });
    }
}
