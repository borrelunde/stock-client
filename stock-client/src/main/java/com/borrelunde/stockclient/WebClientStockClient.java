package com.borrelunde.stockclient;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

import static java.time.Duration.*;
import static reactor.util.retry.Retry.*;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Log4j2
public class WebClientStockClient implements StockClient {
	private final WebClient webClient;

	public WebClientStockClient(final WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Flux<StockPrice> pricesFor(final String symbol) {
		log.info("WebClient stock client");
		return webClient.get()
				.uri("http://localhost:8080/stocks/{symbol}", symbol)
				.retrieve()
				.bodyToFlux(StockPrice.class)
				.retryWhen(backoff(5, ofSeconds(1)).maxBackoff(ofSeconds(20)))
				.doOnError(IOException.class, e -> log.error(e.getMessage()));
	}
}
