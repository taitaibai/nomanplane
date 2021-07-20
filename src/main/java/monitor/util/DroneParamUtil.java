package monitor.util;


import monitor.session.IoTDBSession;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DroneParamUtil {

    private static SessionPool sessionPool;
    private static String host = "127.0.0.1";
    private static int rpcPort = 6667;
    private static String username = "root";
    private static String password = "root";
    private static int poolMaxSize = 10;

    public DroneParamUtil() {
        init();
    }

    private static void init() {

        sessionPool = new SessionPool(host, rpcPort, username, password, poolMaxSize);
    }

    public void simulate() {


    }

    private BigDecimal makeRandom(float max, float min, int scale) {
        BigDecimal random = new BigDecimal(Math.random() * (max - min) + min);
        return random.setScale(scale, RoundingMode.HALF_UP);
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
                "root.ubuntu.tasks",
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
