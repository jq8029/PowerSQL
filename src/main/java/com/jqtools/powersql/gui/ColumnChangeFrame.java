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
	private JTextField tableField = new JTextField();
	private JTextField columnField = new JTextField();
	private JTextField colTypeField = new JTextField();
	private JTextField colLengthField = new JTextField();
	private JTextField colScaleField = new JTextField();

	public static ColumnChangeFrame getInstance() {
		if (instance == null) {
			instance = new ColumnChangeFrame();
		}

		return instance;
	}

	private ColumnChangeFrame() {
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(null);
		int x1 = 30, w1 = 150, x2 = 200, w2 = 300, y = 10, h = 18, hs = 25;

		JLabel tableLabel = new JLabel(Constants.LABEL_TABLE_NAME + " : ");
		JLabel columnLabel = new JLabel(Constants.LABEL_COLUMN_NAME + " : ");
		JLabel colTypeLabel = new JLabel(Constants.LABEL_COLUMN_TYPE + " : ");
		JLabel colLengthLabel = new JLabel(Constants.LABEL_COLUMN_LENGTH + " : ");
		JLabel colScaleLabel = new JLabel(Constants.LABEL_COLUMN_SCALE + " : ");

		tableLabel.setBounds(x1, y, w1, h);
		tableField.setBounds(x2, y, w2, h);
		y += hs;
		columnLabel.setBounds(x1, y, w1, h);
		y += hs;
		colTypeLabel.setBounds(x1, y, w1, h);
		y += hs;
		colLengthLabel.setBounds(x1, y, w1, h);
		y += hs;
		colScaleLabel.setBounds(x1, y, w1, h);
		y += 2 * hs;

		x1 = x1 + 40;
		JButton changeButton = new JButton(Constants.BUTTON_CHANGE);
		changeButton.setBounds(x1, y, 85, h);
		JButton dropButton = new JButton(Constants.BUTTON_DROP);
		dropButton.setBounds(x1 + 95, y, 85, h);
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
		cancelButton.setBounds(x1 + 190, y, 85, h);

		panel.add(tableLabel);
		panel.add(columnLabel);
		panel.add(colTypeLabel);
		panel.add(colLengthLabel);
		panel.add(colScaleLabel);
		panel.add(tableField);
		panel.add(columnField);
		panel.add(colTypeField);
		panel.add(colLengthField);
		panel.add(colScaleField);
		panel.add(changeButton);
		panel.add(dropButton);
		panel.add(cancelButton);
		this.add(panel, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(420, 230));
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
