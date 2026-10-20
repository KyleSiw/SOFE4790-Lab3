import java.util.Scanner;
import java.util.StringTokenizer;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

//
//  Population update client in Java
//  Connects SUB socket to tcp://<host>:5556
//  Subscribes to a 4-digit postal code entered from keyboard input
//
public class wuclient {
    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";

        // Read target 4-digit postal code from standard input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 4-digit postal code to subscribe to: ");
        String filter = scanner.nextLine().trim();

        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to population update server...");
            ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://" + host + ":5556");

            // Subscribe to the postal code byte prefix
            subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));

            System.out.println("Subscribed to postal code: " + filter);
            System.out.println("Listening for population updates...\n");

            int updateNbr = 0;
            long totalPop = 0;

            while (!Thread.currentThread().isInterrupted()) {
                // Receive filtered update string
                String string = subscriber.recvStr(0);
                if (string == null) break;

                string = string.trim();
                StringTokenizer sscanf = new StringTokenizer(string, " ");
                String zipcode = sscanf.nextToken();
                int population = Integer.parseInt(sscanf.nextToken());

                updateNbr++;
                totalPop += population;

                // Output received message
                System.out.println(
                        String.format(
                                "Received update #%d | Postal Code: %s | Population: %d | Running Avg: %d",
                                updateNbr, zipcode, population, (totalPop / updateNbr)
                        )
                );
            }
        } finally {
            scanner.close();
        }
    }
}