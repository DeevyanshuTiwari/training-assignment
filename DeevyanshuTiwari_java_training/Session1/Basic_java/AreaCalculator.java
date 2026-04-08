import java.util.Scanner;

// Class responsible for calculations
class AreaService {

    public static double calculateCircle(double radius) {
        return Math.PI * radius * radius;
    }

    public static double calculateRectangle(double length, double width) {
        return length * width;
    }

    public static double calculateTriangle(double base, double height) {
        return 0.5 * base * height;
    }
}

// Class responsible for user input/output
class InputHandler {
    private Scanner sc = new Scanner(System.in);

    public int getChoice() {
        System.out.println("Choose shape: 1=Circle  2=Rectangle  3=Triangle");
        return sc.nextInt();
    }

    public double getRadius() {
        System.out.print("Enter radius: ");
        return sc.nextDouble();
    }

    public double[] getRectangleInput() {
        System.out.print("Enter length and width: ");
        return new double[]{sc.nextDouble(), sc.nextDouble()};
    }

    public double[] getTriangleInput() {
        System.out.print("Enter base and height: ");
        return new double[]{sc.nextDouble(), sc.nextDouble()};
    }

    public void close() {
        sc.close();
    }
}

// Main class (now clean, not overloaded)
public class AreaCalculator {

    public static void main(String[] args) {

        InputHandler input = new InputHandler();
        int choice = input.getChoice();

        double area = 0;

        switch (choice) {
            case 1:
                double radius = input.getRadius();
                area = AreaService.calculateCircle(radius);
                break;

            case 2:
                double[] rect = input.getRectangleInput();
                area = AreaService.calculateRectangle(rect[0], rect[1]);
                break;

            case 3:
                double[] tri = input.getTriangleInput();
                area = AreaService.calculateTriangle(tri[0], tri[1]);
                break;

            default:
                System.out.println("Invalid choice");
                input.close();
                return;
        }

        System.out.println("Area = " + area);
        input.close();
    }
}