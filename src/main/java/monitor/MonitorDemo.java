package monitor;

import monitor.entity.SystemMonitor;
import monitor.service.MonitorService;
import monitor.serviceImpl.MonitorServiceImpl;
import monitor.session.IoTDBSession;
import monitor.util.*;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.OperatingSystemMXBean;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

public class MonitorDemo {


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

        BufferedReader br = null;
        MonitorConver monitorConver = null;
        MonitorConverToMap monitorConverToMap = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b n 1");
            //monitorConver = new MonitorConver();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            monitorConverToMap = new MonitorConverToMap();
            long systemTime = monitorConverToMap.systemTime(br);
            Map<String, Object> task = monitorConverToMap.task(br);
            Map<String, Object> cpuStatus = monitorConverToMap.cpuStatus(br);
            Map<String, Object> memStatus = monitorConverToMap.memStatus(br);
            Map<String, Object> swapStatus = monitorConverToMap.swapStatus(br);
            br.readLine();
            //Tablet everyCommMate = monitorConver.everyCommMate(br);
            /*List<Tablet> tablets = new ArrayList<>();
            tablets.add(task);
            tablets.add(cpuStatus);
            tablets.add(memStatus);
            tablets.add(swapStatus);*/
            DroneParamUtil droneParamUtil = new DroneParamUtil();
            droneParamUtil.insertTaskStatus(systemTime,task);
            droneParamUtil.insertCpuStatus(systemTime,cpuStatus);
            droneParamUtil.insertCpuStatus(systemTime,memStatus);
            droneParamUtil.insertCpuStatus(systemTime,swapStatus);
            /*for (Tablet tablet : tablets) {
                droneParamUtil.insertSystemTablet(tablet);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        } catch (StatementExecutionException e) {
            e.printStackTrace();
        }
    }
}


