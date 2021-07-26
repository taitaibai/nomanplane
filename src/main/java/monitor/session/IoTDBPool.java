package monitor.session;

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;

import java.util.List;

public class IoTDBPool {
    private static SessionPool sessionPool;
    private static String host = "171.221.206.201";
    private static int rpcPort = 6667;
    private static String username = "root";
    private static String password = "root";
    private static int poolMaxSize = 50;

    public static SessionPool init() {
        sessionPool = new SessionPool(host, rpcPort, username, password, poolMaxSize);
        return sessionPool;
    }

}
