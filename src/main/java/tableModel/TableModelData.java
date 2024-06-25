package tableModel;

import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TableModelData implements TableModel {
   int rowCount = 0;
   Vector data = new Vector();

   public void setTableModelData(Vector data) {
      this.data = data;
   }

   public int getRowCount() {
      return this.rowCount;
   }

   public void setRowCount(int rowCount) {
      this.rowCount = rowCount;
   }

   public int getColumnCount() {
      return 5;
   }

   public String getColumnName(int columnIndex) {
      switch(columnIndex) {
      case 0:
         return "<html><b>Invoice No</b></html>";
      case 1:
         return "<html><b>Dated</b></html>";
      case 2:
         return "<html><b>Order No</b></html>";
      case 3:
         return "<html><b>Order Date</b></html>";
      case 4:
         return "<html><b>To</b></html>";
      default:
         return null;
      }
   }

   public Class<?> getColumnClass(int columnIndex) {
      return (new String()).getClass();
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
   }

   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
   }

   public void addTableModelListener(TableModelListener l) {
   }

   public void removeTableModelListener(TableModelListener l) {
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      Vector row = (Vector)this.data.get(rowIndex);
      return row.get(columnIndex);
   }
}
