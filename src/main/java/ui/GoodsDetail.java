package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GoodsDetail extends JPanel {
   private JLabel description;
   private JLabel quantity;
   private JLabel rate;
   private JLabel value;
   public JLabel sNo;
   public JTextField descriptionTxt;
   public JTextField quantityTxt;
   public JTextField rateTxt;
   public JTextField valueTxt;
   public JButton clearbtn;

   public GoodsDetail() {
      this.jbInit();
   }

   public void jbInit() {
      this.setLayout(new GridBagLayout());
      this.description = new JLabel("Desc");
      this.quantity = new JLabel("Quantity");
      this.rate = new JLabel("Rate");
      this.value = new JLabel("Value");
      this.sNo = new JLabel("");
      this.descriptionTxt = new IGTextField(40, "Description");
      this.quantityTxt = new IGTextField(15, "Quantity");
      this.rateTxt = new IGTextField(10, "Rate");
      this.valueTxt = new IGTextField(30, "Value");
      this.clearbtn = new JButton("<html><b color = red>X</b></html>");
      this.clearbtn.setBorder((Border)null);
      this.quantityTxt.setPreferredSize(new Dimension(100, 22));
      this.quantityTxt.setMinimumSize(new Dimension(100, 22));
      this.descriptionTxt.setPreferredSize(new Dimension(190, 22));
      this.descriptionTxt.setMinimumSize(new Dimension(190, 22));
      this.rateTxt.setPreferredSize(new Dimension(80, 22));
      this.rateTxt.setMinimumSize(new Dimension(80, 22));
      this.valueTxt.setPreferredSize(new Dimension(200, 22));
      this.valueTxt.setMinimumSize(new Dimension(200, 22));
      this.clearbtn.setPreferredSize(new Dimension(40, 10));
      this.clearbtn.setPreferredSize(new Dimension(40, 10));
      this.add(this.sNo, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
      this.add(this.description, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
      this.add(this.descriptionTxt, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
      this.add(this.quantity, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 12, 0, 0), 0, 0));
      this.add(this.quantityTxt, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
      this.add(this.rate, new GridBagConstraints(5, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 12, 0, 0), 0, 0));
      this.add(this.rateTxt, new GridBagConstraints(6, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 0), 0, 0));
      this.add(this.value, new GridBagConstraints(7, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 12, 0, 0), 0, 0));
      this.add(this.valueTxt, new GridBagConstraints(8, 0, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(5, 10, 5, 0), 0, 0));
      this.add(this.clearbtn, new GridBagConstraints(9, 0, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(5, 10, 5, 0), 0, 0));
      this.setVisible(true);
      this.repaint();
      this.valueTxt.addFocusListener(new FocusListener() {
         public void focusLost(FocusEvent arg0) {
         }

         public void focusGained(FocusEvent arg0) {
            if (GoodsDetail.this.rateTxt.getText() != null && !GoodsDetail.this.rateTxt.getText().equals("") && GoodsDetail.this.quantityTxt.getText() != null && !GoodsDetail.this.quantityTxt.getText().equals("")) {
               BigDecimal rate = new BigDecimal(GoodsDetail.this.rateTxt.getText());
               BigInteger quantity = new BigInteger(GoodsDetail.this.quantityTxt.getText());
               BigDecimal totalVal = rate.multiply(new BigDecimal(quantity));
               GoodsDetail.this.valueTxt.setText(String.valueOf(totalVal));
            }

         }
      });
      this.clearbtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            GoodsDetail.this.clearDataFromRow();
         }
      });
   }

   private void clearDataFromRow() {
      if (!this.descriptionTxt.getText().equals("") || !this.rateTxt.getText().equals("") || !this.quantityTxt.getText().equals("") || !this.valueTxt.getText().equals("")) {
         int ans = JOptionPane.showConfirmDialog(this, "Do you want to clear row " + this.sNo.getText() + " ?");
         if (ans == 0) {
            this.descriptionTxt.setText("");
            this.rateTxt.setText("");
            this.quantityTxt.setText("");
            this.valueTxt.setText("");
         }
      }

   }
}
