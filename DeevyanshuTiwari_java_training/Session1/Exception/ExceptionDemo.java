
public class ExceptionDemo {
    static int divide(int a, int b) {
        if (b == 0)
            throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Result = " + divide(10, 2));
            System.out.println("Result = " + divide(10, 0)); // triggers exception
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block always runs");
        }
    }
}