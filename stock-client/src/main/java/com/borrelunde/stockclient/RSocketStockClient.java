package com.borrelunde.stockclient;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static java.time.Duration.ofSeconds;
import static reactor.util.retry.Retry.backoff;

/**
 * @author B. Lunde
 * @since 2025.03.05
 */
@Log4j2
public class RSocketStockClient implements StockClient {

	private final RSocketRequester rSocketRequester;

	public RSocketStockClient(final RSocketRequester rSocketRequester) {
		this.rSocketRequester = rSocketRequester;
	}

	@Override
	public Flux<StockPrice> pricesFor(final String symbol) {
		log.info("RSocket stock client");
		return rSocketRequester.route("stockPrices")
				.data(symbol)
				.retrieveFlux(StockPrice.class)
				.retryWhen(backoff(5, ofSeconds(1)).maxBackoff(ofSeconds(20)))
				.doOnError(IOException.class, e -> log.error(e.getMessage()));
	}
}
