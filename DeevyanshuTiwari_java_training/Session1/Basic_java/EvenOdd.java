import java.util.Scanner;

// Class for logic
class NumberService {
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}

// Class for input handling
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
public class EvenOdd {

    public static void main(String[] args) {

        InputHandler input = new InputHandler();
        int number = input.getNumber();

        boolean result = NumberService.isEven(number);

        if (result)
            System.out.println(number + " is Even");
        else
            System.out.println(number + " is Odd");

        input.close();
    }
}