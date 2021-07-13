package monitor;

import java.lang.management.OperatingSystemMXBean;

public class MonitorDemo {

    private static int kb = 1024;
    private static int mb = 1024 * 1024;

    private static String osName;
    private static int totalThread;
    private static double cpuRatio;
    public static void main(String[] args) {
        long totalMemory = (Runtime.getRuntime().totalMemory()) / kb;

        long freeMemory = (Runtime.getRuntime().freeMemory()) / kb;



    }
}
