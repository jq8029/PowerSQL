package com.jqtools.powersql.obj;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBarPanel extends JPanel {

	private JLabel posLabel = new JLabel("    Pos : 1        Row : 1,  Col : 1  ");

	public StatusBarPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		this.add(this.posLabel);
	}
}
