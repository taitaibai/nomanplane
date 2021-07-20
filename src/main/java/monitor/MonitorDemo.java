package monitor;

import monitor.entity.SystemMonitor;
import monitor.service.MonitorService;
import monitor.serviceImpl.MonitorServiceImpl;
import monitor.session.IoTDBSession;
import monitor.util.DroneParamUtil;
import monitor.util.MonitorConver;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.OperatingSystemMXBean;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MonitorDemo {


    public static void main(String[] args) {
        IoTDBSession ioTDBSession = new IoTDBSession();
        /*MonitorService monitorService = new MonitorServiceImpl();
        SystemMonitor systemMonitor = monitorService.getMonitorInfo();*/
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b n 1");
            MonitorConver monitorConver = new MonitorConver();
            DroneParamUtil droneParamUtil = new DroneParamUtil();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //system = new StringTokenizer(br.readLine(), ",");
            long systemTime = monitorConver.systemTime(br);
            Map<String, Object> task = monitorConver.task(br);
            Map<String, Object> cpuStatus = monitorConver.cpuStatus(br);
            Map<String, Object> memStatus = monitorConver.memStatus(br);
            Map<String, Object> swapStatus = monitorConver.swapStatus(br);
            droneParamUtil.insertTaskStatus(systemTime,task);
            droneParamUtil.insertCpuStatus(systemTime,cpuStatus);
            droneParamUtil.insertMemStatus(systemTime,memStatus);
            droneParamUtil.insertSwapStatus(systemTime,swapStatus);
            br.readLine();
            String[] everyCommMate = monitorConver.everyCommMate(br);
            while (br.readLine() != null) {

            }
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
