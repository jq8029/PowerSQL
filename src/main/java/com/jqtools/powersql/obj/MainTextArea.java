package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.jqtools.powersql.constants.Constants;

public class MainTextArea extends RSyntaxTextArea {

	private static final long serialVersionUID = 1064609692697845846L;
	private MainTextArea textArea = null;

	public MainTextArea() {
		textArea = this;

		// register key (Ctrl + Enter) to execute current sql
		getActionMap().put(Constants.KEY_EXEC_CURRENT_SQL, new AbstractAction(Constants.KEY_EXEC_CURRENT_SQL) {
			private static final long serialVersionUID = 4711206524548141227L;

			public void actionPerformed(ActionEvent evt) {
				try {
					if (textArea != null && textArea.getText() != null && textArea.getText().trim().length() > 0) {
						String sql = textArea.getSelectedText();

						if (sql == null || sql.length() == 0) {
							int pos = textArea.getCaretPosition();
							int start = textArea.getText().lastIndexOf(";", pos);
							int end = textArea.getText().indexOf(";", pos);
							if (start < 0) {
								start = 0;
							} else {
								start = start + 1;
							}
							if (end < 0)
								end = textArea.getText().length();

							if (end <= start) {
								return;
							} else {
								sql = textArea.getText().substring(start, end);
							}
						}

					}
				} catch (Exception e) {
				}
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK),
				Constants.KEY_EXEC_CURRENT_SQL);

	}

}
