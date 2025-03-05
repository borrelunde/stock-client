package com.borrelunde.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
class WebClientStockClientIntegrationTest {

	private final WebClient webClient = WebClient.builder().build();

	@Test
	@DisplayName("Should retrieve stock prices from the service")
	void shouldRetrieveStockPricesFromTheService() {
		// given
		StockClient webClientStockClient = new WebClientStockClient(webClient);

		// when
		Flux<StockPrice> prices = webClientStockClient.pricesFor("SYMBOL");

		// then
		Assertions.assertNotNull(prices);
		Flux<StockPrice> fivePrices = prices.take(5);
		Assertions.assertEquals(5, fivePrices.count().block());
		StockPrice stockPrice = fivePrices.blockFirst();
		Assertions.assertNotNull(stockPrice);
		Assertions.assertEquals("SYMBOL", stockPrice.getSymbol());
	}
}