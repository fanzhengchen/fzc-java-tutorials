import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args){
        MyEventLoop odd = new MyEventLoop("thread odd");
        MyEventLoop even = new MyEventLoop("thread even");

        odd.setNext(even);
        even.setNext(odd);

        final int first = 1;
        odd.execute(new Task(1,odd));
    }


    static class MyEventLoop implements Executor {

        Thread thread ;
        BlockingQueue<Runnable> queue ;
        MyEventLoop next;

        public MyEventLoop(String name ){
            queue = new LinkedBlockingQueue<>();
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(;;){
                        try {
                            Optional.ofNullable(queue.take())
                                    .ifPresent(r -> r.run());
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.setName(name);
            thread.start();
        }

        public String getName(){
            return thread.getName();
        }

        public void setNext(MyEventLoop next) {
            this.next = next;
        }

        public MyEventLoop next(){
            return next;
        }

        @Override
        public void execute(Runnable command) {
            queue.offer(command);
        }
    }
    static class Task implements Runnable{
        private int value;
        private MyEventLoop eventLoop;
        public Task(int value, MyEventLoop eventLoop){
            this.value = value;
            this.eventLoop = eventLoop;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + " " + value);
            System.out.println(eventLoop.getName() + " " + eventLoop.next().getName());
            if(value <= 2000000) {
                eventLoop.next().execute(new Task(value + 1, eventLoop.next()));
            }
        }
    }



}
