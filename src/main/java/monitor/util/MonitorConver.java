package monitor.util;

import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.write.record.Tablet;
import org.apache.iotdb.tsfile.write.schema.MeasurementSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class MonitorConver {
    private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy:MM:dd");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

    /**
     * 当前系统时间
     *
     * @param bf
     * @return System.out.println(mapList);
                System.out.println("QUEUE: TAKE");
     * @throws IOException
     */
    public long systemTime(BufferedReader bf) throws IOException, ParseException {
        Calendar calendar = Calendar.getInstance();
        String format = ymd.format(calendar.getTime());
        String system = new StringTokenizer(bf.readLine(), ",").nextToken();
        String time = system.substring(6, system.indexOf("u"));
        long timestamp = sdf.parse(format + " " + time).getTime();
        return timestamp;
    }

    /**
     * schemas.add(new MeasurementSchema(split[2], TSDataType.INT32));
     * values.add(Integer.parseInt(split[1]));
     * Tasks — 任务（进程），系统现在共有183个进程，其中处于运行中的有*个，
     * *个在休眠（sleep），stoped状态的有*个，zombie状态（僵尸）的有*个。
     *
     * @param br
     * @return
     * @throws IOException
     */
    public Tablet task(BufferedReader br, long timestamp) throws IOException {
        List<String> strings = getStrings(br);
        Map<String, Object> map = new HashMap<>();
        List<MeasurementSchema> schemas = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String[] split = strings.get(i).split("\\s+");
            schemas.add(new MeasurementSchema(split[split.length - 1], TSDataType.DOUBLE));
            values.add(Double.parseDouble(split[split.length - 2]));
        }
        Tablet tablet = new Tablet("root.ubuntu.tasks", schemas, 1);
        tablet.addTimestamp(0, timestamp);
        for (int i = 0; i < schemas.size(); i++) {
            tablet.addValue(schemas.get(i).getMeasurementId(), 0, values.get(i));
        }
        return tablet;
    }

    /**
     * cpu状态
     * 6.7% us — 用户空间占用CPU的百分比。
     * 0.4% sy — 内核空间占用CPU的百分比。
     * 0.0% ni — 改变过优先级的进程占用CPU的百分比
     * 92.9% id — 空闲CPU百分比
     * 0.0% wa — IO等待占用CPU的百分比
     * 0.0% hi — 硬中断（Hardware IRQ）占用CPU的百分比
     * 0.0% si — 软中断（Software Interrupts）占用CPU的百分比
     *
     * @param br
     * @return
     * @throws IOException
     */
    public Tablet cpuStatus(BufferedReader br, long timestamp) throws IOException {
        List<String> strings = getStrings(br);
        Map<String, Object> map = new HashMap();
        List<MeasurementSchema> schemas = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String[] split = strings.get(i).split("\\s+");
            schemas.add(new MeasurementSchema(split[split.length - 1], TSDataType.DOUBLE));
            values.add(Double.parseDouble(split[split.length - 2]));
        }
        Tablet tablet = new Tablet("root.ubuntu.\"Cpu\"", schemas, 1);
        tablet.addTimestamp(0, timestamp);
        for (int i = 0; i < schemas.size(); i++) {
            tablet.addValue(schemas.get(i).getMeasurementId(), 0, values.get(i));
        }
        return tablet;
    }

    /**
     * 内存状态
     * 8306544k total — 物理内存总量（8GB）
     * 7775876k used — 使用中的内存总量（7.7GB）
     * 530668k free — 空闲内存总量（530M）
     * 79236k buffers — 缓存的内存量 （79M
     *
     * @param br
     * @return
     * @throws IOException
     */
    public Tablet memStatus(BufferedReader br, long timestamp) throws IOException {
        List<String> strings = getStrings(br);
        Map<String, Object> map = new HashMap();
        List<MeasurementSchema> schemas = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String[] split = strings.get(i).split("\\s+");
            schemas.add(new MeasurementSchema(split[split.length - 1], TSDataType.DOUBLE));
            values.add(Double.parseDouble(split[split.length - 2]));
        }
        Tablet tablet = new Tablet("root.ubuntu.memstatus", schemas, 1);
        tablet.addTimestamp(0, timestamp);
        for (int i = 0; i < schemas.size(); i++) {
            tablet.addValue(schemas.get(i).getMeasurementId(), 0, values.get(i));
        }
        return tablet;
    }

    /**
     * swap交换分区
     * 2031608k total — 交换区总量（2GB）
     * 2556k used — 使用的交换区总量（2.5M）
     * 2029052k free — 空闲交换区总量（2GB）
     * 4231276k cached — 缓冲的交换区总量（4GB）
     *
     * @param br
     * @return
     * @throws IOException
     */
    public Tablet swapStatus(BufferedReader br, long timestamp) throws IOException {
        List<String> strings = getStrings(br);
        Map<String, Object> map = new HashMap<>();
        List<MeasurementSchema> schemas = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String[] split = strings.get(i).split("\\s+");
            if (i == strings.size() - 1) {
                schemas.add(new MeasurementSchema(split[split.length - 4], TSDataType.DOUBLE));
                values.add(Double.parseDouble(split[split.length - 5]));
                schemas.add(new MeasurementSchema("avalimem", TSDataType.DOUBLE));
                values.add(Double.parseDouble(split[split.length - 3]));
            } else {
                schemas.add(new MeasurementSchema(split[split.length - 1], TSDataType.DOUBLE));
                values.add(Double.parseDouble(split[split.length - 2]));
            }

        }
        Tablet tablet = new Tablet("root.ubuntu.swapstatus", schemas, 1);
        tablet.addTimestamp(0, timestamp);
        for (int i = 0; i < schemas.size(); i++) {
            tablet.addValue(schemas.get(i).getMeasurementId(), 0, values.get(i));
        }
        return tablet;
    }

    /**
     * 各进程（任务）的状态监控
     * PID — 进程id
     * USER — 进程所有者
     * PR — 进程优先级
     * NI — nice值。负值表示高优先级，正值表示低优先级
     * VIRT — 进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
     * RES — 进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
     * SHR — 共享内存大小，单位kb
     * S — 进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
     * %CPU — 上次更新到现在的CPU时间占用百分比
     * %MEM — 进程使用的物理内存百分比
     * TIME+ — 进程使用的CPU时间总计，单位1/100秒
     * COMMAND — 进程名称（命令名/命令行）
     *
     * @param br
     * @return
     * @throws IOException
     */
    public Tablet everyCommMate(BufferedReader br) throws IOException {
        String[] commMeta = br.readLine().split("\\s+");
        List<MeasurementSchema> schemas = new ArrayList<>();
        for (int i = 0; i < commMeta.length - 1; i++) {
            schemas.add(new MeasurementSchema(commMeta[i], TSDataType.TEXT));
        }

        List<Object> values = new ArrayList<>();
        while (br.readLine() != null) {
            String[] split = br.readLine().split("\\s+");
        }
        return null;
    }


    private List<String> getStrings(BufferedReader br) throws IOException {
        StringTokenizer getStrings = new StringTokenizer(br.readLine(), ",");
        List<String> stringList = new ArrayList<>();
        while (getStrings.hasMoreTokens()) {
            stringList.add(getStrings.nextToken());
        }
        return stringList;
    }


}
