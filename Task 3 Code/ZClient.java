//
//  ZClient: starting point for Lab #3
//  Connects a REQ (request) socket to tcp://<host>:<port>
//
//  Usage: java ZClient [host] [port]  (defaults: localhost 5555)
//

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Objects;
import java.util.Scanner;

public class ZClient {


    public static void main(String[] args) {

        Scanner uinp = new Scanner(System.in);
        String taskInt = "";
        String taskRec = "";

        String host = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5555;

        //  The context is closed automatically at the end of the try block
        try (ZContext context = new ZContext()) {
            ZMQ.Socket requester = context.createSocket(SocketType.REQ);
            requester.connect("tcp://" + host + ":" + port);
            requester.setReceiveTimeOut(5000);

            //  Your code goes here
            requester.send("Client Connected.");
            String pulse = requester.recvStr();
            if (pulse != null) {
                System.out.println(pulse);
            }

            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("Please enter a string (or close to exit).");
                taskInt = uinp.nextLine();
                requester.send(taskInt);
                System.out.println("Task sent: " + taskInt);

                taskRec = requester.recvStr();
                if(taskRec == null) {
                    System.out.println("Server timed out.");
                    break;
                }
                if (Objects.equals(taskInt, "close")){
                    break;
                }
                System.out.println(taskRec);
            }


        }
    }
}
