package com.jqtools.powersql.log;

import javax.swing.JOptionPane;

import com.jqtools.powersql.constants.Constants;

public class NoticeMessage {
	public static int showConfirm(String message) {
		return JOptionPane.showConfirmDialog(null, message, Constants.MESSAGE_CONFIRM, JOptionPane.YES_NO_OPTION);
	}

}
