package com.ee.cart.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceAPIClientTest {

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<String> response;

    @InjectMocks
    private PriceAPIClient priceAPIClient;

    @Test
    void shouldReturnPriceFromApiResponse() throws Exception {

        when(response.body()).thenReturn(
                """
                {
                       "title": "Weetabix",
                       "price": 9.98
                   }
                """);
        when(client.send(any(),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(response);

        assertEquals(9.98, priceAPIClient.getPrice("cornflakes"));
    }
}