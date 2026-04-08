public class Operators {
    public static void main(String[] args) {
        int a = 10, b = 3;

        // Arithmetic operators
        System.out.println("=== Arithmetic ===");
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));

        // Relational operators
        System.out.println("\n=== Relational ===");
        System.out.println("a > b  : " + (a > b));
        System.out.println("a < b  : " + (a < b));
        System.out.println("a == b : " + (a == b));
        System.out.println("a != b : " + (a != b));

        // Logical operators
        System.out.println("\n=== Logical ===");
        System.out.println("a>5 && b>1 : " + (a > 5 && b > 1));
        System.out.println("a>5 || b>5 : " + (a > 5 || b > 5));
        System.out.println("!(a == b)  : " + !(a == b));
    }
}