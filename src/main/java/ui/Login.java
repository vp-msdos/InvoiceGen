package ui;

import data.UserInformation;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;
import jaxbutil.JaxbUtil;
import util.Cache;
import util.InvGenHelper;
import util.LoadCache;

public class Login {
   JFrame loginFrame;
   private IGTextField username;
   private IGTextField pwd;
   private InvgenButton login;
   private boolean isLoginDisabled;
   JProgressBar progressBar;
   ThreadMsg msg;
   volatile String loginBtnText = "";
   String userId = "";
   String path = "";
   JaxbUtil jaxb;

   public Login() {
      this.jbInit();
   }

   private void jbInit() {
      LoadCache loadCache = new LoadCache();

      try {
         loadCache.start();
         loadCache.join();
      } catch (InterruptedException var5) {
         var5.printStackTrace();
      }

      this.userId = System.getProperty("user.name");
      this.jaxb = JaxbUtil.getInstance();
      this.loginFrame = new JFrame();
      this.msg = new ThreadMsg();
      InvGenHelper.setLookAndFeel();
      //int i = false;
      this.loginFrame.setTitle("InvoiceGen Login");
      this.loginFrame.setSize(300, 150);
      this.loginFrame.setLayout(new FlowLayout());
      this.loginFrame.setLocationRelativeTo((Component)null);
      this.loginFrame.setDefaultCloseOperation(2);
      this.loginFrame.setVisible(true);
      this.loginFrame.setAlwaysOnTop(true);
      this.loginFrame.setResizable(false);
      Thread progressBarThread = new Thread(new Runnable() {
         public void run() {
            synchronized(Login.this.msg) {
               //int i = false;
               Login.this.progressBar = new JProgressBar(0, 100);
               Login.this.progressBar.setValue(0);
               Login.this.progressBar.setStringPainted(true);
               Login.this.progressBar.setVisible(true);
               Login.this.loginFrame.revalidate();
               Login.this.loginFrame.add(Login.this.progressBar);

               int ix;
               for(ix = 0; ix <= 100; ++ix) {
                  Login.this.progressBar.setVisible(true);
                  Login.this.progressBar.setValue(ix);
                  Login.this.loginFrame.repaint();
                  Login.this.loginFrame.revalidate();

                  try {
                     Thread.sleep(60L);
                  } catch (InterruptedException var8) {
                     var8.printStackTrace();
                  }
               }

               File drive1 = new File("D:\\");
               File drive2 = new File("E:\\");
               File drive3 = new File("C:\\");
               String driveName = "";
               if (drive1.exists()) {
                  driveName = "D:\\";
               } else if (drive2.exists()) {
                  driveName = "E:\\";
               }else if(drive3.exists()) {
                  driveName = "C:\\";
               }
               else {
                  JOptionPane.showMessageDialog(Login.this.loginFrame, "Drive D: or E: is not available !", "Error", 0);
               }

               File directory = new File(driveName + "InvoiceGenConfig");
               File file = new File(driveName + "InvoiceGenConfig\\UserInfo.xml");
               Login.this.path = driveName + "InvoiceGenConfig\\";
               if (!directory.exists()) {
                  this.createDirectory(file);
               } else {
                  Login.this.loginBtnText = Cache.getValueFromCache("LOGIN");
               }

               Login.this.msg.setMsg(ix + "Sucess");
               Login.this.msg.notifyAll();
            }
         }

         private void createDirectory(File file) {
            boolean isDirectoryCreated = file.getParentFile().mkdir();
            if (isDirectoryCreated) {
               System.out.println("Directory Created");
               Login.this.loginBtnText = "SignUp";
               Login.this.loginFrame.revalidate();
            }

         }
      });
      Thread loginFuncThread = new Thread(new Runnable() {
         public void run() {
            synchronized(Login.this.msg) {
               try {
                  Login.this.msg.wait();
                  System.out.println(Login.this.msg.getMsg());
               } catch (InterruptedException var3) {
                  var3.printStackTrace();
               }

               Login.this.progressBar.setVisible(false);
               Login.this.username = new IGTextField(20, Login.this.userId);
               Login.this.pwd = new IGTextField(20, "Password");
               Login.this.login = new InvgenButton(Login.this.loginBtnText);
               Login.this.username.setFocusable(false);
               Login.this.pwd.setFocusable(true);
               Login.this.username.setVisible(true);
               Login.this.pwd.setVisible(true);
               Login.this.login.setVisible(true);
               Login.this.username.setEditable(false);
               Login.this.pwd.setEditable(true);
               Login.this.loginFrame.add(Login.this.username);
               Login.this.loginFrame.add(Login.this.pwd);
               Login.this.loginFrame.add(Login.this.login);
               Login.this.loginFrame.setResizable(false);
               Login.this.loginFrame.repaint();
               Login.this.loginFrame.revalidate();
               Login.this.login.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     this.handleEvent(e);
                  }

                  private void handleEvent(ActionEvent e) {
                     System.out.println("print");
                     if (((InvgenButton)e.getSource()).getText().equals("SignUp")) {
                        if (Login.this.pwd.getText().trim().equals("")) {
                           JOptionPane.showMessageDialog(Login.this.loginFrame, "Password in blank", "Error", 0);
                        } else {
                           String password = Login.this.pwd.getText().trim();
                           UserInformation userInfox = new UserInformation();
                           userInfox.setUserId(Login.this.userId);
                           userInfox.setPassword(password);

                           try {
                              Login.this.jaxb.generateUserInformationXml(userInfox, Login.this.path);
                              SwingUtilities.invokeLater(new Runnable() {
                                 public void run() {
                                    new MainFrame();
                                 }
                              });
                              Login.this.dialogInvisible();
                           } catch (JAXBException var6) {
                              var6.printStackTrace();
                           }
                        }
                     } else if (((InvgenButton)e.getSource()).getText().equals("Login") && Login.this.pwd.getText() != null && !Login.this.pwd.getText().trim().equals("")) {
                        try {
                           UserInformation userInfo = Login.this.jaxb.getUserInformation(Login.this.path);
                           if (userInfo == null) {
                              System.out.println("u");
                           }

                           if (userInfo.getPassword().equals(Login.this.pwd.getText().trim())) {
                              SwingUtilities.invokeLater(new Runnable() {
                                 public void run() {
                                    new MainFrame();
                                    Login.this.dialogInvisible();
                                    Login.this.loginFrame.dispose();
                                 }
                              });
                           } else {
                              JOptionPane.showMessageDialog(Login.this.loginFrame, "Password is Invalid !", "Error", 0);
                           }
                        } catch (JAXBException var5) {
                           var5.printStackTrace();
                        }
                     }

                  }
               });
            }
         }
      });
      //loginFuncThread.start();
      //progressBarThread.start();
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new MainFrame();
            Login.this.dialogInvisible();

         }
      });

   }

   public void dialogInvisible() {
      this.loginFrame.setVisible(false);
      this.loginFrame.dispose();
   }

   public boolean isLoginDisabled() {
      return true;
   }
}
