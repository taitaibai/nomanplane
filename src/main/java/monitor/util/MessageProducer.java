package monitor.util;

import java.util.concurrent.*;

public class MessageProducer implements Runnable{

    private BlockingQueue<Integer> queue;

    public MessageProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(20);
        MessageProducer messageProducer = new MessageProducer(queue);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(
                messageProducer,
                100,
                500,
                TimeUnit.MILLISECONDS);
        MessageConsumer messageConsumer = new MessageConsumer(queue);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
                messageConsumer,
                0,
                1000,
                TimeUnit.MILLISECONDS
        );

    }

    @Override
    public void run() {
        produce(queue);
    }

    private void produce(BlockingQueue queue) {
        while (true) {
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("QUEUE SIZE:"+queue.size());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
