public class SumOfEvens {
    public static void main(String[] args) {
        int sum = 0, i = 1;
        while (i <= 100) {
            if (i % 2 == 0) sum += i;
            i++;
        }
        System.out.println("Sum of even numbers from 1 to 100 = " + sum);
    }
}