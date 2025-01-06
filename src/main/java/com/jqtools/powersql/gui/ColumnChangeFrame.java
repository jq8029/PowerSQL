package com.jqtools.powersql.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jqtools.powersql.constants.Constants;
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
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(null);
		int x = 30, y = 50;

		JButton changeButton = new JButton(Constants.BUTTON_CHANGE);
		changeButton.setBounds(x, y, 85, 18);

		panel.add(changeButton);
		this.add(panel, BorderLayout.CENTER);
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
