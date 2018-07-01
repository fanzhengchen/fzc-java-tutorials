/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-01
 * Time: 3:07 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class FzcSort {

    int[] elements;

    void swap(int x, int y) {
        int t = elements[x];
        elements[x] = elements[y];
        elements[y] = t;
    }

    public FzcSort(int[] array) {
        elements = array;
    }


    /**
     * 冒泡
     */
    public void swapSort() {
        int length = elements.length;
        for (int i = 1; i < length; ++i) {
            for (int j = length - 1; j >= i; --j) {
                if (elements[j] < elements[j - 1]) {
                    swap(j - 1, j);
                }
            }
        }
    }

    public void quickSort() {
        quickSort(0, elements.length - 1);
    }

    /**
     * 快速排序
     *
     * @param l
     * @param r
     */
    public void quickSort(int l, int r) {
        if (l >= r) {
            return;
        }
        int p = l;
        int lhs = l, rhs = r;
        while (l < r) {
            while (elements[p] <= elements[r] && p < r) {
                --r;
            }
            if (p < r) {
                l = p + 1;
                swap(p, r);
                p = r;
            }
            while (elements[l] <= elements[p] && l < p) {
                ++l;
            }
            if (l < p) {
                r = p - 1;
                swap(p, l);
                p = l;
            }
        }

        quickSort(lhs, p - 1);
        quickSort(p + 1, rhs);

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Integer t : elements) {
            builder.append(" ");
            builder.append(t);
        }
        builder.append("]");
        return builder.toString();
    }
}
