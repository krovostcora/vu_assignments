package com.example.currencyconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This service performs currency conversion using frankfurter.app API.
 */
public class CurrencyService {

    private static final String API_URL = "https://api.frankfurter.app/latest";

    /**
     * Returns a formatted list of supported currency codes, split across 3 lines.
     */
    public String getSupportedCurrencies() {
        try {
            JsonNode json = getJsonNodeFromUrl(API_URL);
            JsonNode rates = json.get("rates");
            if (rates == null) return "No rates found";

            List<String> codes = new ArrayList<>();
            codes.add("EUR (base)");
            rates.fieldNames().forEachRemaining(codes::add);

            StringBuilder result = new StringBuilder("Available currencies:\n\n");

            int total = codes.size();
            int perLine = (int) Math.ceil((double) total / 3);

            for (int i = 0; i < total; i++) {
                result.append(codes.get(i));
                if (i < total - 1) result.append(", ");
                if ((i + 1) % perLine == 0 && i != total - 1) {
                    result.append("\n");
                }
            }

            return result.toString();

        } catch (IOException e) {
            return "Failed to load currencies: " + e.getMessage();
        }
    }

    /**
     * Converts given amount from one currency to another.
     * @param base   The original currency (e.g. "EUR")
     * @param target The target currency (e.g. "USD")
     * @param amount The amount to convert
     * @return A string showing conversion result
     */
    public String convert(String base, String target, double amount) {
        try {
            String requestUrl = String.format("%s?amount=%.2f&from=%s&to=%s", API_URL, amount, base, target);
            JsonNode root = getJsonNodeFromUrl(requestUrl);
            JsonNode rates = root.get("rates");

            if (rates == null || !rates.has(target)) {
                return "Conversion failed: rate not found.";
            }

            double result = rates.get(target).asDouble();
            return String.format("\n%.2f %s = %.2f %s", amount, base, result, target);

        } catch (IOException e) {
            return "Error during conversion: " + e.getMessage();
        }
    }

    /**
     * Performs HTTP GET request to the given URL and parses the JSON response.
     * @param urlString The URL to fetch JSON from
     * @return Parsed JsonNode from the response
     * @throws IOException if HTTP or parsing fails
     */
    private static JsonNode getJsonNodeFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.toString());
    }
}
