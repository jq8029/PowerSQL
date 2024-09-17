package com.jqtools.powersql.obj;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jqtools.powersql.constants.Constants;

public class FilterSortFrame extends JFrame {

	private static final long serialVersionUID = 2086152060767330545L;

	public FilterSortFrame() {
		super();

		this.setTitle(Constants.TITLE_FILTER_SORT);
		this.getContentPane().setLayout(null);

		int height = 16;
		int x1 = 15;
		int y1 = 20;

		// add filter panel
		JPanel filterPanel = new JPanel(null);

		// add sort panel
		JPanel sortPanel = new JPanel(null);

		this.getContentPane().add(filterPanel);
		this.getContentPane().add(sortPanel);

		this.pack();
		setLocationRelativeTo(this);
	}

}
