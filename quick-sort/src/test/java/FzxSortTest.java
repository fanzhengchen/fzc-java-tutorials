import org.junit.Assert;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-01
 * Time: 3:22 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class FzxSortTest {


    @Test
    public void testQuickSort() {
        int n = 800000;
        int module = (1 << 25);
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = (int) (Math.random() * module);
        }
        FzcSort sort = new FzcSort(array);
        sort.quickSort();
        System.out.println(sort.toString());
        System.out.println(array.length);
        Assert.assertTrue(checkIfIncrease(array));
    }

    @Test
    public void testSwapSort() {
        int n = 80;
        int module = (1 << 25);
        int[] array = new int[n];
        for (int i = 0; i < n; ++i) {
            array[i] = (int) (Math.random() * module);
        }
        FzcSort sort = new FzcSort(array);
        sort.swapSort();
        System.out.println(sort.toString());
        System.out.println(array.length);
        Assert.assertTrue(checkIfIncrease(array));
    }

    public boolean checkIfIncrease(int[] array) {
        int length = array.length;
        if (length <= 1) {
            return true;
        }

        for (int i = 1; i < length; ++i) {
            if (array[i] < array[i - 1]) {
                System.out.println(i + " " + array[i - 1] + " " + array[i]);
                return false;
            }
        }
        return true;
    }
}
