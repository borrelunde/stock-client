package com.borrelunde.stockui;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.borrelunde.stockui.ChartApplication.StageReadyEvent;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	@Override
	public void onApplicationEvent(final StageReadyEvent event) {
		Stage stage = event.getStage();
	}
}
