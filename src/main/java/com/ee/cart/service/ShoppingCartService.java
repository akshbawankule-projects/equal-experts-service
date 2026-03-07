package com.ee.cart.service;

import com.ee.cart.client.PriceAPIClient;
import com.ee.cart.model.CartEntry;
import com.ee.cart.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartService {

    private final Map<String, CartEntry> shoppingCart = new HashMap<>();
    private final PriceAPIClient priceAPIClient;

    public ShoppingCartService(PriceAPIClient priceAPIClient) {
        this.priceAPIClient = priceAPIClient;
    }

    public void addProduct(String productName, int quantity) {

        CartEntry existingCartEntry = shoppingCart.get(productName);
        if (existingCartEntry != null) {
            existingCartEntry.setQuantity(existingCartEntry.getQuantity() + quantity);
        } else {
            Product product = new Product(productName, priceAPIClient.getPrice(productName));
            shoppingCart.put(productName, new CartEntry(product, quantity));
        }

    }

    public double getSubtotal() {
        double subtotal = shoppingCart.values()
                .stream()
                .mapToDouble(CartEntry::getTotalPrice)
                .sum();
        return round(subtotal);
    }

    public double getTax() {
        return round(getSubtotal() * 0.125);
    }

    public double getTotal() {
        return round(getSubtotal() + getTax());
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
