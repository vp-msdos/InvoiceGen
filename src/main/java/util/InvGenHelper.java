package util;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

public class InvGenHelper {
   public static void setLookAndFeel() {
      try {
         LookAndFeelInfo[] var3;
         int var2 = (var3 = UIManager.getInstalledLookAndFeels()).length;

         for(int var1 = 0; var1 < var2; ++var1) {
            LookAndFeelInfo info = var3[var1];
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (Exception var8) {
         System.out.println("Nimbus Theame is Not available Switching to windows Theame.");

         try {
            UIManager.setLookAndFeel("Windows");
         } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
         } catch (InstantiationException var5) {
            var5.printStackTrace();
         } catch (IllegalAccessException var6) {
            var6.printStackTrace();
         } catch (UnsupportedLookAndFeelException var7) {
            var7.printStackTrace();
         }
      }

   }
}
