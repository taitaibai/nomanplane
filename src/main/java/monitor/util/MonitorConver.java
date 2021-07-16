package monitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.StringTokenizer;

public class MonitorConver {
    private static final SimpleDateFormat ymd=new SimpleDateFormat("yyyy:MM:dd");

    public String systemTime(BufferedReader bf) throws IOException {
        Calendar calendar = Calendar.getInstance();
        String format = ymd.format(calendar.getTime());
        String system = new StringTokenizer(bf.readLine(), ",").nextToken();
        String time = system.substring(6, system.indexOf("u"));
        return format + "T" + time;
    }

    public void task(BufferedReader bf) {

    }

    public void cpuStatus() {

    }

    public void menStatus() {

    }

    public void swapStatus() {

    }


}
