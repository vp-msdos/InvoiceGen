package writeExcel;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiWriteExcelFile {
   static int row;
   static TableModel model;
   static String invno;
   static String dated;
   static String orderno;
   static String orderDate;
   static String to;
   static Vector<Vector<Object>> dataGoods;
   static final String path = "E:\\InvoiceGen\\Inv.xls";

   public static void writeInvoice(boolean isSeparate) {
      invno = model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "";
      dated = model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "";
      orderno = model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "";
      orderDate = model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "";
      to = model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "";

      try {
         FileInputStream fileIn = new FileInputStream("E:\\InvoiceGen\\Inv.xls");
         HSSFWorkbook workbook = null;
         workbook = new HSSFWorkbook(fileIn);
         HSSFSheet worksheet = workbook.getSheet("Invoice");
         HSSFRow row1 = worksheet.getRow(3);
         HSSFCell cellA1 = row1.getCell(4);
         cellA1.setCellValue(invno);
         HSSFCell cellA2 = row1.getCell(6);
         cellA2.setCellValue(dated);
         HSSFRow row2 = worksheet.getRow(4);
         HSSFCell cellA3 = row2.getCell(3);
         cellA3.setCellValue(orderno);
         HSSFRow row3 = worksheet.getRow(5);
         HSSFCell cellA4 = row3.getCell(3);
         cellA4.setCellValue(orderDate);
         HSSFRow row4 = worksheet.getRow(6);
         HSSFCell cellA5 = row4.getCell(1);
         cellA5.setCellValue(to);

         int rowId;
         int i;
         for(rowId = 11; rowId <= 30; ++rowId) {
            HSSFRow rowclear = worksheet.getRow(rowId);

            for(i = 0; i <= 6; ++i) {
               HSSFCell cellclear = rowclear.getCell(i);
               cellclear.setCellValue("");
            }
         }

         rowId = 11;
         Double sum = 0.0D;

         HSSFCell cell0;
         for(i = 0; i < dataGoods.size(); ++i) {
            Vector vec = (Vector)dataGoods.get(i);
            HSSFRow row11 = worksheet.getRow(rowId);
            int val = 0;

            for(int j = 0; j <= 6; ++j) {
               if (j != 2 && j != 3) {
                  cell0 = row11.getCell(j);
                  if (j == 6) {
                     sum = sum + Double.valueOf(vec.get(val).toString());
                  }

                  cell0.setCellValue(vec.get(val).toString());
                  ++val;
               }
            }

            ++rowId;
         }

         Double vat = sum * 4.0D / 100.0D;
         Double sat = sum * 1.0D / 100.0D;
         Double total = sum + vat + sat;
         Integer roundOff = (int)Math.round(total);
         HSSFRow row38 = worksheet.getRow(38);
         cell0 = row38.getCell(6);
         cell0.setCellValue(sum);
         HSSFRow row39 = worksheet.getRow(39);
         HSSFCell cell39 = row39.getCell(6);
         cell39.setCellValue(vat);
         HSSFRow row40 = worksheet.getRow(40);
         HSSFCell cell40 = row40.getCell(6);
         cell40.setCellValue(sat);
         HSSFRow row41 = worksheet.getRow(41);
         HSSFCell cell41 = row41.getCell(6);
         cell41.setCellValue(total);
         HSSFRow row42 = worksheet.getRow(42);
         HSSFCell cell42 = row42.getCell(6);
         cell42.setCellValue((double)roundOff);
         StringBuilder strInWords = new StringBuilder("");
         pw(roundOff / 1000000000, " Hundred", strInWords);
         pw(roundOff / 10000000 % 100, " Crore", strInWords);
         pw(roundOff / 100000 % 100, " Lakh", strInWords);
         pw(roundOff / 1000 % 100, " Thousand", strInWords);
         pw(roundOff / 100 % 10, " Hundred", strInWords);
         pw(roundOff % 100, " ", strInWords);
         HSSFRow row44 = worksheet.getRow(44);
         HSSFCell cell44 = row44.getCell(2);
         cell44.setCellValue("Rs." + strInWords.toString() + " Only");
         FileOutputStream fileOut = new FileOutputStream("E:\\InvoiceGen\\Inv.xls");
         workbook.write(fileOut);
         fileOut.flush();
         fileOut.close();
         if (isSeparate) {
            Desktop.getDesktop().open(new File("E:\\InvoiceGen\\Inv.xls"));
         }
      } catch (FileNotFoundException var33) {
         var33.printStackTrace();
      } catch (IOException var34) {
         var34.printStackTrace();
      }

   }

   public static void writeConInvoice(boolean isSeparate) {
      invno = model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "";
      dated = model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "";
      orderno = model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "";
      orderDate = model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "";
      to = model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "";

      try {
         FileInputStream fileIn = new FileInputStream("E:\\InvoiceGen\\Inv.xls");
         HSSFWorkbook workbook = null;
         workbook = new HSSFWorkbook(fileIn);
         HSSFSheet worksheet = workbook.getSheet("Con");
         HSSFRow row1 = worksheet.getRow(14);
         HSSFCell cellA1 = row1.getCell(0);
         cellA1.setCellValue("Invoice No");
         HSSFRow row2 = worksheet.getRow(15);
         HSSFCell cellA2 = row2.getCell(0);
         cellA2.setCellValue(invno);
         HSSFRow row3 = worksheet.getRow(17);
         HSSFCell cellA3 = row3.getCell(0);
         cellA3.setCellValue("Dated");
         HSSFRow row4 = worksheet.getRow(18);
         HSSFCell cellA4 = row4.getCell(0);
         cellA4.setCellValue(dated);
         HSSFRow row5 = worksheet.getRow(40);
         HSSFCell cellA5 = row5.getCell(1);
         cellA5.setCellValue(dated);
         HSSFRow row6 = worksheet.getRow(56);
         HSSFCell cellA6 = row6.getCell(1);
         cellA6.setCellValue(dated);
         HSSFCell cellA7 = row6.getCell(6);
         cellA7.setCellValue(dated);

         int rowId;
         int j;
         HSSFCell cellclear;
         for(rowId = 11; rowId <= 21; ++rowId) {
            HSSFRow rowclear = worksheet.getRow(rowId);

            for(j = 0; j <= 6; ++j) {
               if (j != 0 && j != 1) {
                  cellclear = rowclear.getCell(j);
                  cellclear.setCellValue("");
               }
            }
         }

         rowId = 11;
         Double sum = 0.0D;

         for(j = 0; j < dataGoods.size(); ++j) {
            Vector vec = (Vector)dataGoods.get(j);
            HSSFRow row11 = worksheet.getRow(rowId);
            int val = 1;

            for(int k = 2; k <= 5; ++k) {
               HSSFCell cell0 = row11.getCell(k);
               if (k == 5) {
                  sum = sum + Double.valueOf(vec.get(val).toString());
               }

               cell0.setCellValue(vec.get(val).toString());
               ++val;
            }

            ++rowId;
         }

         HSSFRow row7 = worksheet.getRow(22);
         cellclear = row7.getCell(5);
         cellclear.setCellValue(sum);
         Integer roundOff = (int)Math.round(sum);
         HSSFRow row8 = worksheet.getRow(23);
         HSSFCell cellA9 = row8.getCell(5);
         cellA9.setCellValue((double)roundOff);
         StringBuilder strInWords = new StringBuilder("");
         pw(roundOff / 1000000000, " Hundred", strInWords);
         pw(roundOff / 10000000 % 100, " Crore", strInWords);
         pw(roundOff / 100000 % 100, " Lakh", strInWords);
         pw(roundOff / 1000 % 100, " Thousand", strInWords);
         pw(roundOff / 100 % 10, " Hundred", strInWords);
         pw(roundOff % 100, " ", strInWords);
         HSSFRow row9 = worksheet.getRow(26);
         HSSFCell cellA10 = row9.getCell(5);
         cellA10.setCellValue("Rs." + strInWords.toString() + " Only");
         HSSFRow row10 = worksheet.getRow(3);
         HSSFCell cellA12 = row10.getCell(2);
         cellA12.setCellValue(orderno);
         HSSFCell cellA11 = row10.getCell(5);
         cellA11.setCellValue(dated);
         FileOutputStream fileOut = new FileOutputStream("E:\\InvoiceGen\\Inv.xls");
         workbook.write(fileOut);
         fileOut.flush();
         fileOut.close();
         if (isSeparate) {
            Desktop.getDesktop().open(new File("E:\\InvoiceGen\\Inv.xls"));
         }
      } catch (FileNotFoundException var31) {
         var31.printStackTrace();
      } catch (IOException var32) {
         var32.printStackTrace();
      }

   }

   public static void writeCrvInvoice(boolean isSeparate) {
      invno = model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "";
      dated = model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "";
      orderno = model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "";
      orderDate = model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "";
      to = model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "";

      try {
         FileInputStream fileIn = new FileInputStream("E:\\InvoiceGen\\Inv.xls");
         HSSFWorkbook workbook = null;
         workbook = new HSSFWorkbook(fileIn);
         HSSFSheet worksheet = workbook.getSheet("Crv");
         HSSFRow row1 = worksheet.getRow(15);
         HSSFCell cellA2 = row1.getCell(2);
         cellA2.setCellValue(orderno);
         HSSFCell cellA1 = row1.getCell(5);
         cellA1.setCellValue(dated);

         int rowId;
         int j;
         HSSFCell cellclear;
         for(rowId = 19; rowId <= 28; ++rowId) {
            HSSFRow rowclear = worksheet.getRow(rowId);

            for(j = 0; j <= 6; ++j) {
               if (j != 3) {
                  cellclear = rowclear.getCell(j);
                  cellclear.setCellValue("");
               }
            }
         }

         rowId = 19;
         Double sum = 0.0D;

         for(j = 0; j < dataGoods.size(); ++j) {
            Vector vec = (Vector)dataGoods.get(j);
            HSSFRow row11 = worksheet.getRow(rowId);
            int val = 1;

            for(int k = 2; k <= 6; ++k) {
               HSSFCell cell0 = row11.getCell(k);
               if (k == 6) {
                  sum = sum + Double.valueOf(vec.get(val).toString());
               }

               if (j != 3) {
                  cell0.setCellValue(vec.get(val).toString());
                  ++val;
               }
            }

            ++rowId;
         }

         HSSFRow row7 = worksheet.getRow(29);
         cellclear = row7.getCell(6);
         cellclear.setCellValue(sum);
         Integer roundOff = (int)Math.round(sum);
         HSSFRow row8 = worksheet.getRow(30);
         HSSFCell cellA9 = row8.getCell(6);
         cellA9.setCellValue((double)roundOff);
         StringBuilder strInWords = new StringBuilder("");
         pw(roundOff / 1000000000, " Hundred", strInWords);
         pw(roundOff / 10000000 % 100, " Crore", strInWords);
         pw(roundOff / 100000 % 100, " Lakh", strInWords);
         pw(roundOff / 1000 % 100, " Thousand", strInWords);
         pw(roundOff / 100 % 10, " Hundred", strInWords);
         pw(roundOff % 100, " ", strInWords);
         HSSFRow row9 = worksheet.getRow(32);
         HSSFCell cellA10 = row9.getCell(2);
         cellA10.setCellValue(strInWords.toString() + " Only");
         FileOutputStream fileOut = new FileOutputStream("E:\\InvoiceGen\\Inv.xls");
         workbook.write(fileOut);
         fileOut.flush();
         fileOut.close();
         if (isSeparate) {
            Desktop.getDesktop().open(new File("E:\\InvoiceGen\\Inv.xls"));
         }
      } catch (FileNotFoundException var18) {
         var18.printStackTrace();
      } catch (IOException var19) {
         var19.printStackTrace();
      }

   }

   public static void writeAllInvoice() {
      writeInvoice(false);
      writeConInvoice(false);
      writeCrvInvoice(false);

      try {
         Desktop.getDesktop().open(new File("E:\\InvoiceGen\\Inv.xls"));
      } catch (IOException var1) {
         var1.printStackTrace();
      }

   }

   public static void getTableData(int row, TableModel model, Vector<Vector<Object>> dataGoods) {
      PoiWriteExcelFile.row = row;
      PoiWriteExcelFile.model = model;
      PoiWriteExcelFile.dataGoods = dataGoods;
   }

   public static void pw(Integer n, String ch, StringBuilder strInWords) {
      String[] one = new String[]{" ", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", "Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};
      String[] ten = new String[]{" ", " ", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};
      if (n > 19) {
         strInWords.append(ten[n / 10] + " " + one[n % 10]);
      } else {
         strInWords.append(one[n]);
      }

      if (n > 0) {
         strInWords.append(ch);
      }

   }
}
