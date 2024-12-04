package thirdTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

public class Assignment4Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the URL to send the POST request: ");
        String url = scanner.nextLine();
        System.out.print("Enter the data to send (for example, in JSON format): ");
        String postData = scanner.nextLine();

        // Send the POST request with the provided data
        sendPostRequest(url, postData);
    }

    // Method to send a POST request to the specified URL with the given data
    public static void sendPostRequest(String url, String postData) {
        // HttpClient with a 10-second timeout
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check and output the response status code
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("Success! Response code: " + response.statusCode());
            } else {
                System.err.println("Request failed with response code: " + response.statusCode());
            }

            // Output the response body
            System.out.println("Response body: \n" + response.body());

        } catch (IOException | InterruptedException e) {
            System.err.println("Error occurred during the request: " + e.getMessage());
        }
    }
}
