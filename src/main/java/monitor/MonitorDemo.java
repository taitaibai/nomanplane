package monitor;

import monitor.entity.SystemMonitor;
import monitor.service.MonitorService;
import monitor.serviceImpl.MonitorServiceImpl;
import monitor.util.MonitorConver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.OperatingSystemMXBean;
import java.util.StringTokenizer;

public class MonitorDemo {


    public static void main(String[] args) {
        MonitorService monitorService = new MonitorServiceImpl();
        SystemMonitor systemMonitor = monitorService.getMonitorInfo();
        BufferedReader br = null;
        StringTokenizer system = null;
        StringTokenizer tasks = null;
        StringTokenizer cpuStatus = null;
        StringTokenizer memStatus = null;
        StringTokenizer swap = null;
        StringTokenizer procStatus = null;
        StringTokenizer st = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b n 1");
            MonitorConver monitorConver = new MonitorConver();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //system = new StringTokenizer(br.readLine(), ",");
            String systemTime = monitorConver.systemTime(br);
            tasks = new StringTokenizer(br.readLine(), ",");
            cpuStatus = new StringTokenizer(br.readLine(), ",");
            memStatus = new StringTokenizer(br.readLine(), ",");
            swap = new StringTokenizer(br.readLine(), ",");
            br.readLine();
            procStatus = new StringTokenizer(br.readLine(), ",");
            while (br.readLine() != null) {
                st = new StringTokenizer(br.readLine(), ",");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
