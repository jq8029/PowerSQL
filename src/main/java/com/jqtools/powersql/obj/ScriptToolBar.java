package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;

public class ScriptToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = 2325100178568484815L;
	private JButton buttons[] = new JButton[Constants.TEXTS.length];
	private MainTextArea textArea = new MainTextArea();

	public ScriptToolBar() {
		int space = Constants.SPACE_03;
		int count = 0;

		for (int i = 0; i < Constants.TEXTS.length; i++) {
			buttons[i] = new JButton(new ImageIcon(Constants.ICON_PATH + Constants.TEXTS[i]));
			buttons[i].setActionCommand(Constants.TEXTS[i]);
			buttons[i].addActionListener(this);
			buttons[i].setToolTipText(Constants.TEXTS[i]);

			buttons[i].setBounds(Constants.SPACE_23 * count + space, Constants.SPACE_02, Constants.SPACE_21,
					Constants.SPACE_21);
			add(buttons[i]);

			// add group separator
			for (int idx = 0; idx < Constants.TOOLBAR_SEPARATOR.length; idx++) {
				if (i == Constants.TOOLBAR_SEPARATOR[idx]) {
					space += Constants.SPACE_10;
					this.addSeparator();
				}
			}
		}

		this.setForeground(Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
