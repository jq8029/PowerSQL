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

	}
}
