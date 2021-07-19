package monitor.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Random;

public class DroneParamUtil {

    public void simulate() {


    }

    private BigDecimal makeRandom(float max, float min, int scale) {
        BigDecimal random = new BigDecimal(Math.random() * (max - min) + min);
        return random.setScale(scale, RoundingMode.HALF_UP);
    }


}
