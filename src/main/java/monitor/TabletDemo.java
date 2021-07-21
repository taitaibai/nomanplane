package monitor;

import monitor.util.MessageConsumer;
import monitor.util.MessageProducer;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.util.List;
import java.util.concurrent.*;

public class TabletDemo {
    public static void main(String[] args) {

        /*BlockingQueue<List<Tablet>> queue = new LinkedBlockingQueue<>(30);

        MessageProducer messageProducer = new MessageProducer(queue);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(
                messageProducer,
                100,
                1000,
                TimeUnit.MILLISECONDS);

        MessageConsumer messageConsumer = new MessageConsumer(queue);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
                messageConsumer,
                0,
                1000,
                TimeUnit.MILLISECONDS
        );*/

    }
}
