package thirdTask;

import java.io.*;
import java.util.Scanner;

// Serializable version of Cone
class Cones extends Circle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private double height;

    // Constructor with default values for radius and height
    public Cones() {
        this(1, 1);
    }

    // Constructor with specified radius and height
    public Cones(double radius, double height) {
        super(radius);
        this.height = height;
    }

    // Getters and setters for height
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Overriding the getArea() method from thirdTask.Circle to calculate the cone's total surface area
    @Override
    public double getArea() {
        return Math.PI * radius * (radius + Math.sqrt(radius * radius + height * height));
    }

    // Method to calculate the volume
    public double getVolume() {
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }

    // Overriding the printNameShape() method to specify the shape
    @Override
    public void printNameShape() {
        System.out.println("thirdTask.Cone");
    }

    // Overriding the print() method to display cone information
    @Override
    public void print() {
        System.out.println("Cone with a radius " + getRadius() + " and a height " + height + ", has an area P=" + getArea() + " and a volume V=" + getVolume());
    }
}

public class Assignment4Task1 {

    public static void main(String[] args) {
        String fileName = "cones.ser";
        Scanner scanner = new Scanner(System.in);

        // Writing results to the binary file
        writeConesToFile(fileName);

        // Ask users if they want to read the file
        System.out.println("Do you want to read created file? (Please, type yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("You chose to read the file!\n Here the cones are: \n\n");
            readConesFromFile(fileName);
        } else {
            System.out.println("You chose not to read the file.");
        }
    }

    // Method to write 8 cones to a binary file
    public static void writeConesToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (int i = 1; i <= 8; i++) {
                Cones cones = new Cones(i, i + 1);
                oos.writeObject(cones);
            }
            System.out.println("All 8 cones written to the file " + fileName + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read cones from a binary file and print them
    public static void readConesFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            for (int i = 1; i <= 8; i++) {
                Cones cones = (Cones) ois.readObject();
                cones.print();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
