package monitor;

import monitor.entity.SystemMonitor;
import monitor.service.MonitorService;
import monitor.serviceImpl.MonitorServiceImpl;
import monitor.session.IoTDBSession;
import monitor.util.MonitorConver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.OperatingSystemMXBean;
import java.text.ParseException;
import java.util.List;
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
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //system = new StringTokenizer(br.readLine(), ",");
            long systemTime = monitorConver.systemTime(br);
            List<String> task = monitorConver.task(br);
            List<String> cpuStatus = monitorConver.cpuStatus(br);
            List<String> menStatus = monitorConver.menStatus(br);
            List<String> swapStatus = monitorConver.swapStatus(br);
            br.readLine();
            List<String> everyCommMate = monitorConver.everyCommMate(br);

            while (br.readLine() != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
