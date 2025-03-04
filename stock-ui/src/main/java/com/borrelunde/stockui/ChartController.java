package com.borrelunde.stockui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Component;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Component
public class ChartController {
	@FXML
	public LineChart<String, Double> chart;
}
