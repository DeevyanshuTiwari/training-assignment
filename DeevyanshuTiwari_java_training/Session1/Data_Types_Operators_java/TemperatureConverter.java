import java.util.Scanner;

public class TemperatureConverter {
    static double celsiusToFahrenheit(double c) { return (c * 9 / 5) + 32; }
    static double fahrenheitToCelsius(double f) { return (f - 32) * 5 / 9; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 = Celsius to Fahrenheit");
        System.out.println("2 = Fahrenheit to Celsius");
        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.print("Enter Celsius: ");
            double c = sc.nextDouble();
            System.out.println(c + "°C = " + celsiusToFahrenheit(c) + "°F");
        } else {
            System.out.print("Enter Fahrenheit: ");
            double f = sc.nextDouble();
            System.out.println(f + "°F = " + fahrenheitToCelsius(f) + "°C");
        }
        sc.close();
    }
}