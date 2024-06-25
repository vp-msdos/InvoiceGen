package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import writeExcel.PoiWriteExcelFile;

public class PrintInvoiceDialog extends JDialog {
   static PrintInvoiceDialog printInvoiceDialog;
   JButton btnInv1;
   JButton btnInv2;
   JButton btnInv3;
   JButton btnAll;

   private PrintInvoiceDialog() {
      this.jbInit();
   }

   public static PrintInvoiceDialog getInstance() {
      if (printInvoiceDialog == null) {
         printInvoiceDialog = new PrintInvoiceDialog();
      }

      return printInvoiceDialog;
   }

   public void jbInit() {
      this.btnInv1 = new JButton("INVOICE");
      this.btnInv2 = new JButton("CON");
      this.btnInv3 = new JButton("CRV");
      this.btnAll = new JButton("ALL");
      this.setLayout(new FlowLayout());
      this.add(this.btnInv1);
      this.add(this.btnInv2);
      this.add(this.btnInv3);
      this.add(this.btnAll);
      this.setTitle("Print Invoice");
      this.setSize(400, 100);
      this.setDefaultCloseOperation(2);
      this.setAlwaysOnTop(true);
      this.setVisible(true);
      this.setAutoRequestFocus(true);
      this.btnInv1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            PrintInvoiceDialog.this.generateInvoice();
         }
      });
      this.btnInv2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            PrintInvoiceDialog.this.generateConInvoice();
         }
      });
      this.btnInv3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            PrintInvoiceDialog.this.generateCrvInvoice();
         }
      });
      this.btnAll.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            PrintInvoiceDialog.this.generateAllInvoices();
         }
      });
   }

   public void generateInvoice() {
      PoiWriteExcelFile.writeInvoice(true);
      this.setVisible(false);
   }

   public void generateConInvoice() {
      PoiWriteExcelFile.writeConInvoice(true);
      this.setVisible(false);
   }

   public void generateCrvInvoice() {
      PoiWriteExcelFile.writeCrvInvoice(true);
      this.setVisible(false);
   }

   public void generateAllInvoices() {
      PoiWriteExcelFile.writeAllInvoice();
      this.setVisible(false);
   }
}
