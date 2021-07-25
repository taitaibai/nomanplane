package monitor.util;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageConsumer implements Runnable {

    private BlockingQueue<List<Tablet>> tabletBlockingQueue;
    private BlockingQueue<List<Map<String, Object>>> listBlockingQueue;
    private SessionPool sessionPool;
    /*public MessageConsumer(BlockingQueue<List<Tablet>> blockingQueue) {
        this.tabletBlockingQueue = blockingQueue;
    }*/

    public MessageConsumer(BlockingQueue<List<Map<String, Object>>> listBlockingQueue) {
        this.listBlockingQueue = listBlockingQueue;
    }

    public MessageConsumer(BlockingQueue<List<Map<String, Object>>> listBlockingQueue, SessionPool sessionPool) {
        this.listBlockingQueue = listBlockingQueue;
        this.sessionPool = sessionPool;
    }

    @Override
    public void run() {
        //tabletConsume();
        listMapConsume();
    }

    private void listMapConsume() {
        while (true) {
            try {
                List<Map<String, Object>> mapList = listBlockingQueue.take();
                System.out.println("CONSUMER:TAKE ---QUEUE SIZE:"+listBlockingQueue.size());
                Object timestamp = mapList.get(0).get("systemtime");
                DroneParamUtil droneParamUtil = new DroneParamUtil(sessionPool);
                droneParamUtil.insertTaskStatus((Long) timestamp,mapList.get(1));
                droneParamUtil.insertCpuStatus((Long) timestamp,mapList.get(2));
                droneParamUtil.insertMemStatus((Long) timestamp,mapList.get(3));
                droneParamUtil.insertSwapStatus((Long) timestamp,mapList.get(4));
                droneParamUtil.simulate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void tabletConsume() {
        while (true) {
            try {
                List<Tablet> tablets = tabletBlockingQueue.take();
                System.out.println("QUEUE: TAKE");
                DroneParamUtil droneParamUtil = new DroneParamUtil(sessionPool);
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

        }
    }
}
