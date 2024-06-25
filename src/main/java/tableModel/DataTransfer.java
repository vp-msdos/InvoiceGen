package tableModel;

import connection.ConnectionPool;
import datamodel.AddData;
import datamodel.CommonData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Vector;

public class DataTransfer {
   private final String INVOICE_QUERY = "select INVOICE_NO,DATED,ORDER_NO,ORDER_DATE,TO from INVOICE where DEL_IND = 'N' ";
   private final String GOODS_QUERY = "select ROW_ID,DESCRIPTION,QUANTITY,RATE,VALUE_OF_GOOD from GOODS where INVOICE_NO = ? and ORDER_NO = ?";
   private final String INSERT_INVOICE_DATA = "INSERT into INVOICE VALUES (?,?,?,?,?,?,?,?)";
   private final String INSERT_INVOICE_GOODS_DATA = "INSERT into GOODS (INVOICE_NO,ORDER_NO,DESCRIPTION,QUANTITY,RATE,VALUE_OF_GOOD,UPDATED_DATE,UPDATED_BY,DEL_IND,ROW_ID)VALUES (?,?,?,?,?,?,?,?,?,?)";
   private final String DELETE_ROW_INVOICE = "UPDATE INVOICE SET UPDATED_DATE = ?, UPDATED_BY = ?, DEL_IND = ? where INVOICE_NO = ? and ORDER_NO = ?";
   private final String DELETE_ROW_GOODS_DATA = "UPDATE GOODS SET UPDATED_DATE = ?, UPDATED_BY = ?, DEL_IND = ? where INVOICE_NO = ? and ORDER_NO = ?";
   private final String UPDATE_INVOICE_DATA = "UPDATE INVOICE set DATED = ?, ORDER_DATE = ?, TO = ?, UPDATED_DATE = ?, UPDATED_BY = ? where INVOICE_NO = ? and ORDER_NO = ?";
   private final String UPDATE_INVOICE_GOODS_DATA = "UPDATE GOODS set DESCRIPTION = ?, QUANTITY = ?,RATE = ? , VALUE_OF_GOOD = ?, UPDATED_DATE = ?, UPDATED_BY =? where INVOICE_NO = ? and ORDER_NO = ? and ROW_ID = ?";
   TableModelData tab = new TableModelData();
   int tabCol;
   Connection conn;
   PreparedStatement psm;

   public DataTransfer() {
      this.tabCol = this.tab.getColumnCount();
      this.conn = null;
      this.psm = null;
   }

