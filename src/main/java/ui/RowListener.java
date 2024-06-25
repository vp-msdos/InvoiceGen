package ui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class RowListener implements ListSelectionListener {
   MainFrame readRow;
   JTable table;

   public RowListener(MainFrame rar) {
      this.readRow = rar;
      this.table = this.readRow.table;
   }

   public void valueChanged(ListSelectionEvent e) {
      if (!e.getValueIsAdjusting()) {
         ListSelectionModel model = this.table.getSelectionModel();
         int lead = model.getLeadSelectionIndex();
         this.displayRowValues(lead);
      }

   }

   public void setSelectedRow(int index) {
      this.displayRowValues(index);
   }

   private void displayRowValues(int rowIndex) {
      this.readRow.seedDataModel(rowIndex);
   }
}
