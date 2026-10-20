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
        String taskMsg = "";
        String taskRec = "";
        int taskInt = 0;
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


            while (!Thread.currentThread().isInterrupted()) {
                    taskRec = responder.recvStr();
                    taskMsg = " ";
                    System.out.println("Task received: " + taskRec);

                    char[] chars = taskRec.toCharArray();

                    for(int i = chars.length-1; i >= 0; i--) {
                        taskMsg += chars[i];
                    }

                    Thread.sleep(2000);
                responder.send(taskMsg);
                System.out.println("String Reversed.");



            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
