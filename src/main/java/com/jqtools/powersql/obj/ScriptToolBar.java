package com.jqtools.powersql.obj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.utils.Tools;

public class ScriptToolBar extends JToolBar implements ActionListener, CaretListener {

	private static final long serialVersionUID = 2325100178568484815L;
	private JButton buttons[] = new JButton[Constants.SQL_TEXTS.length];
	private MainTextArea textArea = new MainTextArea();
	private StatusPanel statusPanel = new StatusPanel();

	public ScriptToolBar() {
		int space = Constants.SPACE_03;
		int count = 0;

		for (int i = 0; i < Constants.SQL_TEXTS.length; i++) {
			buttons[i] = Tools.getImageButton(Constants.SQL_TEXTS[i], Constants.SQL_ENABLES[i]);
			buttons[i].addActionListener(this);

			buttons[i].setBounds(Constants.SPACE_23 * count + space, Constants.SPACE_02, Constants.SPACE_21,
					Constants.SPACE_21);
			add(buttons[i]);

			// add group separator
			for (int idx = 0; idx < Constants.SQL_TOOLBAR_SEPARATOR.length; idx++) {
				if (i == Constants.SQL_TOOLBAR_SEPARATOR[idx]) {
					space += Constants.SPACE_10;
					this.addSeparator();
				}
			}
		}

		this.setForeground(Color.BLACK);
		this.textArea.addCaretListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();

		if (text == null) {
			return;
		}

		try {
			if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_EXECUTE])) {
				this.textArea.executeSQL();
			} else if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_UNDO])) {
				this.textArea.getUndoManager().undo();
				this.setButtonEnabled(Constants.SQL_TOOLBAR_UNDO, this.getTextArea().getUndoManager().canUndo());
				this.setButtonEnabled(Constants.SQL_TOOLBAR_REDO, this.getTextArea().getUndoManager().canRedo());
			} else if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_REDO])) {
				this.textArea.getUndoManager().redo();
				this.setButtonEnabled(Constants.SQL_TOOLBAR_UNDO, this.getTextArea().getUndoManager().canUndo());
				this.setButtonEnabled(Constants.SQL_TOOLBAR_REDO, this.getTextArea().getUndoManager().canRedo());
			} else if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_CUT])) {
				this.textArea.cut();
			} else if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_COPY])) {
				this.textArea.copy();
			} else if (text.equals(Constants.SQL_TEXTS[Constants.SQL_TOOLBAR_PASTE])) {
				this.textArea.paste();
			}
		} catch (Exception ex) {
			MessageLogger.error(ex);
		}
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

	@Override
	public void caretUpdate(CaretEvent e) {
		// update the location in text area
		statusPanel.setPos(this.getTextArea().getCaretPosition() + 1, (this.getTextArea().getCaretLineNumber() + 1),
				(this.getTextArea().getCaretOffsetFromLineStart() + 1));

		// update cut/copy/undo/redo button
		int dot = e.getDot(), mark = e.getMark();
		if (dot != mark) {
			setButtonEnabled(Constants.SQL_TOOLBAR_CUT, true);
			setButtonEnabled(Constants.SQL_TOOLBAR_COPY, true);
		}

		this.setButtonEnabled(Constants.SQL_TOOLBAR_UNDO, this.getTextArea().getUndoManager().canUndo());
		this.setButtonEnabled(Constants.SQL_TOOLBAR_REDO, this.getTextArea().getUndoManager().canRedo());
	}

	public MainTextArea getTextArea() {
		return textArea;
	}

	public StatusPanel getStatusPanel() {
		return statusPanel;
	}
}
