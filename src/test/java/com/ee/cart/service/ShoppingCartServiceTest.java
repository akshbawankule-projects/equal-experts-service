package com.ee.cart.service;

import com.ee.cart.client.PriceAPIClient;
import com.ee.cart.model.CartEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private PriceAPIClient priceAPIClient;

    @InjectMocks
    private ShoppingCartService cartService;

    @Test
    void shouldCalculateTotalCorrectly() {

        when(priceAPIClient.getPrice("cornflakes")).thenReturn(2.52);
        when(priceAPIClient.getPrice("weetabix")).thenReturn(9.98);

        cartService.addProduct("cornflakes", 1);
        cartService.addProduct("cornflakes", 1);
        cartService.addProduct("weetabix", 1);

        assertEquals(15.02, cartService.getSubtotal());
        assertEquals(1.88, cartService.getTax());
        assertEquals(16.90, cartService.getTotal());

        verify(priceAPIClient).getPrice("weetabix");
        verify(priceAPIClient).getPrice("cornflakes");

    }

    @Test
    void shouldHandlePriceApiFailure() {

        when(priceAPIClient.getPrice("Apple")).thenThrow(new RuntimeException("API down"));

        assertThrows(RuntimeException.class,
                () -> cartService.addProduct("Apple", 1));

    }
}