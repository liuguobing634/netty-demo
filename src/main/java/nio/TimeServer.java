package nio;

/**
 * Created by 刘国兵 on 2017/7/8.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 9876;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexedTimeServer-001").start();
    }

}
