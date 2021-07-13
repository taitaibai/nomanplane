package monitor.serviceImpl;

import monitor.entity.MonitorInfo;
import monitor.service.MonitorService;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;

import com.sun.management.OperatingSystemMXBean;

public class MonitorServiceImpl implements MonitorService {
    private static final long kb = 1024;

    @Override
    public MonitorInfo getMonitorInfo() {
        long totalMemory = (Runtime.getRuntime().totalMemory()) / kb;
        long freeMemory = (Runtime.getRuntime().freeMemory()) / kb;
        long maxMemory = (Runtime.getRuntime().maxMemory()) / kb;
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = (osmxb.getTotalPhysicalMemorySize()) / kb;
        long freePhysicalMemorySize = (osmxb.getFreePhysicalMemorySize()) / kb;
        long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null;parentThread=parentThread.getParent()) {
            int totalThread = parentThread.activeCount();
            double cpuRatio = 0;
        }
        return null;
    }

    private static double getCpuRateForLinux() {
        BufferedReader bufferedReader = null;
        StringTokenizer tokenizer = null;
        try {
            Process process = Runtime.getRuntime().exec("top -b -n 0.1");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            bufferedReader.readLine();
            tokenizer = new StringTokenizer(bufferedReader.readLine());
            tokenizer.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
