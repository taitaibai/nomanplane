package monitor;

import monitor.util.*;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MapDemo {
    public static void main(String[] args) {
        BlockingQueue<List<Map<String, Object>>> queue = new LinkedBlockingQueue<>(30);
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
        );
    }
}
