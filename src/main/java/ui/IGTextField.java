package ui;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;

public class IGTextField extends JTextField {
   IGTextField field;
   private Shape shape;
   private String textForPleceHolder;
   Color colour = new Color(0, 76, 153);

   public IGTextField(int size, String textForPleceHolder) {
      super(size);
      this.textForPleceHolder = textForPleceHolder;
      this.setOpaque(false);
      this.field = this;
      this.jbInit();
   }

   private void jbInit() {
      PromptSupport.setPrompt(this.textForPleceHolder, this);
      PromptSupport.setFocusBehavior(FocusBehavior.HIDE_PROMPT, this);
      PromptSupport.setFontStyle(1, this);
      this.field.addMouseListener(new MouseListener() {
         public void mouseReleased(MouseEvent e) {
         }

         public void mousePressed(MouseEvent e) {
         }

         public void mouseExited(MouseEvent e) {
            e.getComponent().setBackground(Color.white);
         }

         public void mouseEntered(MouseEvent e) {
            if (e.getComponent().isEnabled()) {
               e.getComponent().setBackground(Color.cyan);
            }

         }

         public void mouseClicked(MouseEvent e) {
         }
      });
   }
}
