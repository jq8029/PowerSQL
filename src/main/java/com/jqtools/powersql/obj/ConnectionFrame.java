package com.jqtools.powersql.obj;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.utils.DBTools;

public class ConnectionFrame extends JFrame {
	private static final long serialVersionUID = 4752644080497862101L;

	public static final int INDEX_NAME = 0;
	public static final int INDEX_USER = 1;
	public static final int INDEX_PWD = 2;
	public static final int INDEX_URL = 3;
	public static final int INDEX_DRIVER = 4;

	private static final String TEXT[] = { Constants.CONNECTION_NAME, Constants.CONNECTION_USER,
			Constants.CONNECTION_PWD, Constants.CONNECTION_URL, Constants.CONNECTION_DRIVER_NAME };

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
			if (values[i] != null) {
				values[i].addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e) {
					}

					public void keyReleased(KeyEvent e) {
					}

					public void keyTyped(KeyEvent e) {
						saveButton.setEnabled(true);
					}
				});
			}

			this.panel.add(names[i]);
			this.panel.add(values[i]);
			height += 25;
		}

		height += 15;
		testButton.setBounds(x1 + width / 2, height, width, 20);
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				test();
			}
		});

		saveButton.setBounds(x1 + width + width / 2 + 15, height, width, 20);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		saveButton.setEnabled(false);

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

	public void test() {
		DatabaseInfo dbInfo = new DatabaseInfo();

		dbInfo.setUser(getValue(INDEX_USER));
		dbInfo.setPassword(getValue(INDEX_PWD));
		dbInfo.setUrl(getValue(INDEX_URL));
		dbInfo.setDriverName(getValue(INDEX_DRIVER));

		Connection conn = null;
		String error = null;

		try {
			conn = DBTools.createConnection(dbInfo);
		} catch (Exception ex) {
			error = ex.getMessage();
		}

		if (conn == null) {
			if (error == null) {
				NoticeMessage.showMessage("Unable to connect database.");
			} else {
				NoticeMessage.showMessage("Unable to connect database.\n" + error.toString());
			}
		} else {
			try {
				conn.close();
			} catch (Exception e) {
			}
			conn = null;

			NoticeMessage.showMessage("Connect database successfully.");
		}
	}

	public void save() {
		String conName = getValue(INDEX_NAME);
		DatabaseInfo dbInfo = DBTools.getDBConnection(conName);
		if (dbInfo == null) {
			dbInfo = new DatabaseInfo();
		}

		dbInfo.setUser(getValue(INDEX_USER));
		dbInfo.setPassword(getValue(INDEX_PWD));
		dbInfo.setUrl(getValue(INDEX_URL));
		dbInfo.setDriverName(getValue(INDEX_DRIVER));

		DBTools.updateDBConnection(conName, dbInfo);

		saveButton.setEnabled(false);
	}

	public void close() {
		this.setVisible(false);
	}

	private String getValue(int index) {
		String value = "";
		if (index < values.length && values[index].getText() != null) {
			value = values[index].getText().trim();
		}

		return value;
	}

	public static void main(String args[]) {
		new ConnectionFrame().setVisible(true);
	}
}
