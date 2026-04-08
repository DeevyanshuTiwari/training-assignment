
// Abstract class
abstract class Shape {
    abstract double getArea(); // must be implemented by child

    void display() {
        System.out.println("Area = " + getArea());
    }
}

// Interface
interface Drawable {
    void draw(); // all methods public abstract by default
}

class Circle extends Shape implements Drawable {
    double radius;

    Circle(double r) {
        this.radius = r;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

public class AdvancedTopics {
    public static void main(String[] args) {
        Circle c = new Circle(5);
        c.draw();
        c.display();
    }
}
