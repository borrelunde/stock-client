package com.borrelunde.stockui;

import com.borrelunde.stockclient.StockClient;
import com.borrelunde.stockclient.StockPrice;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static java.lang.String.*;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Component
public class ChartController {

	@FXML
	public LineChart<String, Double> chart;

	private final StockClient stockClient;

	public ChartController(final StockClient stockClient) {
		this.stockClient = stockClient;
	}

	@FXML
	public void initialize() {
		final String symbolOne = "SYMBOL1";
		final PriceSubscriber priceSubscriberOne = new PriceSubscriber(symbolOne);
		stockClient.pricesFor(symbolOne).subscribe(priceSubscriberOne);

		final String symbolTwo = "SYMBOL2";
		final PriceSubscriber priceSubscriberTwo = new PriceSubscriber(symbolTwo);
		stockClient.pricesFor(symbolTwo).subscribe(priceSubscriberTwo);

		final ObservableList<Series<String, Double>> data = observableArrayList();
		data.add(priceSubscriberOne.getSeries());
		data.add(priceSubscriberTwo.getSeries());
		chart.setData(data);
	}

	private static class PriceSubscriber implements Consumer<StockPrice> {

		private final ObservableList<Data<String, Double>> seriesData = observableArrayList();
		private final Series<String, Double> series;

		public PriceSubscriber(final String symbol) {
			series = new Series<>(symbol, seriesData);
		}

		@Override
		public void accept(final StockPrice stockPrice) {
			Platform.runLater(() ->
					seriesData.add(new Data<>(
							valueOf(stockPrice.getTime().getSecond()),
							stockPrice.getPrice()))
			);
		}

		public Series<String, Double> getSeries() {
			return series;
		}
	}
}
