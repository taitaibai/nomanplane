package monitor.util;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageConsumer implements Runnable{

    BlockingQueue<List<Tablet>> blockingQueue;

    public MessageConsumer(BlockingQueue<List<Tablet>> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        consume(blockingQueue);
    }

    private void consume(BlockingQueue<List<Tablet>> queue) {
        while (true) {
            try {
                List<Tablet> tablets = queue.take();
                DroneParamUtil droneParamUtil = new DroneParamUtil();
                for (Tablet tablet : tablets) {
                    droneParamUtil.insertSystemTablet(tablet);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IoTDBConnectionException e) {
                e.printStackTrace();
            } catch (StatementExecutionException e) {
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
