package com.jqtools.powersql.gui;

import javax.swing.JFrame;

import com.jqtools.powersql.obj.Session;

public class ColumnChangeFrame extends JFrame {

	private static final long serialVersionUID = 3686492535018911494L;

	private static ColumnChangeFrame instance = null;
	private Session session = null;

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

	public void setSession(Session session) {
		this.session = session;
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
