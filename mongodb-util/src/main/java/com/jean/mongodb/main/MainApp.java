package com.jean.mongodb.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jean.mongodb.controller.MainController;
import com.jean.mongodb.utils.PropertiesUtil;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class MainApp extends Application {

	private ApplicationContext context = null;

	@Override
	public void init() throws Exception {
		context = new ClassPathXmlApplicationContext("/applicationContext.xml");
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		MainController controller = context.getBean(MainController.class);
		loader.setControllerFactory(new Callback<Class<?>, Object>() {

			@Override
			public Object call(Class<?> param) {
				return controller;
			}
		});
		Parent root = loader.load(getClass().getResourceAsStream("/fxml/Scene.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");
		String prop = "/application.properties";
		String name = PropertiesUtil.getProperty(prop, "project.name");
		String version = PropertiesUtil.getProperty(prop, "project.version");
		stage.setTitle(name + "    " + version);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/mongodb-logo.png")));
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Dialog<ButtonType> dialog = new Dialog<>();
				dialog.setContentText("\r\n\r\n是否退出？\r\n\r\n");
				dialog.setTitle("退出提示");
				dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				dialog.showAndWait().ifPresent(res -> {
					if (res != ButtonType.OK) {
						event.consume();
					}
				});
			}
		});
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
