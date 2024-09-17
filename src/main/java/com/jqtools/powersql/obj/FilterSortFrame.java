package com.jqtools.powersql.obj;

import javax.swing.JFrame;

import com.jqtools.powersql.constants.Constants;

public class FilterSortFrame extends JFrame {

	private static final long serialVersionUID = 2086152060767330545L;

	public FilterSortFrame() {
		super();

		this.setTitle(Constants.TITLE_FILTER_SORT);
		this.getContentPane().setLayout(null);

		this.pack();
		setLocationRelativeTo(this);
	}

}
