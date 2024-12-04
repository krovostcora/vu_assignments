package Exam.Numbers;

import java.io.*;
import java.util.List;

public class ReadFile {
    public static void main(String[] args) {
        try (FileInputStream fileIn = new FileInputStream("rationalN.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            List<String> results = (List<String>) objectIn.readObject();

            System.out.println("Contents of rationalN.ser:");
            for (String result : results) {
                System.out.println(result);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}




