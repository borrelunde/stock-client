package com.borrelunde.stockclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPrice {
	private String symbol;
	private Double price;
	private LocalDateTime time;
}
