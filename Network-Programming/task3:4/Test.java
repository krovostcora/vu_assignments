package thirdTask;

public class Test {
    public static void main(String[] args) {
        // Create a Square object
        Square square = new Square(5);
        System.out.println("Square:");
        square.print();

        // Create a Circle object
        Circle circle = new Circle(3);
        System.out.println("\nCircle:");
        circle.print();

        // Create a Cone object
        Cones cones = new Cones(2, 4);
        System.out.println("\nCone:");
        cones.print();
    }
}
