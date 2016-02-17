package com.jean.counter.main;

import com.jean.counterb.utils.PropertiesUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResourceAsStream("/fxml/Scene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");
		String prop = "/application.properties";
		String name = PropertiesUtil.getProperty(prop, "project.name");
		String version = PropertiesUtil.getProperty(prop, "project.version");
		stage.setTitle(name + "    " + version);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
