package ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class HighlightRenderer extends DefaultTableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (row == table.getSelectedRow()) {
         this.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.BLACK));
      }

      return this;
   }
}
