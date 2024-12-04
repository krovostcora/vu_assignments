package Exam.Numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<RationalNumber> numbers = new ArrayList<>();

        System.out.println("Enter 5 rational numbers (a and b) in the form a/b:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter a for rational number " + (i + 1) + ": ");
            double a = scanner.nextDouble();
            System.out.print("Enter b for rational number " + (i + 1) + ": ");
            double b = scanner.nextDouble();
            numbers.add(new RationalNumber(a, b));
        }
        scanner.close();

        List<String> results = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                RationalNumber num1 = numbers.get(i);
                RationalNumber num2 = numbers.get(j);

                double sum = num1.computeSum(num2);
                double product = num1.computeProduct(num2);

                String result = num1 + " " + num2 + " " + sum + " " + product;
                results.add(result);
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream("rationalN.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(results);
            System.out.println("Results have been written to rationalN.ser");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
