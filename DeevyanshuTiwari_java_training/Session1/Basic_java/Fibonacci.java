import java.util.Scanner;

// Class for logic
class FibonacciService {

    public static void printFibonacci(int terms) {
        int a = 0, b = 1;

        System.out.print("Fibonacci: ");
        for (int i = 0; i < terms; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
    }
}

// Class for input
class InputHandler {
    private Scanner sc = new Scanner(System.in);

    public int getTerms() {
        System.out.print("Enter how many terms: ");
        return sc.nextInt();
    }

    public void close() {
        sc.close();
    }
}

// Main class
public class Fibonacci {

    public static void main(String[] args) {

        InputHandler input = new InputHandler();
        int terms = input.getTerms();

        FibonacciService.printFibonacci(terms);

        input.close();
    }
}