package com.jean.counter.controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.jean.counter.entity.FileInfo;
import com.jean.counter.service.ScannerService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

public class MainController implements Initializable {

	@FXML
	Button btnDirBrowse;
	@FXML
	CheckBox cbJava;
	@FXML
	CheckBox cbJs;
	@FXML
	CheckBox cbCss;
	@FXML
	CheckBox cbJsp;
	@FXML
	CheckBox cbCs;
	@FXML
	CheckBox cbXml;
	@FXML
	CheckBox cbProperties;
	@FXML
	CheckBox cbHtml;
	@FXML
	CheckBox cbAsp;
	@FXML
	CheckBox cbFxml;
	@FXML
	CheckBox cbTxt;
	@FXML
	CheckBox cbH;
	@FXML
	CheckBox cbSh;
	@FXML
	CheckBox cbC;
	@FXML
	CheckBox cbPhp;
	@FXML
	CheckBox cbCpp;
	@FXML
	TreeTableView<FileInfo> treeTableView;
	@FXML
	Label msg;
	@FXML
	Label lines;
	@FXML
	Label filesSize;
	@FXML
	ProgressIndicator progress;
	@FXML
	CheckBox cbAll;
	@FXML
	ListView<String> dirList;

	DecimalFormat format = new DecimalFormat("#.##");

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		treeTableView.getColumns().get(0).setCellValueFactory(new TreeItemPropertyValueFactory<>("path"));
		treeTableView.getColumns().get(1).setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
		TreeTableColumn<FileInfo, String> sizeColumn = (TreeTableColumn<FileInfo, String>) treeTableView//
				.getColumns().get(2);
		sizeColumn.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<FileInfo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FileInfo, String> param) {
						return new SimpleStringProperty(fileSize(param.getValue().getValue().getSize()));
					}
				});
		treeTableView.getColumns().get(3).setCellValueFactory(new TreeItemPropertyValueFactory<>("lines"));

		//
		cbAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				cbJava.setSelected(newValue);
				cbJs.setSelected(newValue);
				cbCss.setSelected(newValue);
				cbJsp.setSelected(newValue);
				cbCs.setSelected(newValue);
				cbXml.setSelected(newValue);
				cbProperties.setSelected(newValue);
				cbHtml.setSelected(newValue);
				cbAsp.setSelected(newValue);
				cbFxml.setSelected(newValue);
				cbTxt.setSelected(newValue);
				cbH.setSelected(newValue);
				cbSh.setSelected(newValue);
				cbC.setSelected(newValue);
				cbPhp.setSelected(newValue);
				cbCpp.setSelected(newValue);
			}
		});
		//
		final ScannerService service = new ScannerService();
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				TreeItem<FileInfo> item = service.getValue();
				treeTableView.setRoot(item);
			};
		});
		service.setOnScheduled(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				if (treeTableView.getRoot() != null) {
					treeTableView.getRoot().getChildren().clear();
					treeTableView.setRoot(null);
				}
			}
		});
		msg.textProperty().bind(service.messageProperty());
		progress.visibleProperty().bind(service.runningProperty());
		progress.progressProperty().bind(service.progressProperty());
		btnDirBrowse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File file = directoryChooser.showDialog(btnDirBrowse.getScene().getWindow());
				if (file == null || !file.isDirectory()) {
					return;
				}

				dirList.getItems().add(file.getAbsolutePath());
				service.setRootDir(file);
				FileInfo fileInfo = new FileInfo(file.getAbsolutePath());
				TreeItem<FileInfo> root = new TreeItem<>(fileInfo);
				root.setExpanded(true);
				service.setRoot(root);
				service.restart();
			}
		});
	}

	public String fileSize(long size) {
		if (size < 1024) {
			return size + " B";
		} else if (size < 1024 * 1024) {
			return format.format(size / (1024.0)) + " KB";
		} else if (size < 1024 * 1024 * 1024.0) {
			return format.format(size / (1024 * 1024.0)) + " MB";
		} else if (size < 1024 * 1024 * 1024 * 1024.0) {
			return format.format(size / (1024 * 1024 * 1024.0)) + " GB";
		}
		return "文件太大...";
	}

}
