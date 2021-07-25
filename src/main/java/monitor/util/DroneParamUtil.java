package monitor.util;


import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.write.record.Tablet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DroneParamUtil {

    private SessionPool sessionPool;

    public DroneParamUtil(SessionPool sessionPool) {
        this.sessionPool = sessionPool;
    }

    public void simulate() throws IoTDBConnectionException, StatementExecutionException {
        long currentTimeMillis = System.currentTimeMillis();
        List<String> measurements = new ArrayList<>();
        List<TSDataType> dataTypes = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        int cs = 10;
        measurements.add("\"发动机低压转子转速\"");
        measurements.add("\"发动机高压转子转速\"");
        measurements.add("\"发动机低压涡轮后燃气温度\"");
        measurements.add("\"发动机出口压力\"");
        for (int i = 0; i < cs; i++) {
            if (i > 3) {
                measurements.add("s" + (i + 1));
            }
            dataTypes.add(TSDataType.DOUBLE);
            values.add(makeRandom(1000, 100, 2).doubleValue());
        }
        sessionPool.insertRecord(
                "root.drone.\"发动机系统\"",
                currentTimeMillis,
                measurements,
                dataTypes,
                values);
    }

    private BigDecimal makeRandom(float max, float min, int scale) {
        //Double r = new Double(Math.random() * (max - min) + min);
        BigDecimal random = new BigDecimal(Math.random() * (max - min) + min);
        return random.setScale(scale, RoundingMode.HALF_UP);
    }

    public void insertSystemTablet(Tablet tablet) throws IoTDBConnectionException, StatementExecutionException {
        sessionPool.insertTablet(tablet);
        tablet.reset();
    }

    public void insertTaskStatus(long timestamp, Map<String, Object> map) throws IoTDBConnectionException, StatementExecutionException {
        List<String> measurements = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<TSDataType> dataTypes = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            measurements.add(entry.getKey());
            values.add(entry.getValue());
            dataTypes.add(TSDataType.TEXT);
        }
        sessionPool.insertRecord(
                "root.ubuntu.\"tasks\"",
                timestamp,
                measurements,
                dataTypes,
                values);
    }

    public void insertCpuStatus(long timestamp, Map<String, Object> map) throws IoTDBConnectionException, StatementExecutionException {
        List<String> measurements = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<TSDataType> dataTypes = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            measurements.add(entry.getKey());
            values.add(entry.getValue());
            dataTypes.add(TSDataType.TEXT);
        }
        sessionPool.insertRecord(
                "root.ubuntu.\"%Cpu\"",
                timestamp,
                measurements,
                dataTypes,
                values);
    }

    public void insertMemStatus(long timestamp, Map<String, Object> map) throws IoTDBConnectionException, StatementExecutionException {
        List<String> measurements = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<TSDataType> dataTypes = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            measurements.add(entry.getKey());
            values.add(entry.getValue());
            dataTypes.add(TSDataType.TEXT);
        }
        sessionPool.insertRecord(
                "root.ubuntu.MemStatus",
                timestamp,
                measurements,
                dataTypes,
                values);
    }

    public void insertSwapStatus(long timestamp, Map<String, Object> map) throws IoTDBConnectionException, StatementExecutionException {
        List<String> measurements = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<TSDataType> dataTypes = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            measurements.add(entry.getKey());
            values.add(entry.getValue());
            dataTypes.add(TSDataType.TEXT);
        }
        sessionPool.insertRecord(
                "root.ubuntu.SwapStatus",
                timestamp,
                measurements,
                dataTypes,
                values);
    }


}
