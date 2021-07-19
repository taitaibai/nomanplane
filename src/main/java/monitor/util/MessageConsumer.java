package monitor.util;

import java.util.concurrent.BlockingQueue;

public class MessageConsumer implements Runnable{

    BlockingQueue blockingQueue;

    public MessageConsumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        consume(blockingQueue);
    }

    private void consume(BlockingQueue queue) {
        while (true) {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
