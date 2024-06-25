package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class InvgenButton extends JButton {
   InvgenButton btn = this;

   public InvgenButton(String btnName) {
      super(btnName);
      this.jbInit();
   }

   private void jbInit() {
      this.btn.setBackground(Color.gray);
      this.btn.setForeground(Color.BLACK);
      this.btn.addMouseListener(new MouseListener() {
         public void mouseReleased(MouseEvent e) {
         }

         public void mousePressed(MouseEvent e) {
         }

         public void mouseExited(MouseEvent e) {
            InvgenButton.this.btn.setBackground(Color.gray);
         }

         public void mouseEntered(MouseEvent e) {
            InvgenButton.this.btn.setBackground(new Color(0, 76, 153));
         }

         public void mouseClicked(MouseEvent e) {
         }
      });
   }
}
