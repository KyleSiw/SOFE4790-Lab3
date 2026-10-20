//
//  PrimeNumbers: prints all the prime numbers up to a given number
//
//  Usage: java PrimeNumbers <number>
//  Example: java PrimeNumbers 10   prints   2 3 5 7
//

public class PrimeNumbers {

    //  Returns true if n is a prime number
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int limit = (args.length > 0) ? Integer.parseInt(args[0]) : 10;

        for (int n = 2; n <= limit; n++) {
            if (isPrime(n)) {
                System.out.print(n + " ");
            }
        }
        System.out.println();
    }
}
