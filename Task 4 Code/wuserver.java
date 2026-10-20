import java.util.Random;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

//
//  Population update server in Java
//  Binds PUB socket to tcp://*:5556
//  Publishes continuous population updates for 10 distinct 4-digit postal codes
//
public class wuserver {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");

            Random random = new Random(System.currentTimeMillis());

            // Generate 10 random 4-digit postal codes (1000 - 9999)
            int[] postalCodes = new int[10];
            for (int i = 0; i < postalCodes.length; i++) {
                postalCodes[i] = 1000 + random.nextInt(9000);
                System.out.println(String.format("  [%2d] Postal Code: %04d", (i + 1), postalCodes[i]));
            }
            System.out.println("Starting continuous population update broadcast...\n");

            while (!Thread.currentThread().isInterrupted()) {
                // Randomly select one of the 10 generated postal codes
                int zipcode = postalCodes[random.nextInt(postalCodes.length)];
                // Generate random population number (5,000 to 500,000)
                int population = 5000 + random.nextInt(495000);

                // Format update as 4-digit postal code + population string
                String update = String.format("%04d %d", zipcode, population);

                // Broadcast the message to all SUB clients
                publisher.send(update, 0);
                System.out.println("Published update: " + update);

                // Pause for 200 ms between updates for readable console output
                Thread.sleep(2000);
            }
        }
    }
}