package monitor.util;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

public class MessageProducer implements Runnable {

    private BlockingQueue<List<Tablet>> queue;
    BlockingQueue<List<Map<String, Object>>> listBlockingQueue;
    private MonitorConver monitorConver;

    /*public MessageProducer(BlockingQueue<List<Tablet>> queue) {
        this.queue = queue;
    }*/

    public MessageProducer(BlockingQueue<List<Map<String, Object>>> listBlockingQueue) {
        this.listBlockingQueue = listBlockingQueue;
    }

    @Override
    public void run() {

        //tabletProduce(queue);
        listMapProduce(listBlockingQueue);
    }

    private void listMapProduce(BlockingQueue<List<Map<String, Object>>> listBlockingQueue) {
        while (true) {
            BufferedReader br = null;
            MonitorConver monitorConver = null;
            MonitorConverToMap monitorConverToMap = null;
            try {
                Process process = Runtime.getRuntime().exec("top -b n 1");
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                monitorConverToMap = new MonitorConverToMap();
                long systemTime = monitorConverToMap.systemTime(br);
                Map<String, Object> task = monitorConverToMap.task(br);
                Map<String, Object> cpuStatus = monitorConverToMap.cpuStatus(br);
                Map<String, Object> memStatus = monitorConverToMap.memStatus(br);
                Map<String, Object> swapStatus = monitorConverToMap.swapStatus(br);
                br.readLine();
                List<Map<String, Object>> mapList = new ArrayList<>();
                mapList.add((Map<String, Object>) new HashMap<>().put("systemtime", systemTime));
                mapList.add(task);
                mapList.add(cpuStatus);
                mapList.add(memStatus);
                mapList.add(swapStatus);
                listBlockingQueue.put(mapList);
                DroneParamUtil droneParamUtil = new DroneParamUtil();
                droneParamUtil.insertTaskStatus(systemTime, task);
                droneParamUtil.insertCpuStatus(systemTime, cpuStatus);
                droneParamUtil.insertCpuStatus(systemTime, memStatus);
                droneParamUtil.insertCpuStatus(systemTime, swapStatus);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();/* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            } catch (IoTDBConnectionException e) {
                e.printStackTrace();
            } catch (StatementExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("QUEUE SIZE:" + queue.size());

           /* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void tabletProduce(BlockingQueue<List<Tablet>> queue) {
        while (true) {
            BufferedReader br = null;
            try {
                Process process = Runtime.getRuntime().exec("top -b n 1");
                monitorConver = new MonitorConver();
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                long systemTime = monitorConver.systemTime(br);
                Tablet task = monitorConver.task(br, systemTime);
                Tablet cpuStatus = monitorConver.cpuStatus(br, systemTime);
                Tablet memStatus = monitorConver.memStatus(br, systemTime);
                Tablet swapStatus = monitorConver.swapStatus(br, systemTime);
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
