package com.jean.mongodb.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

@Controller
public class MainController implements Initializable {

	@FXML
	private TreeView<Object> dbTreeview;
	@FXML
	private VBox dataList;
	@FXML
	private SplitPane splitPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		splitPane.setDividerPosition(0, 0.2);
		TreeItem<Object> root = new TreeItem<>("数据库列表");
		dbTreeview.setRoot(root);
		dbTreeview.setShowRoot(false);
		dbTreeview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Object>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue,
					TreeItem<Object> newValue) {
			}
		});
	}
}
