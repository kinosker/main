/**
 * This class enables the wrap-text function of the table.
 */
//@author A0112675H
package userInterface;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


@SuppressWarnings("serial")
public class LineWrapCellRenderer extends JTextArea implements
		TableCellRenderer {

	InteractiveTableModel tableModel;

	public LineWrapCellRenderer(InteractiveTableModel tableModel) {
		this.tableModel = tableModel;
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		this.setOpaque(true);

		// sets the column colour 
		DefaultWeDoTableColor.setDefaultBackGroundColour(this, row, column,
				table.getSelectedRow(), tableModel.getValueAt(row,
						InteractiveTableModel.INDEX_PRIORITY), tableModel
						.getValueAt(row, InteractiveTableModel.INDEX_CHECK));

		setText((String) value);

		setSize(table.getColumnModel().getColumn(column).getWidth(),
				getPreferredSize().height);

		if (table.getRowHeight(row) != getPreferredSize().height) {
			table.setRowHeight(row, getPreferredSize().height);
		}

		return this;
	}
}
