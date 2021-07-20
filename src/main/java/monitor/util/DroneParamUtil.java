package monitor.util;


import monitor.session.IoTDBSession;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Random;

public class DroneParamUtil {

    private static IoTDBSession ioTDBSession;

    public DroneParamUtil() {
        init();
    }

    private static void init() {
        ioTDBSession = new IoTDBSession();
    }

    public void simulate() {


    }

    private BigDecimal makeRandom(float max, float min, int scale) {
        BigDecimal random = new BigDecimal(Math.random() * (max - min) + min);
        return random.setScale(scale, RoundingMode.HALF_UP);
    }

    public void insertTaskStatus(long timestamp,Map<String, Object> taskStatus) {
        for (Map.Entry<String, Object> entry : taskStatus.entrySet()) {
            //ioTDBSession.insertRecord(entry.getKey(),);
        }
    }

    public void insertCpuStatus(Map<String, Object> cpuStatus) {

    }

    public void insertMemStatus(Map<String, Object> MemStatus) {

    }

    public void insertSwapStatus(Map<String, Object> swapStatus) {

    }



}
