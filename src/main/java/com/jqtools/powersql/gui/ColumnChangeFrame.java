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
import com.jqtools.powersql.log.NoticeMessage;
import com.jqtools.powersql.obj.ColumnInfo;
import com.jqtools.powersql.obj.Session;
import com.jqtools.powersql.utils.ExecuteSQL;
import com.jqtools.powersql.utils.Tools;

public class ColumnChangeFrame extends JFrame {

	private static final long serialVersionUID = 3686492535018911494L;

	private static ColumnChangeFrame instance = null;
	private JPanel panel = new JPanel(null);
	private Session session = null;
	private ColumnInfo info = null;
	private JTextField tableField = new JTextField();
	private JTextField columnField = new JTextField();
	private JTextField colNewField = new JTextField();
	private JComboBox<String> colTypeBox = new JComboBox<String>();
	private JTextField colLengthField = new JTextField();
	private JTextField colScaleField = new JTextField();
	private JButton changeButton = new JButton(Constants.BUTTON_CHANGE);
	private JButton dropButton = new JButton(Constants.BUTTON_DROP);

	public static ColumnChangeFrame getInstance() {
		if (instance == null) {
			instance = new ColumnChangeFrame();
		}

		return instance;
	}

	private ColumnChangeFrame() {
		this.setLayout(new BorderLayout());
		this.setTitle(Constants.TITLE_COLUMN);
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
		colTypeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeColType();
			}
		});

		y += hs;
		colLengthLabel.setBounds(x1, y, w1, h);
		colLengthField.setBounds(x2, y, w2, h);
		y += hs;
		colScaleLabel.setBounds(x1, y, w1, h);
		colScaleField.setBounds(x2, y, w2, h);
		y += 2 * hs;

		x1 = x1 + 40;
		changeButton.setBounds(x1, y, 85, h);
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
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
		newInfo.setTypeName(String.valueOf(colTypeBox.getSelectedItem()));
		newInfo.setNumericLen(Tools.getInt(colLengthField.getText().trim(), newInfo.getNumericLen()));
		newInfo.setNumericScale(Tools.getInt(colScaleField.getText().trim(), newInfo.getNumericScale()));

		if (info.getColumnName() == null) {
			if (ExecuteSQL.execute(session.getConnection(), session.getDbData().createColumnName(newInfo))) {
				NoticeMessage.showMessage(Constants.MSG_SUCCESS_CRT_COL);
				this.setVisible(false);
			} else {
				NoticeMessage.showMessage(Constants.MSG_FAIL_CRT_COL_NAME);
				return;
			}
		} else {
			boolean success = false;
			if (!(Tools.isEqual(newInfo.getTypeName(), info.getTypeName())
					&& newInfo.getNumericLen() == info.getNumericLen()
					&& newInfo.getNumericScale() == info.getNumericScale())) {
				if (ExecuteSQL.execute(session.getConnection(), session.getDbData().changeColumn(info, newInfo))) {
					success = true;
				} else {
					NoticeMessage.showMessage(Constants.MSG_FAIL_CHG_COL_TYPE);
					return;
				}
			}

			if (newInfo.equal(info)) {
			} else {
				if (!Tools.isEqual(newInfo.getColumnName(), info.getColumnName())) {
					if (ExecuteSQL.execute(session.getConnection(),
							session.getDbData().renameColumnName(info, newInfo))) {
						NoticeMessage.showMessage(Constants.MSG_SUCCESS_CHG_COL);
						this.setVisible(false);
					} else {
						NoticeMessage.showMessage(Constants.MSG_FAIL_CHG_COL_NAME);
						return;
					}
				}
			}
		}
	}

	public void drop() {
		if (ExecuteSQL.execute(session.getConnection(), session.getDbData().dropColumnSQL(info))) {
			NoticeMessage.showMessage(Constants.MSG_SUCCESS_DROP_COL);
			this.setVisible(false);
		} else {
			NoticeMessage.showMessage(Constants.MSG_FAIL_DROP_COL);
			return;
		}
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

		if (info.getColumnName() == null) {
			panel.remove(columnField);
			changeButton.setText(Constants.BUTTON_CREATE);
			dropButton.setEnabled(false);
			colNewField.setText("");
			colNewField.setBounds(115, 35, 270, 18);

			colLengthField.setText("");
			colScaleField.setText("");
		} else {
			panel.add(columnField);
			changeButton.setText(Constants.BUTTON_CHANGE);
			dropButton.setEnabled(true);
			colNewField.setText(info.getColumnName());
			colNewField.setBounds(253, 35, 133, 18);

			columnField.setText(info.getColumnName());
			colTypeBox.setSelectedItem(info.getTypeName());
			if (session.getDbData().getDataTypesWithQuota().contains(info.getTypeName())) {
				colScaleField.setEditable(false);
				if (info.getNumericLen() > 0) {
					colLengthField.setText(String.valueOf(info.getNumericLen()));
				}
			} else {
				colScaleField.setEditable(true);
				if (info.getNumericLen() > 0) {
					colLengthField.setText(String.valueOf(info.getNumericLen()));
				} else if (info.getMaxLength() > 0) {
					colLengthField.setText(String.valueOf(info.getMaxLength()));
				}

				colScaleField.setText(String.valueOf(info.getNumericScale()));
			}
		}

		this.pack();
		this.repaint();
	}

	public void cancel() {
		this.setVisible(false);
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	public void changeColType() {
		Object type = colTypeBox.getSelectedItem();
		colLengthField.setEditable(session.getDbData().getDataTypesWithLen().contains(type));
		colScaleField.setEditable(session.getDbData().getDataTypesWithScale().contains(type));
	}

	public static void main(String[] args) {
		new ColumnChangeFrame().setVisible(true);
	}
}
