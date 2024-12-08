package thirdTask;

import java.io.*;

public class Assignment3Task1 {

    public static void main(String[] args) {
        String fileName = "Cones7.txt";

        writeConesToFile(fileName);

        readConesFromFile(fileName);
    }

    public static void writeConesToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 1; i <= 7; i++) {
                Cones cones = new Cones(i, i + 1);
                writer.write("Cone " + i + ": ");
                writer.write("Radius: " + cones.getRadius() + ", Height: " + cones.getHeight());
                writer.write(", Area: " + cones.getArea() + ", Volume: " + cones.getVolume());
                writer.newLine();  // Move to the next line
            }
            System.out.println("Cones written to file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readConesFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
