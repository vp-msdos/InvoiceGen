package ui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

public class RefreshUI extends Thread {
   Runnable refreshUi;

   public RefreshUI(Runnable refreshUi) {
      this.refreshUi = refreshUi;
   }

   public void run() {
      try {
         SwingUtilities.invokeAndWait(this.refreshUi);
      } catch (InvocationTargetException var2) {
         var2.printStackTrace();
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

   }
}
