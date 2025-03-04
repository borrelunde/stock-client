package com.borrelunde.stockclient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Configuration
public class ClientConfiguration {
	@Bean
	public WebClientStockClient webClientStockClient(final WebClient webClient) {
		return new WebClientStockClient(webClient);
	}

	@Bean
	// This annotation is used to prevent the creation of a
	// bean if another bean of the same type is already present.
	@ConditionalOnMissingBean
	public WebClient webClient() {
		return WebClient.builder().build();
	}
}
