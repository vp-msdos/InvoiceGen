package upload;

import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class UploadData {
   public volatile int process = 0;
   public JProgressBar pBar;
   public JDialog pBarDialog;
   Runnable upload = new Runnable() {
      public void run() {
         UploadData.this.uploadInvoiceData();
      }
   };
   Runnable progress = new Runnable() {
      public void run() {
         if (UploadData.this.process == 100) {
            for(int i = 1; i <= 100; ++i) {
               UploadData.this.pBar.setValue(UploadData.this.process);

               try {
                  Thread.sleep(400L);
               } catch (InterruptedException var3) {
                  var3.printStackTrace();
               }
            }
         }

      }
   };

   public void showBrowseWindow() {
      this.pBarDialog = new JDialog();
      this.pBar = new JProgressBar(0, 100);
      this.pBar.setStringPainted(true);
      this.pBarDialog.add(this.pBar);
      this.pBarDialog.setSize(300, 60);
      this.pBarDialog.setLocationByPlatform(true);
      this.pBarDialog.setVisible(true);
      JFileChooser fileCh = new JFileChooser("");
      fileCh.showOpenDialog(new JFrame());
      File selFile = fileCh.getSelectedFile();
      Thread uploadTh = new Thread(this.progress);
      Thread progressTh = new Thread(this.upload);
      uploadTh.start();
      progressTh.start();

      try {
         uploadTh.join();
      } catch (InterruptedException var6) {
         var6.printStackTrace();
      }

   }

   public void uploadInvoiceData() {
      for(int i = 0; i <= 100; this.process = i++) {
         System.out.println(i);
      }

   }
}
