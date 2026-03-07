package com.ee.cart.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriceAPIClient {

    private static final String BASE_URL = "https://equalexperts.github.io/backend-take-home-test-data/";

    private final HttpClient client;

    public PriceAPIClient(HttpClient client){
        this.client = client;
    }

    public double getPrice(String product) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + product + ".json"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(response.body());

            return node.get("price").asDouble();

        } catch (Exception e) {
            throw new RuntimeException("Price API failed for product: " + product, e);
        }
    }
}