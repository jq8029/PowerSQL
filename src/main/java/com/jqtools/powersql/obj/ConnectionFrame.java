package com.jqtools.powersql.obj;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jqtools.powersql.constants.Constants;

public class ConnectionFrame extends JFrame {
	private static final long serialVersionUID = 4752644080497862101L;

	public static final int INDEX_NAME = 0;
	public static final int INDEX_USER = 1;
	public static final int INDEX_PWD = 2;
	public static final int INDEX_URL = 3;
	public static final int INDEX_DRIVER = 4;
	public static final int INDEX_JARS = 5;

	private static final String TEXT[] = { Constants.CONNECTION_NAME, Constants.CONNECTION_USER,
			Constants.CONNECTION_PWD, Constants.CONNECTION_URL, Constants.CONNECTION_DRIVER_NAME,
			Constants.CONNECTION_DRIVER_JAR };

	private JLabel names[] = new JLabel[TEXT.length];
	private JTextField values[] = new JTextField[TEXT.length];
	private JButton addButton = new JButton(Constants.BUTTON_ADD);
	private JButton saveButton = new JButton(Constants.BUTTON_SAVE);
	private JButton closeButton = new JButton(Constants.BUTTON_CLOSE);
	private JPanel panel = new JPanel(null);

	private static ConnectionFrame conFrame = null;

	public static ConnectionFrame getInstance() {
		if (conFrame == null) {
			conFrame = new ConnectionFrame();
		}

		return conFrame;
	}

	private ConnectionFrame() {
		this.setTitle(Constants.TITLE_CONNECTION);

		int x1 = 20;
		int x2 = 130;
		int height = 25;
		int width1 = 150;
		int width2 = 240;

		for (int i = 0; i < names.length; i++) {
			names[i] = new JLabel(TEXT[i]);

			names[i].setBounds(x1, height, width1, 20);

			if (i == INDEX_PWD) {
				values[i] = new JPasswordField();
			} else {
				values[i] = new JTextField();
				values[i].setBounds(x2, height, width2, 20);
			}
			values[i].setBounds(x2, height, width2, 20);

			this.panel.add(names[i]);
			this.panel.add(values[i]);
			height += 25;
		}

		this.getContentPane().add("Center", panel);
		this.panel.setPreferredSize(new Dimension(650, 500));
		this.pack();
	}

	public static void main(String args[]) {
		new ConnectionFrame().setVisible(true);
	}
}
