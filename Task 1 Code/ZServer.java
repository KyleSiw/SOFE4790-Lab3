//
//  ZServer: starting point for Lab #3
//  Binds a REP (reply) socket to tcp://*:<port>
//
//  Usage: java ZServer [port]        (default port: 5555)
//

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZServer {

    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 5555;

        //  The context is closed automatically at the end of the try block
        try (ZContext context = new ZContext()) {
            ZMQ.Socket responder = context.createSocket(SocketType.REP);
            responder.bind("tcp://*:" + port);

            //  Your code goes here
            String pulse = responder.recvStr();
            if (pulse != null) {
                System.out.println(pulse);
                responder.send("Server Connected.");
            }


        }
    }
}
