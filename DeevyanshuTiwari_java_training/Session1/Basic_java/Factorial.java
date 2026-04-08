import java.util.Scanner;

// Class for logic
class FactorialService {

    public static long calculateFactorial(int n) {
        if (n == 0 || n == 1) return 1;
        return n * calculateFactorial(n - 1);
    }
}

// Class for input
class InputHandler {
    private Scanner sc = new Scanner(System.in);

    public int getNumber() {
        System.out.print("Enter a number: ");
        return sc.nextInt();
    }

    public void close() {
        sc.close();
    }
}

// Main class
public class Factorial {

    public static void main(String[] args) {

        InputHandler input = new InputHandler();
        int number = input.getNumber();

        long result = FactorialService.calculateFactorial(number);

        System.out.println("Factorial of " + number + " = " + result);

        input.close();
    }
}