// Class for pattern logic
class PatternService {

    // Triangle pattern
    public static void printTriangle(int rows) {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++)
                System.out.print("* ");
            System.out.println();
        }
    }

    // Square pattern
    public static void printSquare(int size) {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++)
                System.out.print("* ");
            System.out.println();
        }
    }
}

// Main class (only controls flow)
public class Patterns {

    public static void main(String[] args) {

        System.out.println("Triangle (5 rows):");
        PatternService.printTriangle(5);

        System.out.println("\nSquare (4x4):");
        PatternService.printSquare(4);
    }
}