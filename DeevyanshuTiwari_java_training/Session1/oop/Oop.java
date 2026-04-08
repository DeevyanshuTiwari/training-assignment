class Student {
    private String name;
    private int rollNumber;
    private double marks;

    // Constructor
    Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    // Method (will be overridden)
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNumber);
        System.out.println("Marks: " + marks);
    }

    // Polymorphism (method overloading)
    public void displayInfo(String message) {
        System.out.println(message);
        displayInfo();
    }

    // Getters (Encapsulation)
    public String getName() {
        return name;
    }
}

// Child class (Inheritance)
class GraduateStudent extends Student {
    private String specialization;

    // Constructor
    GraduateStudent(String name, int rollNumber, double marks, String specialization) {
        super(name, rollNumber, marks);
        this.specialization = specialization;
    }

    // Method overriding (Runtime Polymorphism)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
    }
}

// Main class
public class Oop {
    public static void main(String[] args) {

        Student s1 = new Student("Rahul", 101, 85.5);
        GraduateStudent g1 = new GraduateStudent("Amit", 102, 90.0, "Computer Science");

        System.out.println("Student Details:");
        s1.displayInfo();

        System.out.println("\nGraduate Student Details:");
        g1.displayInfo();

        System.out.println("\nPolymorphism (Overloading):");
        s1.displayInfo("Displaying Student Info:");
    }
}