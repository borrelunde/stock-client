package com.borrelunde.stockui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
public class ChartApplication extends Application {
	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		applicationContext = new SpringApplicationBuilder(StockUiApplication.class).run();
	}

	@Override
	public void start(final Stage stage) {
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

	public static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(final Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return (Stage) getSource();
		}
	}
}
