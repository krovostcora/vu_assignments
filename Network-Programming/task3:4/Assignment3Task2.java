package thirdTask;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Assignment3Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a URL:");
        String urlString = scanner.nextLine();

        String outputFileName = "RawData.txt";
        readDataFromURL(urlString, outputFileName);
    }

    public static void readDataFromURL(String urlString, String outputFileName) {
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();
            System.out.println("Data from given URL written to " + outputFileName + " successfully.");

        } catch (MalformedURLException e) {
            System.out.println("The URL is not valid, are you sure that it's correct?: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
