package com.jqtools.powersql.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.obj.Session;

public class ColumnChangeFrame extends JFrame {

	private static final long serialVersionUID = 3686492535018911494L;

	private static ColumnChangeFrame instance = null;
	private Session session = null;
	private JTextField tableField = new JTextField();;

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

		JLabel tableLabel = new JLabel(Constants.LABEL_TABLE_NAME + " : ");
		JLabel columnLabel = new JLabel(Constants.LABEL_COLUMN_NAME + " : ");
		JLabel colTypeLabel = new JLabel(Constants.LABEL_COLUMN_TYPE + " : ");
		JLabel colLengthLabel = new JLabel(Constants.LABEL_COLUMN_LENGTH + " : ");
		JLabel colScaleLabel = new JLabel(Constants.LABEL_COLUMN_SCALE + " : ");

		x = x + 40;
		JButton changeButton = new JButton(Constants.BUTTON_CHANGE);
		changeButton.setBounds(x, y, 85, 18);
		JButton dropButton = new JButton(Constants.BUTTON_DROP);
		dropButton.setBounds(x + 95, y, 85, 18);
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
		cancelButton.setBounds(x + 190, y, 85, 18);

		panel.add(changeButton);
		panel.add(dropButton);
		panel.add(cancelButton);
		this.add(panel, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(420, 200));
		this.pack();
		setLocationRelativeTo(this);
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
