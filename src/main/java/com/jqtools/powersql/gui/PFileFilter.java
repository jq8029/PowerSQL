package com.jqtools.powersql.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PFileFilter extends FileFilter {
	private String[] fileExts = null;

	public PFileFilter(String fileExts[]) {
		this.fileExts = fileExts;
	}

	@Override
	public boolean accept(File file) {

		if (fileExts == null || fileExts.length == 0 || file.isDirectory()) {
			return true;
		} else {
			for (String ext : fileExts) {
				if (ext == null)
					continue;

				return (file.getAbsolutePath().toLowerCase().endsWith(ext.toLowerCase()));
			}
		}

		return false;
	}

}
