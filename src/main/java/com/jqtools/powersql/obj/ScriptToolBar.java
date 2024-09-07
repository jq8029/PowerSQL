package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;

public class ScriptToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = 2325100178568484815L;
	private JButton buttons[] = new JButton[Constants.TEXTS.length];
	private MainTextArea textArea = new MainTextArea();

	public ScriptToolBar() {
		int space = Constants.SPACE_03;
		int count = 0;

		for (int i = 0; i < Constants.TEXTS.length; i++) {
			buttons[i] = new JButton(
					new ImageIcon(Constants.ICON_PATH + Constants.TEXTS[i] + Constants.ICON_EXTENSION));
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
		String text = e.getActionCommand();

		if (text == null) {
			return;
		}

		try {
			if (text.equals(Constants.TEXTS[Constants.TOOLBAR_EXECUTE])) {
				this.textArea.executeSQL();
			} else if (text.equals(Constants.TEXTS[Constants.TOOLBAR_UNDO])) {
				this.textArea.getUndoManager().undo();
				this.setButtonEnabled(Constants.TOOLBAR_UNDO, this.getTextArea().getUndoManager().canUndo());
				this.setButtonEnabled(Constants.TOOLBAR_REDO, this.getTextArea().getUndoManager().canRedo());
			} else if (text.equals(Constants.TEXTS[Constants.TOOLBAR_REDO])) {
				this.textArea.getUndoManager().redo();
				this.setButtonEnabled(Constants.TOOLBAR_UNDO, this.getTextArea().getUndoManager().canUndo());
				this.setButtonEnabled(Constants.TOOLBAR_REDO, this.getTextArea().getUndoManager().canRedo());
			} else if (text.equals(Constants.TEXTS[Constants.TOOLBAR_CUT])) {
				this.textArea.cut();
			}
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
	}

	public MainTextArea getTextArea() {
		return textArea;
	}

	public boolean isButtonEnabled(int buttonId) {
		if (buttonId < 0 || buttonId >= buttons.length || buttons[buttonId] == null)
			return false;

		return buttons[buttonId].isEnabled();
	}

	public void setButtonEnabled(int buttonId, boolean enable) {
		if (buttonId < 0 || buttonId >= buttons.length || buttons[buttonId] == null)
			return;

		buttons[buttonId].setEnabled(enable);
	}
}
