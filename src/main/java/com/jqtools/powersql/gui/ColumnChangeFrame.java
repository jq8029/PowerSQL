package com.jqtools.powersql.gui;

import javax.swing.JFrame;

public class ColumnChangeFrame extends JFrame {

	private static ColumnChangeFrame instance = null;

	public static ColumnChangeFrame getInstance() {
		if (instance == null) {
			instance = new ColumnChangeFrame();
		}

		return instance;
	}

	private ColumnChangeFrame() {
		initialize();
	}

	public void initialize() {

	}

	public void change() {

	}

	public void drop() {

	}

	public void cancel() {
		this.setVisible(false);
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	public static void main(String[] args) {
		new ColumnChangeFrame().setVisible(true);
	}
}
