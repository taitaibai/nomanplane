package monitor.session;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;

import java.util.List;


/**
 * @author lgt
 */
public class IoTDBSession {
    private static SessionPool sessionPool;
    private static String host = "127.0.0.1";
    private static int rpcPort = 6667;
    private static String username = "root";
    private static String password = "root";
    private static int poolMaxSize = 10;

    public IoTDBSession() {
        init();
    }

    private static void init() {
        sessionPool = new SessionPool(host, rpcPort, username, password,poolMaxSize);
    }


    public void insertRecord(String deivceId,long time,List<String> mesurement,List<String> values) throws IoTDBConnectionException, StatementExecutionException {
        sessionPool.insertRecord(deivceId,time,mesurement,values);
    }


}
