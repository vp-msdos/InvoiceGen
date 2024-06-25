package tableModel;

import java.util.Enumeration;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableColModel implements TableColumnModel {
   public void addColumn(TableColumn aColumn) {
   }

   public void removeColumn(TableColumn column) {
   }

   public void moveColumn(int columnIndex, int newIndex) {
   }

   public void setColumnMargin(int newMargin) {
   }

   public int getColumnCount() {
      return 0;
   }

   public Enumeration<TableColumn> getColumns() {
      return null;
   }

   public int getColumnIndex(Object columnIdentifier) {
      return 0;
   }

   public TableColumn getColumn(int columnIndex) {
      return null;
   }

   public int getColumnMargin() {
      return 0;
   }

   public int getColumnIndexAtX(int xPosition) {
      return 0;
   }

   public int getTotalColumnWidth() {
      return 0;
   }

   public void setColumnSelectionAllowed(boolean flag) {
   }

   public boolean getColumnSelectionAllowed() {
      return false;
   }

   public int[] getSelectedColumns() {
      return null;
   }

   public int getSelectedColumnCount() {
      return 0;
   }

   public void setSelectionModel(ListSelectionModel newModel) {
   }

   public ListSelectionModel getSelectionModel() {
      return null;
   }

   public void addColumnModelListener(TableColumnModelListener x) {
   }

   public void removeColumnModelListener(TableColumnModelListener x) {
   }
}
