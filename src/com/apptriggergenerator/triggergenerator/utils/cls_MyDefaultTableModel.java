package com.apptriggergenerator.triggergenerator.utils;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class cls_MyDefaultTableModel extends DefaultTableModel {
	int[] cols_no_editables;

	public cls_MyDefaultTableModel(Vector data, Vector headers, int[] cols_no_editables) {
		super(data, headers);
		this.cols_no_editables = cols_no_editables;
	}

	public boolean isCellEditable(int row, int column) {
		boolean response = true;
		if (column == 9) {
			response = Boolean.parseBoolean(getValueAt(row, 8).toString());
		} else {
			for (int i = 0; i < cols_no_editables.length; i++) {
				if (column == cols_no_editables[i]) {
					response = false;
				}
			}
		}
		return response;
	}
}
