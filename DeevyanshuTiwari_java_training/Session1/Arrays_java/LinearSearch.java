import java.util.Scanner;

public class LinearSearch {
    static int search(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == target)
                return i;
        return -1;
    }

    public static void main(String[] args) {
        int[] numbers = { 15, 30, 45, 60, 75, 90 };
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number to search: ");
        int target = sc.nextInt();

        int index = search(numbers, target);
        if (index != -1)
            System.out.println("Found at index " + index);
        else
            System.out.println("Not found");
        sc.close();
    }
}