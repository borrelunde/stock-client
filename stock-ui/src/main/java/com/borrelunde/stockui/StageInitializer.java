package com.borrelunde.stockui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.borrelunde.stockui.ChartApplication.StageReadyEvent;

/**
 * @author B. Lunde
 * @since 2025.03.04
 */
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
	@Value("classpath:/chart.fxml")
	private Resource chartResource;
	private final String applicationTitle;
	private final ApplicationContext applicationContext;

	public StageInitializer(@Value("${spring.application.ui.title}") final String applicationTitle,
	                        final ApplicationContext applicationContext) {
		this.applicationTitle = applicationTitle;
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(final StageReadyEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(chartResource.getURL());
			fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
			final Parent parent = fxmlLoader.load();

			Stage stage = event.getStage();
			stage.setScene(new Scene(parent, 800, 600));
			stage.setTitle(applicationTitle);
			stage.show();
		} catch (IOException e) {
			// Throw a runtime exception to keep the demo short.
			throw new RuntimeException(e);
		}
	}
}
