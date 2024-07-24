package com.jqtools.powersql.obj;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JButton testButton = new JButton(Constants.BUTTON_TEST);
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
		int width = 80;

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

		height += 15;
		testButton.setBounds(x1 + width / 2, height, width, 20);

		saveButton.setBounds(x1 + width + width / 2 + 15, height, width, 20);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		closeButton.setBounds(x1 + width * 2 + width / 2 + 30, height, width, 20);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		this.panel.add(testButton);
		this.panel.add(saveButton);
		this.panel.add(closeButton);

		this.getContentPane().add("Center", panel);
		this.panel.setPreferredSize(new Dimension(390, 230));
		this.pack();
	}

	public void save() {

	}

	public void close() {

	}

	public static void main(String args[]) {
		new ConnectionFrame().setVisible(true);
	}
}
