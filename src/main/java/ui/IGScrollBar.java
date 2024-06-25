package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JScrollBar;

public class IGScrollBar extends JScrollBar {
   IGScrollBar scroll = this;

   public IGScrollBar(int bar) {
      super(bar);
      this.jbInit();
   }

   private void jbInit() {
      this.scroll.setBackground(Color.gray);
      this.scroll.addMouseListener(new MouseListener() {
         public void mouseReleased(MouseEvent e) {
         }

         public void mousePressed(MouseEvent e) {
         }

         public void mouseExited(MouseEvent e) {
            IGScrollBar.this.scroll.setBackground(Color.gray);
         }

         public void mouseEntered(MouseEvent e) {
            IGScrollBar.this.scroll.setBackground(new Color(0, 76, 153));
         }

         public void mouseClicked(MouseEvent e) {
         }
      });
   }
}
