package com.jean.autoLogin.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jean.autoLogin.constant.Constant;
import com.jean.autoLogin.entity.User;
import com.jean.autoLogin.service.LoginTask;
import com.jean.autoLogin.utils.SerializeUtil;
import com.jean.autoLogin.utils.StringUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	CheckBox rememberMe;
	@FXML
	CheckBox autoLogin;
	@FXML
	ComboBox<String> username;
	@FXML
	PasswordField password;
	@FXML
	Label message;
	@FXML
	Button login;
	@FXML
	Button cancel;

	LoginTask task;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		message.setText(null);
		username.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!StringUtil.isBlack(newValue)) {
					for (User user : Constant.USERS) {
						if (newValue.equals(user.getUsername())) {
							password.setText(user.getPassword());
							rememberMe.setSelected(true);
							autoLogin.setSelected(user.getAutoLogin());
							return;
						}
					}
					rememberMe.setSelected(false);
					autoLogin.setSelected(false);
				}
			}
		});
		LoginEventHandler loginEventHandler = new LoginEventHandler();

		username.setOnKeyPressed(loginEventHandler);
		password.setOnKeyPressed(loginEventHandler);
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				login();
			}
		});
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (task != null && task.isRunning()) {
					task.cancel();
					return;
				}
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
		});

		for (User user : Constant.USERS) {
			username.getItems().add(user.getUsername());
			if (user.getAutoLogin()) {
				username.getSelectionModel().select(user.getUsername());
				login();
			}
		}
	}

	private void login() {
		String userName = username.getValue();
		if (userName == null) {
			userName = username.getEditor().getText();
		}
		String pwd = password.getText();
		login.disableProperty().unbind();
		if (task != null && task.isRunning()) {
			return;
		}
		message.textProperty().unbind();
		message.setText(null);
		User user = new User(userName, pwd, autoLogin.isSelected());
		task = new LoginTask("http://10.0.2.100/webAuth/", user, rememberMe.isSelected());
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				if (task.getValue()) {
					updateUsers(user);
				}
			};
		});
		login.disableProperty().bind(task.runningProperty());
		message.textProperty().bind(task.messageProperty());
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	private class LoginEventHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				login();
			}
		};
	}

	private void updateUsers(User user) {
		if (rememberMe.isSelected()) {
			if (user.getAutoLogin()) {
				for (User u : Constant.USERS) {
					u.setAutoLogin(false);
				}
			}
			Constant.USERS.add(user);
			SerializeUtil.serialize(Constant.USERS, Constant.USER_INFO_FILE_PATH + Constant.USER_INFO_FILE_NAME);
		}
	}
}
