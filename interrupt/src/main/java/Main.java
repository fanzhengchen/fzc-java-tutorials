/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-07-05
 * Time: 1:18 AM
 *
 * @author: MarkFan
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) throws Exception{
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("sssssssssssss");

                try {
                    Thread.sleep(30000);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("A " + Thread.currentThread().isInterrupted());
                }


            }
        });


        thread.start();

        System.out.println(" b " + thread.isInterrupted());

        thread.interrupt();

        System.out.println(thread.isInterrupted());

        Thread.sleep(5000);

        Thread.interrupted();

        thread.interrupt();
        System.out.println("aaaaaaaaa");


    }
}
