package monitor;

import monitor.session.IoTDBPool;
import monitor.util.*;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

public class MapDemo {
    public static void main(String[] args) {
        SessionPool sessionPool = IoTDBPool.init();
        BlockingQueue<List<Map<String, Object>>> queue = new LinkedBlockingQueue<>(30);
        BlockingQueue<List<Tablet>> tabletQueue = new LinkedBlockingQueue<>(30);

        MessageProducer messageProducer = new MessageProducer(queue);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(
                messageProducer,
                100,
                1000,
                TimeUnit.MILLISECONDS);

        MessageConsumer messageConsumer = new MessageConsumer(queue,sessionPool);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
                messageConsumer,
                1000,
                1000,
                TimeUnit.MILLISECONDS
        );
    }
}
