package monitor.serviceImpl;

import monitor.entity.MonitorInfo;
import monitor.service.MonitorService;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;

import com.sun.management.OperatingSystemMXBean;
import monitor.util.Bytes;

public class MonitorServiceImpl implements MonitorService {
    private static final long kb = 1024;
    private static final File versionFile = new File("/proc/version");
    private static String linuxVersion = null;
    private static final int FAULTLENGTH=10;
    private static final int PERCENT=100;
    private static final int CPUTIME = 30;
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
            if (linuxVersion.equals("2.4")) {
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                tokenizer = new StringTokenizer(bufferedReader.readLine());
                tokenizer.nextToken();
                tokenizer.nextToken();
                String user = tokenizer.nextToken();
                tokenizer.nextToken();
                String system = tokenizer.nextToken();
                tokenizer.nextToken();
                String nice = tokenizer.nextToken();
                System.out.println(user + "，" + system + "," + nice);
                user = user.substring(0, user.indexOf("%"));
                system = system.substring(0, nice.indexOf("%"));
                float userUsage = new Float(user).floatValue();
                float systemUsage = new Float(system).floatValue();
                float niceUsage = new Float(nice).floatValue();
                return (userUsage + systemUsage + niceUsage) / 100;
            } else {
                bufferedReader.readLine();
                bufferedReader.readLine();
                tokenizer = new StringTokenizer(bufferedReader.readLine());
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                tokenizer.nextToken();
                String cpuUsage = tokenizer.nextToken();
                System.out.println(cpuUsage);
                Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
                return (1 - usage.floatValue() / 100);
            }

        } catch (IOException e) {
            e.printStackTrace();
            freeResouce(bufferedReader);
            return 1;
        }finally {
            freeResouce(bufferedReader);
        }
    }

    private static void freeResouce(BufferedReader bf) {
        try {
            if (bf != null) {
                bf.close();
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private long[] readCpu(final Process proc) {
        long[] re = new long[2];
        try {
            proc.getOutputStream().close();
            LineNumberReader input = new LineNumberReader(new InputStreamReader(proc.getInputStream()));
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();
                String cmd=Bytes.substring(line,cmdidx,kmtidx-1);
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                if (caption.equals("System Idle Process ")||caption.equals("System")) {
                    idletime += Long.valueOf(
                            Bytes.substring(line, kmtidx, rocidx - 1).trim())
                            .longValue();
                    idletime += Long.valueOf(
                            Bytes.substring(line, umtidx, wocidx - 1).trim())
                            .longValue();
                    continue;
                }
                kneltime += Long.valueOf(
                        Bytes.substring(line, kmtidx, rocidx - 1).trim())
                        .longValue();
                usertime += Long.valueOf(
                        Bytes.substring(line, umtidx, wocidx - 1).trim())
                        .longValue();
            }
            re[0] = idletime;
            re[1] = kneltime + usertime;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        finally {
            try {
                proc.getInputStream().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }
}
