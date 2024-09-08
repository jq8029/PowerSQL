package com.jqtools.powersql.obj;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = -2647008365234019097L;

	private JLabel posLabel = new JLabel("    Pos : 1        Row : 1,  Col : 1  ");

	public StatusPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		this.add(this.posLabel);
	}

	public void setPos(int pos, int row, int col) {
		this.posLabel.setText("    Pos : " + pos + "        Row : " + row + ",  Col : " + col + "  ");
	}
}
