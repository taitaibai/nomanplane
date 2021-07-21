package monitor.util;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageConsumer implements Runnable {

    BlockingQueue<List<Tablet>> tabletBlockingQueue;
    BlockingQueue<List<Map<String, Object>>> listBlockingQueue;
    /*public MessageConsumer(BlockingQueue<List<Tablet>> blockingQueue) {
        this.tabletBlockingQueue = blockingQueue;
    }*/

    public MessageConsumer(BlockingQueue<List<Map<String, Object>>> listBlockingQueue) {
        this.listBlockingQueue = listBlockingQueue;
    }

    @Override
    public void run() {
        //tabletConsume();
        listMapConsume(listBlockingQueue);
    }

    private void listMapConsume(BlockingQueue<List<Map<String, Object>>> listBlockingQueue) {
        while (true) {
            try {
                List<Map<String, Object>> mapList = listBlockingQueue.take();
                Object timestamp = mapList.get(0).get("systemtime");
                DroneParamUtil droneParamUtil = new DroneParamUtil();
                droneParamUtil.insertTaskStatus((Long) timestamp,mapList.get(1));
                droneParamUtil.insertCpuStatus((Long) timestamp,mapList.get(2));
                droneParamUtil.insertMemStatus((Long) timestamp,mapList.get(3));
                droneParamUtil.insertSwapStatus((Long) timestamp,mapList.get(4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IoTDBConnectionException e) {
                e.printStackTrace();
            } catch (StatementExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private void tabletConsume(BlockingQueue<List<Tablet>> queue) {
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
