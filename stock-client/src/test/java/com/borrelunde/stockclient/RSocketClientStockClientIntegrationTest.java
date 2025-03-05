package com.borrelunde.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@SpringBootTest
class RSocketClientStockClientIntegrationTest {

	@Autowired
	private RSocketRequester.Builder builder;

	private RSocketRequester createRSocketRequester() {
		return builder.tcp("localhost", 7000);
	}

	@Test
	@DisplayName("Should retrieve stock prices from the service")
	void shouldRetrieveStockPricesFromTheService() {
		// given
		RSocketStockClient rSocketClientStockClient = new RSocketStockClient(createRSocketRequester());

		// when
		Flux<StockPrice> prices = rSocketClientStockClient.pricesFor("SYMBOL");

		// then
		StepVerifier.create(prices.take(5))
				.expectNextMatches(aStockPrice -> aStockPrice.getSymbol().equals("SYMBOL"))
				.expectNextMatches(aStockPrice -> aStockPrice.getSymbol().equals("SYMBOL"))
				.expectNextMatches(aStockPrice -> aStockPrice.getSymbol().equals("SYMBOL"))
				.expectNextMatches(aStockPrice -> aStockPrice.getSymbol().equals("SYMBOL"))
				.expectNextMatches(aStockPrice -> aStockPrice.getSymbol().equals("SYMBOL"))
				.verifyComplete();
	}
}