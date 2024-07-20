package com.jqtools.powersql.obj;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private static ConnectionFrame conFrame = null;

	public static ConnectionFrame getInstance() {
		if (conFrame == null) {
			conFrame = new ConnectionFrame();
		}

		return conFrame;
	}

	private ConnectionFrame() {

	}
}
