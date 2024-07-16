package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.jqtools.powersql.constants.Constants;

public class MainTextArea extends RSyntaxTextArea {

	private static final long serialVersionUID = 1064609692697845846L;
	private MainTextArea textArea = null;

	public MainTextArea() {
		// register key to execute current sql
		getActionMap().put(Constants.KEY_EXEC_CURRENT_SQL, new AbstractAction(Constants.KEY_EXEC_CURRENT_SQL) {
			private static final long serialVersionUID = 4711206524548141227L;

			public void actionPerformed(ActionEvent evt) {
				try {
					if (textArea != null) {
						// execute sql
					}
				} catch (Exception e) {
				}
			}
		});

	}
}