   public TableModelData getTableModel() {
      System.out.println("Running app on win (" + System.getProperty("os.arch") + ")");

      try {
         this.conn = ConnectionPool.getConnection();
         Statement stm = this.conn.createStatement(1004, 1008);
         ResultSet rs = stm.executeQuery("select INVOICE_NO,DATED,ORDER_NO,ORDER_DATE from INVOICE where DEL_IND = 'N' ");
         int rowCount = rs.last() ? rs.getRow() : 0;
         rs.beforeFirst();
         System.out.println("Total Row Counted in Application :- " + rowCount);
         this.tab.setRowCount(rowCount);
         Vector<Vector<Object>> data = new Vector();

         for(int r = 0; rs.next(); ++r) {
            int c = 0;
            Vector<Object> vector = new Vector();

            for(int columnIndex = 1; columnIndex <= this.tabCol; ++columnIndex) {
               vector.add(c, rs.getObject(columnIndex));
               ++c;
            }

            data.add(r, vector);
         }

         if(rowCount > 0) {
            this.tab.setTableModelData(data);
         }
         return this.tab;
      } catch (ClassNotFoundException var9) {
         var9.printStackTrace();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return null;
   }

   public Vector<Vector<Object>> getGoods(String invoicNo, String orderNo) {
      Vector dataGoods = new Vector();

      try {
         if (this.conn == null) {
            this.conn = ConnectionPool.getConnection();
         }

         this.psm = this.conn.prepareStatement("select ROW_ID,DESCRIPTION,QUANTITY,RATE,VALUE_OF_GOOD from GOODS where INVOICE_NO = ? and ORDER_NO = ?");
         this.psm.setString(1, invoicNo);
         this.psm.setString(2, orderNo);
         ResultSet rs = this.psm.executeQuery();

         for(int r = 0; rs.next(); ++r) {
            int c = 0;
            Vector<Object> vector = new Vector();

            for(int columnIndex = 1; columnIndex <= 5; ++columnIndex) {
               vector.add(c, rs.getObject(columnIndex));
               ++c;
            }

            dataGoods.add(r, vector);
         }
      } catch (ClassNotFoundException var9) {
         var9.printStackTrace();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return dataGoods;
   }

   public int addInvoiceData(AddData addData) throws SQLException {
      boolean var2 = false;

      int success;
      try {
         if (this.conn == null) {
            this.conn = ConnectionPool.getConnection();
         }

         this.psm = null;
         this.psm = this.conn.prepareStatement("INSERT into INVOICE VALUES (?,?,?,?,?,?,?,?)");
         this.psm.setString(1, addData.getInvoiceNo());
         this.psm.setString(2, addData.getDated());
         this.psm.setString(3, addData.getOrderNo());
         this.psm.setString(4, addData.getOrderDate());
         this.psm.setString(5, addData.getTo());
         this.psm.setDate(6, addData.getUpdatedDate());
         this.psm.setString(7, addData.getUpdatedBy());
         this.psm.setString(8, addData.getDelInd());
         success = this.psm.executeUpdate();
         if (success == 1) {
            success = 0;
            this.psm = this.conn.prepareStatement("INSERT into GOODS (INVOICE_NO,ORDER_NO,DESCRIPTION,QUANTITY,RATE,VALUE_OF_GOOD,UPDATED_DATE,UPDATED_BY,DEL_IND,ROW_ID)VALUES (?,?,?,?,?,?,?,?,?,?)");
            Map goods = addData.getGoodsValues();

            for(int i = 0; i < addData.getCompCount(); ++i) {
               Map dataGoods = (Map)goods.get(i);
               if (dataGoods != null) {
                  this.psm.setString(1, addData.getInvoiceNo());
                  this.psm.setString(2, addData.getOrderNo());
                  this.psm.setString(3, dataGoods.get("DESC").toString());
                  this.psm.setString(4, dataGoods.get("QUANTITY").toString());
                  this.psm.setString(5, dataGoods.get("RATE").toString());
                  this.psm.setString(6, dataGoods.get("TOTALVALUE").toString());
                  this.psm.setDate(7, addData.getUpdatedDate());
                  this.psm.setString(8, addData.getUpdatedBy());
                  this.psm.setString(9, addData.getDelInd());
                  this.psm.setInt(10, (Integer)dataGoods.get("COUNTER"));
                  success = this.psm.executeUpdate();
                  if (success == 1) {
                  }
               }
            }
         }
      } catch (SQLException | ClassNotFoundException var6) {
         success = 0;
         this.conn.rollback();
         var6.printStackTrace();
      }

      if (success == 1) {
         this.conn.commit();
      }

      return success;
   }

   public int deleteSelectedRow(CommonData deleteData) throws SQLException {
      boolean success1 = false;

      int success;
      try {
         if (this.conn == null) {
            this.conn = ConnectionPool.getConnection();
         }

         this.psm = null;
         this.psm = this.conn.prepareStatement("UPDATE INVOICE SET UPDATED_DATE = ?, UPDATED_BY = ?, DEL_IND = ? where INVOICE_NO = ? and ORDER_NO = ?");
         this.psm.setDate(1, deleteData.getUpdatedDate());
         this.psm.setString(2, deleteData.getUpdatedBy());
         this.psm.setString(3, "Y");
         this.psm.setString(4, deleteData.getInvoiceNo());
         this.psm.setString(5, deleteData.getOrderNo());
         success = this.psm.executeUpdate();
         if (success == 1) {
            this.psm = null;
            success1 = false;
            this.psm = this.conn.prepareStatement("UPDATE GOODS SET UPDATED_DATE = ?, UPDATED_BY = ?, DEL_IND = ? where INVOICE_NO = ? and ORDER_NO = ?");
            this.psm.setDate(1, deleteData.getUpdatedDate());
            this.psm.setString(2, deleteData.getUpdatedBy());
            this.psm.setString(3, "Y");
            this.psm.setString(4, deleteData.getInvoiceNo());
            this.psm.setString(5, deleteData.getOrderNo());
            success = this.psm.executeUpdate();
         }
      } catch (SQLException | ClassNotFoundException var4) {
         success = 0;
         this.conn.rollback();
         var4.printStackTrace();
      }

      if (success > 0) {
         success = 1;
      }

      if (success == 1) {
         this.conn.commit();
      }

      return success;
   }

   public int modifyInvoiceData(AddData addData) {
      int success = 0;

      try {
         if (this.conn == null) {
            this.conn = ConnectionPool.getConnection();
         }

         this.psm = null;
         this.psm = this.conn.prepareStatement("UPDATE INVOICE set DATED = ?, ORDER_DATE = ?, TO = ?, UPDATED_DATE = ?, UPDATED_BY = ? where INVOICE_NO = ? and ORDER_NO = ?");
         this.psm.setString(1, addData.getDated());
         this.psm.setString(2, addData.getOrderDate());
         this.psm.setString(3, addData.getTo());
         this.psm.setDate(4, addData.getUpdatedDate());
         this.psm.setString(5, addData.getUpdatedBy());
         this.psm.setString(6, addData.getInvoiceNo());
         this.psm.setString(7, addData.getOrderNo());
         success = this.psm.executeUpdate();
         if (success == 1) {
            success = 0;
            this.psm = this.conn.prepareStatement("UPDATE GOODS set DESCRIPTION = ?, QUANTITY = ?,RATE = ? , VALUE_OF_GOOD = ?, UPDATED_DATE = ?, UPDATED_BY =? where INVOICE_NO = ? and ORDER_NO = ? and ROW_ID = ?");
            Map goods = addData.getGoodsValues();

            for(int i = 0; i < goods.size(); ++i) {
               Map dataGoods = (Map)goods.get(i);
               if (dataGoods != null && dataGoods.get("DESC").toString() != null) {
                  this.psm.setString(1, dataGoods.get("DESC").toString());
                  this.psm.setString(2, dataGoods.get("QUANTITY").toString());
                  this.psm.setString(3, dataGoods.get("RATE").toString());
                  this.psm.setString(4, dataGoods.get("TOTALVALUE").toString());
               } else {
                  this.psm.setString(1, "");
                  this.psm.setString(2, "");
                  this.psm.setString(3, "");
                  this.psm.setString(4, "");
               }

               this.psm.setDate(5, addData.getUpdatedDate());
               this.psm.setString(6, addData.getUpdatedBy());
               this.psm.setString(7, addData.getInvoiceNo());
               this.psm.setString(8, addData.getOrderNo());
               this.psm.setInt(9, (Integer)dataGoods.get("COUNTER"));
               success = this.psm.executeUpdate();
               if (success != 1) {
                  break;
               }
            }
         }
      } catch (SQLException | ClassNotFoundException var7) {
         var7.printStackTrace();
      }

      if (success > 0) {
         success = 1;
      }

      if (success == 1) {
         try {
            this.conn.commit();
         } catch (SQLException var6) {
            var6.printStackTrace();
         }
      }

      return success;
   }

   private boolean isvalidValue(Map dataGoods) {
      return dataGoods.get("DESC") != null && !dataGoods.get("DESC").equals("") && dataGoods.get("RATE") != null && !dataGoods.get("RATE").equals("") && dataGoods.get("QUANTITY") != null && !dataGoods.get("QUANTITY").equals("") && dataGoods.get("QUANTITY") != null && !dataGoods.get("QUANTITY").equals("");
   }
}
