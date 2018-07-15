import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Target;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-14
 * Time: 5:59 PM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class TestByteBuffer {

    @Test
    public void test() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);


        byteBuffer.putInt(1);
        byteBuffer.putInt(2);
        byteBuffer.putInt(3);

        Assert.assertEquals(byteBuffer.position(), 12);
        Assert.assertEquals(byteBuffer.limit(),100);
        Assert.assertEquals(byteBuffer.capacity(),100);

        byteBuffer.flip();

        Assert.assertEquals(byteBuffer.position(),0);
        Assert.assertEquals(byteBuffer.limit(),12);

        byteBuffer.get();
        byteBuffer.get();
        byteBuffer.get();
        byteBuffer.get();

        Assert.assertEquals(byteBuffer.position(),4);
        Assert.assertEquals(byteBuffer.limit(),12);

        byteBuffer.compact();

        byteBuffer.putInt(4);
        byteBuffer.putInt(5);

        Assert.assertEquals(byteBuffer.position(),16);
        Assert.assertEquals(byteBuffer.limit(),100);



        //byteBuffer.mark();
        //byteBuffer.reset();
        byteBuffer.clear();

        Assert.assertEquals(byteBuffer.position(),0);
        Assert.assertEquals(byteBuffer.limit(),100);

    }
}
