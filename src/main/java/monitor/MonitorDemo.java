package monitor;

import monitor.entity.SystemMonitor;
import monitor.service.MonitorService;
import monitor.serviceImpl.MonitorServiceImpl;
import monitor.util.MonitorConver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.StringTokenizer;

public class MonitorDemo {


    public static void main(String[] args) {
        /*MonitorService monitorService = new MonitorServiceImpl();
        SystemMonitor systemMonitor = monitorService.getMonitorInfo();*/
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b n 1");
            MonitorConver monitorConver = new MonitorConver();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //system = new StringTokenizer(br.readLine(), ",");
            String systemTime = monitorConver.systemTime(br);
            List<String> task = monitorConver.task(br);
            List<String> cpuStatus = monitorConver.cpuStatus(br);
            List<String> menStatus = monitorConver.menStatus(br);
            List<String> swapStatus = monitorConver.swapStatus(br);
            br.readLine();
            List<String> everyCommMate = monitorConver.everyCommMate(br);
            while (br.readLine() != null) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
