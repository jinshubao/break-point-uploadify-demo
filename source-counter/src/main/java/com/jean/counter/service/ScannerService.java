package com.jean.counter.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.jean.counter.entity.FileInfo;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

public class ScannerService extends Service<TreeItem<FileInfo>> {

	File rootDir;
	TreeItem<FileInfo> root;

	@Override
	protected Task<TreeItem<FileInfo>> createTask() {
		return new Task<TreeItem<FileInfo>>() {
			@Override
			protected TreeItem<FileInfo> call() throws Exception {
				updateProgress(-1, 1);
				listFiles(rootDir, root);
				updateProgress(1, 1);
				updateMessage("扫描完成");
				return root;
			}

			void listFiles(File rootDir, TreeItem<FileInfo> root) throws FileNotFoundException {
				for (File f : rootDir.listFiles()) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSize(f.length());
					TreeItem<FileInfo> treeItem = new TreeItem<FileInfo>(fileInfo);
					if (f.isHidden()) {
						continue;
					}
					updateMessage("正在扫描 " + f.getAbsolutePath());
					if (f.isFile()) {
						fileInfo.setPath(f.getParent());
						String name = f.getName();
						fileInfo.setName(name);
						Scanner scanner = null;
						try {
							scanner = new Scanner(f);
							while (scanner.hasNextLine()) {
								scanner.nextLine();
								fileInfo.setLines(fileInfo.getLines() + 1);
							}
						} finally {
							if (scanner != null) {
								scanner.close();
							}
						}
					} else if (f.isDirectory()) {
						fileInfo.setPath(f.getAbsolutePath());
						listFiles(f, treeItem);
					}
					root.getChildren().add(treeItem);
				}
			}
		};
	}

	public File getRootDir() {
		return rootDir;
	}

	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	public TreeItem<FileInfo> getRoot() {
		return root;
	}

	public void setRoot(TreeItem<FileInfo> root) {
		this.root = root;
	}

}
