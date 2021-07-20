package monitor.util;

import org.apache.iotdb.tsfile.write.record.Tablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MessageProducer implements Runnable {

    private BlockingQueue<List<Tablet>> queue;
    private MonitorConver monitorConver;

    public MessageProducer(BlockingQueue<List<Tablet>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        produce(queue);
    }

    private void produce(BlockingQueue<List<Tablet>> queue) {
        while (true) {
            BufferedReader br = null;
            try {
                Process process = Runtime.getRuntime().exec("top -b n 1");
                monitorConver = new MonitorConver();
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                long systemTime = monitorConver.systemTime(br);
                Tablet task = monitorConver.task(br,systemTime);
                Tablet cpuStatus = monitorConver.cpuStatus(br,systemTime);
                Tablet memStatus = monitorConver.memStatus(br,systemTime);
                Tablet swapStatus = monitorConver.swapStatus(br,systemTime);
                br.readLine();
                Tablet everyCommMate = monitorConver.everyCommMate(br);
                List<Tablet> tablets = new ArrayList<>();
                tablets.add(task);
                tablets.add(cpuStatus);
                tablets.add(memStatus);
                tablets.add(swapStatus);
                queue.put(tablets);
                System.out.println(queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("QUEUE SIZE:" + queue.size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
