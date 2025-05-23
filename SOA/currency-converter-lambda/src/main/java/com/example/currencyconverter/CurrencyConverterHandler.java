package com.example.currencyconverter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Lambda entry point class for currency conversion.
 * Expects API Gateway event with JSON body containing base, target, and amount.
 * Uses Jackson to parse the JSON body from the event.
 */
public class CurrencyConverterHandler implements RequestHandler<Map<String, Object>, String> {

    private final CurrencyService currencyService = new CurrencyService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The handleRequest method is called by AWS Lambda.
     *
     * @param input   The incoming event from API Gateway as a Map.
     *                The actual request data is in the "body" field as a JSON string.
     * @param context Lambda execution context.
     * @return Conversion result, supported currencies list, or error message.
     */
    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        try {
            // Extract the raw JSON body string from the event
            String bodyJson = (String) input.get("body");
            if (bodyJson == null) {
                return "Missing request body";
            }

            // Parse JSON body into a Map<String, String>
            Map<String, String> body = objectMapper.readValue(bodyJson, new TypeReference<Map<String, String>>() {});

            // If the user requests supported currencies, return the list
            if (body.containsKey("supported")) {
                return currencyService.getSupportedCurrencies();
            }

            // Extract required parameters from the parsed body
            String base = body.get("base");
            String amountStr = body.get("amount");
            String target = body.get("target");

            if (base == null || amountStr == null || target == null) {
                return "Missing parameters. Required: base, amount, target";
            }

            // Convert amount string to double
            double amount = Double.parseDouble(amountStr);

            // Perform currency conversion
            return currencyService.convert(base, target, amount);

        } catch (NumberFormatException e) {
            return "Invalid amount: " + e.getMessage();
        } catch (Exception e) {
            // Log the error for debugging purposes
            context.getLogger().log("Error in handleRequest: " + e.getMessage());
            return "Internal error: " + e.getMessage();
        }
    }
}
