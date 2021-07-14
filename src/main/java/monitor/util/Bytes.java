package monitor.util;

import java.nio.charset.StandardCharsets;

public class Bytes {
    public static String substring(String src,int start_idex,int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idex; i <=end_idx ; i++) {
            tgt += (char)b[i];
        }
        return tgt;
    }
}
