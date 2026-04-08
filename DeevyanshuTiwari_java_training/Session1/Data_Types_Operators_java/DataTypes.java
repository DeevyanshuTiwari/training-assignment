public class DataTypes {
    public static void main(String[] args) {

        // Primitive data types store actual values directly in memory.
        int    age     = 25;
        double salary  = 55000.50;
        char   grade   = 'A';
        boolean active = true;

        System.out.println("Primitive int: "     + age);
        System.out.println("Primitive double: "  + salary);
        System.out.println("Primitive char: "    + grade);
        System.out.println("Primitive boolean: " + active);

        // Reference types store address (reference) of an object, not the actual data.
        
        String name    = "Rahul";           // String object
        int[]  marks   = {90, 85, 78};      // Array object

        System.out.println("\nReference String: " + name);
        System.out.println("Reference Array first element: " + marks[0]);
    }
}