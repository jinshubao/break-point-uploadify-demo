package com.jean.counter.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileInfo {

	public FileInfo() {
		super();
	}

	public FileInfo(String path) {
		this.path.set(path);
	}

	private StringProperty path = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private LongProperty size = new SimpleLongProperty();
	private IntegerProperty lines = new SimpleIntegerProperty();

	public StringProperty pathProperty() {
		return path;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public LongProperty sizeProperty() {
		return size;
	}

	public IntegerProperty linesProperty() {
		return lines;
	}

	public String getPath() {
		return path.get();
	}

	public void setPath(String dir) {
		this.path.set(dir);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String type) {
		this.name.set(type);
	}

	public Long getSize() {
		return size.get();
	}

	public void setSize(Long size) {
		this.size.set(size);
	}

	public Integer getLines() {
		return lines.get();
	}

	public void setLines(Integer lines) {
		this.lines.set(lines);
	}

}
