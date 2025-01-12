package com.jqtools.powersql.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.utils.Tools;

public class ColumnChangeFrame extends JFrame {

	private static final long serialVersionUID = 3686492535018911494L;

	private static ColumnChangeFrame instance = null;
	private Session session = null;
	private ColumnInfo info = null;
	private JTextField tableField = new JTextField();
	private JTextField columnField = new JTextField();
	private JTextField colNewField = new JTextField();
	private JComboBox<String> colTypeBox = new JComboBox<String>();
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
		int x1 = 15, w1 = 150, x2 = 115, w2 = 270, y = 10, h = 18, hs = 25;

		JLabel tableLabel = new JLabel(Constants.LABEL_TABLE_NAME + " : ");
		JLabel columnLabel = new JLabel(Constants.LABEL_COLUMN_NAME + " : ");
		JLabel colTypeLabel = new JLabel(Constants.LABEL_COLUMN_TYPE + " : ");
		JLabel colLengthLabel = new JLabel(Constants.LABEL_COLUMN_LENGTH + " : ");
		JLabel colScaleLabel = new JLabel(Constants.LABEL_COLUMN_SCALE + " : ");

		tableLabel.setBounds(x1, y, w1, h);
		tableField.setBounds(x2, y, w2, h);
		tableField.setEditable(false);
		y += hs;
		columnLabel.setBounds(x1, y, w1, h);
		columnField.setBounds(x2, y, w2 / 2, h);
		columnField.setEditable(false);
		colNewField.setBounds(x2 + w2 / 2 + 3, y, w2 / 2 - 2, h);
		y += hs;
		colTypeLabel.setBounds(x1, y, w1, h);
		colTypeBox.setBounds(x2, y, w2, h);
		y += hs;
		colLengthLabel.setBounds(x1, y, w1, h);
		colLengthField.setBounds(x2, y, w2, h);
		y += hs;
		colScaleLabel.setBounds(x1, y, w1, h);
		colScaleField.setBounds(x2, y, w2, h);
		y += 2 * hs;

		x1 = x1 + 40;
		JButton changeButton = new JButton(Constants.BUTTON_CHANGE);
		changeButton.setBounds(x1, y, 85, h);
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
		JButton dropButton = new JButton(Constants.BUTTON_DROP);
		dropButton.setBounds(x1 + 95, y, 85, h);
		dropButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drop();
			}
		});
		JButton cancelButton = new JButton(Constants.BUTTON_CANCEL);
		cancelButton.setBounds(x1 + 190, y, 85, h);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		panel.add(tableLabel);
		panel.add(columnLabel);
		panel.add(colTypeLabel);
		panel.add(colLengthLabel);
		panel.add(colScaleLabel);
		panel.add(tableField);
		panel.add(columnField);
		panel.add(colNewField);
		panel.add(colTypeBox);
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
		ColumnInfo newInfo = (ColumnInfo) info.clone();
		newInfo.setColumnName(colNewField.getText().trim());
		newInfo.setNumericLen(Tools.getInt(colNewField.getText().trim(), newInfo.getNumericLen()));

		if (!newInfo.equal(info)) {
			if (!Tools.isEqual(newInfo.getColumnName(), info.getColumnName())) {
				session.getDbData().renameColumnName(info, newInfo);
			}
		}

		if (!(Tools.isEqual(newInfo.getTypeName(), info.getTypeName())
				&& newInfo.getNumericLen() == info.getNumericLen()
				&& newInfo.getNumericScale() == info.getNumericScale())) {
			session.getDbData().changeColumn(info, newInfo);
		}
	}

	public void drop() {
		session.getDbData().dropColumnSQL(info);
	}

	public void setData(Session session, ColumnInfo info) {
		this.session = session;
		this.info = info;

		colTypeBox.removeAllItems();
		if (session.getDbData().getAllDataTypes().size() > 0) {
			for (String item : session.getDbData().getAllDataTypes()) {
				colTypeBox.addItem(item);
			}
		}

		tableField.setText(info.getName());
		columnField.setText(info.getColumnName());
		colTypeBox.setSelectedItem(info.getTypeName());
		if (session.getDbData().getDataTypesWithQuota().contains(info.getTypeName())) {
			colLengthField.setEditable(false);
			colScaleField.setEditable(false);
			colLengthField.setEditable(false);
			colScaleField.setEditable(false);
		} else {
			colLengthField.setEditable(true);
			colScaleField.setEditable(true);
			if (info.getNumericLen() > 0) {
				colLengthField.setText(String.valueOf(info.getNumericLen()));
			} else if (info.getMaxLength() > 0) {
				colLengthField.setText(String.valueOf(info.getMaxLength()));
			}

			colScaleField.setText(String.valueOf(info.getNumericScale()));
		}
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
