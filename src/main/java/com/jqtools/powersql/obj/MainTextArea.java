package com.jqtools.powersql.obj;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.jqtools.powersql.constants.Constants;
import com.jqtools.powersql.log.MessageLogger;
import com.jqtools.powersql.utils.ExecuteSQL;

public class MainTextArea extends RSyntaxTextArea {

	private static final long serialVersionUID = 1064609692697845846L;
	private Session session = null;
	private JScrollPane resultScroll = null;
	private UndoManager undoManager = new UndoManager();
	private StatusPanel statusPanel = new StatusPanel();

	public MainTextArea() {

		// register key (Ctrl + Enter) to execute current sql
		getActionMap().put(Constants.KEY_EXEC_CURRENT_SQL, new AbstractAction(Constants.KEY_EXEC_CURRENT_SQL) {
			private static final long serialVersionUID = 4711206524548141227L;

			public void actionPerformed(ActionEvent evt) {
				executeSQL();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK),
				Constants.KEY_EXEC_CURRENT_SQL);

		getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});
	}

	public void executeSQL() {
		try {
			// skip it if there is no session
			if (session == null)
				return;

			// skip it if no text in the textArea
			if (this.getText() != null && this.getText().trim().length() > 0) {
				String sql = this.getSelectedText();

				if (sql == null || sql.length() == 0) {
					int pos = this.getCaretPosition();
					int start = this.getText().lastIndexOf(";", pos);
					int end = this.getText().indexOf(";", pos);
					if (start < 0) {
						start = 0;
					} else {
						start = start + 1;
					}
					if (end < 0)
						end = this.getText().length();

					if (end <= start) {
						return;
					} else {
						sql = this.getText().substring(start, end);
					}
				}

				ExecuteSQL.execute(session, sql, resultScroll);
			}
		} catch (Exception e) {
			MessageLogger.error(e);
		}
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setResultScroll(JScrollPane resultScroll) {
		this.resultScroll = resultScroll;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public StatusPanel getStatusPanel() {
		return statusPanel;
	}

}
