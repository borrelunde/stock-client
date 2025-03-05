package com.borrelunde.stockclient;

import reactor.core.publisher.Flux;

/**
 * @author B. Lunde
 * @since 2025.03.05
 */
public interface StockClient {
	Flux<StockPrice> pricesFor(String symbol);
}
