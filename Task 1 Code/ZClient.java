//
//  ZClient: starting point for Lab #3
//  Connects a REQ (request) socket to tcp://<host>:<port>
//
//  Usage: java ZClient [host] [port]  (defaults: localhost 5555)
//

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZClient {

    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5555;

        //  The context is closed automatically at the end of the try block
        try (ZContext context = new ZContext()) {
            ZMQ.Socket requester = context.createSocket(SocketType.REQ);
            requester.connect("tcp://" + host + ":" + port);

            //  Your code goes here

        }
    }
}
