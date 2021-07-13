package monitor.entity;

public class MonitorInfo {

    private long totalMemory;
    private long freeMemory;
    private long maxMemory;
    private String osName;
    private long totolMemorySize;
    private long freePhysicalMemorySize;
    private long userMemory;
    private int totalThread;
    private double cpuRatio;

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public long getTotolMemorySize() {
        return totolMemorySize;
    }

    public void setTotolMemorySize(long totolMemorySize) {
        this.totolMemorySize = totolMemorySize;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public long getUserMemory() {
        return userMemory;
    }

    public void setUserMemory(long userMemory) {
        this.userMemory = userMemory;
    }

    public int getTotalThread() {
        return totalThread;
    }

    public void setTotalThread(int totalThread) {
        this.totalThread = totalThread;
    }

    public double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }
}
