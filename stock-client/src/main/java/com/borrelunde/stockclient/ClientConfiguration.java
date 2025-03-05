package com.borrelunde.stockclient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Configuration
public class ClientConfiguration {
	@Bean
	@Profile("sse")
	public StockClient webClientStockClient(final WebClient webClient) {
		return new WebClientStockClient(webClient);
	}

	@Bean
	@Profile("rsocket")
	public StockClient rSocketStockClient(final RSocketRequester rSocketRequester) {
		return new RSocketStockClient(rSocketRequester);
	}

	@Bean
	public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
		return builder.tcp("localhost", 7000);
	}

	@Bean
	// This annotation is used to prevent the creation of a
	// bean if another bean of the same type is already present.
	@ConditionalOnMissingBean
	public WebClient webClient() {
		return WebClient.builder().build();
	}
}
