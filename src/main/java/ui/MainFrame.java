package ui;

import datamodel.AddData;
import datamodel.CommonData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import tableModel.DataTransfer;
import tableModel.TableModelData;
import upload.UploadData;
import util.Cache;
import util.InvGenHelper;
import util.LoadCache;
import writeExcel.PoiWriteExcelFile;

public class MainFrame extends JFrame {
   GoodsDetail[] gd;
   public JTable table;
   private JMenuBar viewMenu;
   private JScrollPane pane;
   private JScrollPane paneForRate;
   private JPanel panel;
   private JPanel panelInner;
   private JPanel panelBtn;
   private JPanel panelRate;
   private JPanel panelRateCont;
   private JLabel invNumber;
   private JLabel dated;
   private JLabel orderNumber;
   private JLabel orderDate;
   private JLabel to;
   private IGTextField invNumberTxt;
   private IGTextField datedTxt;
   private IGTextField orderNumberTxt;
   private IGTextField orderDateTxt;
   private JTextArea toTxt;
   private IGTextField InvoiceNo;
   private InvgenButton add;
   private InvgenButton modify;
   private InvgenButton delete;
   private InvgenButton print;
   private InvgenButton ok;
   private InvgenButton cancel;
   private InvgenButton addGoods;
   private InvgenButton search;
   public static String DISPLAY_MODE = "DISPLAY";
   public static String ADD_MODE = "ADD";
   public static String MODIFY_MODE = "MODIFY";
   public static String DELETE_MODE = "DELETE";
   public static String SELECTION_MODE = "SELECT";
   public static String mode;
   public static String updatedBy;
   public final int mingoodCount = 4;
   public final int maxGoodCount = 50;
   private int count = 5;
   private static int compCounter = 4;
   private static int w = 0;
   DataTransfer dt = null;
   public TableModelData tableMod;
   private int rowSelected;
   private PrintInvoiceDialog printInv;
   public JPopupMenu popUpmenu;
   public JMenuItem searchItem;
   public JDialog searchDialog;
   public JMenuItem upload;
   final Runnable refreshUi = new Runnable() {
      public void run() {
         MainFrame.this.table.removeAll();
         MainFrame.this.tableMod = MainFrame.this.dt.getTableModel();
         MainFrame.this.table.setModel(MainFrame.this.tableMod);
         MainFrame.this.table.repaint();
         MainFrame.this.table.revalidate();
      }
   };

   public MainFrame() {
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
      InvGenHelper.setLookAndFeel();
      this.launchInvoiceGenUi();
   }

   private void launchInvoiceGenUi() {
      updatedBy = System.getProperty("user.name");
      //this.renewMessage();
      System.out.println("InvoiceGen initialising.. \nDeveloped by: Vishwas(Sanorous Technologies Pvt. Ltd.)");
      this.searchItem = new JMenuItem("Search by Invoice No");
      this.search = new InvgenButton("Search");
      this.upload = new JMenuItem("Upload");
      this.table = new JTable();
      this.dt = new DataTransfer();
      this.tableMod = new TableModelData();
      this.tableMod = this.dt.getTableModel();
      if(this.tableMod != null) {
         this.table.setModel(this.tableMod);
      }
      ListSelectionModel selectionModel = this.table.getSelectionModel();
      selectionModel.setSelectionMode(0);
      selectionModel.addListSelectionListener(new RowListener(this));
      mode = DISPLAY_MODE;
      this.pane = new JScrollPane();
      this.pane.setVerticalScrollBar(new IGScrollBar(1));
      this.paneForRate = new JScrollPane();
      this.paneForRate.setVerticalScrollBarPolicy(20);
      this.paneForRate.setHorizontalScrollBarPolicy(31);
      this.paneForRate.setVerticalScrollBar(new IGScrollBar(1));
      this.viewMenu = new JMenuBar();
      JPanel dummyPnl = new JPanel();
      JMenuItem mn1 = new JMenuItem("File");
      JMenuItem mn2 = new JMenuItem("Config");
      JMenuItem mn3 = new JMenuItem("Help ?");
      JMenuItem mn4 = new JMenuItem("Credits");
      mn1.setMinimumSize(new Dimension(70, 20));
      mn2.setMinimumSize(new Dimension(70, 20));
      mn3.setMinimumSize(new Dimension(70, 20));
      this.viewMenu.setLayout(new GridBagLayout());
      this.viewMenu.add(mn1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.viewMenu.add(mn2, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.viewMenu.add(mn3, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.viewMenu.add(mn4, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.viewMenu.add(dummyPnl, new GridBagConstraints(3, 0, 2, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.panel = new JPanel(new GridBagLayout());
      this.panelInner = new JPanel(new GridBagLayout());
      this.panelBtn = new JPanel(new GridBagLayout());
      this.panelRate = new JPanel(new GridBagLayout());
      this.panelRateCont = new JPanel(new GridBagLayout());
      this.invNumber = new JLabel("Invoice No");
      this.dated = new JLabel("Dated");
      this.orderNumber = new JLabel("Order No");
      this.orderDate = new JLabel("Order Date");
      this.to = new JLabel("To");
      this.invNumberTxt = new IGTextField(50, "Invoice No");
      this.datedTxt = new IGTextField(50, "Dated");
      this.orderNumberTxt = new IGTextField(100, "Order No");
      this.orderDateTxt = new IGTextField(50, "Order Date");
      this.toTxt = new JTextArea(20, 50);
      this.invNumberTxt.setMinimumSize(new Dimension(100, 25));
      this.datedTxt.setMinimumSize(new Dimension(150, 25));
      this.orderNumberTxt.setMinimumSize(new Dimension(200, 25));
      this.orderDateTxt.setMinimumSize(new Dimension(100, 25));
      this.toTxt.setMinimumSize(new Dimension(200, 50));
      this.toTxt.setBorder(this.datedTxt.getBorder());
      this.add = new InvgenButton(Cache.getValueFromCache("ADD"));
      this.modify = new InvgenButton(Cache.getValueFromCache("MODIFY"));
      this.delete = new InvgenButton(Cache.getValueFromCache("DELETE"));
      this.print = new InvgenButton(Cache.getValueFromCache("PRINT"));
      this.ok = new InvgenButton("Ok");
      this.cancel = new InvgenButton("Cancel");
      this.addGoods = new InvgenButton("Add Goods");
      List<JButton> listButton = new ArrayList();
      listButton.add(this.add);
      listButton.add(this.modify);
      listButton.add(this.delete);
      listButton.add(this.print);
      listButton.add(this.ok);
      listButton.add(this.cancel);
      listButton.add(this.addGoods);
      this.registerAllButtonForKeyStrok(listButton);
      this.ok.setMinimumSize(new Dimension(70, 20));
      this.add.setMinimumSize(new Dimension(80, 25));
      this.modify.setMinimumSize(new Dimension(80, 25));
      this.delete.setMinimumSize(new Dimension(80, 25));
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      int wi = (int)dim.getWidth();
      int hi = (int)(dim.getHeight() / 4.0D);
      this.table.setPreferredScrollableViewportSize(new Dimension(wi, hi));
      this.pane.setViewportView(this.table);
      this.table.setVisible(true);
      this.setTitle("Invoice Gen");
      this.setPreferredSize(new Dimension((int)dim.getWidth(), (int)(dim.getHeight() - 40.0D)));
      this.setMaximumSize(new Dimension((int)dim.getWidth(), (int)(dim.getHeight() - 40.0D)));
      this.setMinimumSize(new Dimension((int)(dim.getWidth() - 200.0D), (int)(dim.getHeight() - 60.0D)));
      this.setVisible(true);
      this.setLayout(new GridBagLayout());
      dummyPnl.setMinimumSize(new Dimension(new Dimension((int)(dim.getWidth() - 230.0D), 20)));
      this.viewMenu.setMinimumSize(new Dimension(new Dimension((int)(dim.getWidth() - 10.0D), 20)));
      this.viewMenu.setMaximumSize(new Dimension(new Dimension((int)(dim.getWidth() - 20.0D), 25)));
      this.viewMenu.setPreferredSize(new Dimension(new Dimension((int)(dim.getWidth() - 20.0D), 25)));
      this.pane.setMinimumSize(new Dimension(new Dimension((int)(dim.getWidth() - 20.0D), 197)));
      this.pane.setMaximumSize(new Dimension(new Dimension((int)(dim.getWidth() - 10.0D), 207)));
      this.pane.setPreferredSize(new Dimension(new Dimension((int)(dim.getWidth() - 10.0D), 207)));
      this.panel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3, true), "Data Section"));
      w = (int)(dim.getWidth() / 1.5D);
      int h = (int)(dim.getHeight() / 5.0D);
      this.gd = new GoodsDetail[50];
      this.paneForRate.setPreferredSize(new Dimension(w, h));
      this.paneForRate.setMinimumSize(new Dimension(w, h));

      for(int i = 0; i < 4; ++i) {
         int r = i + 1;
         this.gd[i] = new GoodsDetail();
         this.gd[i].setMaximumSize(new Dimension(w - 50, 29));
         this.gd[i].setPreferredSize(new Dimension(w - 50, 29));
         this.gd[i].setMinimumSize(new Dimension(w - 50, 29));
         this.gd[i].sNo.setText(String.valueOf(r));
         this.gd[i].repaint();
         this.panelRate.add(this.gd[i], new GridBagConstraints(0, i, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
         this.gd[i].revalidate();
      }

      this.panelRate.setBorder(new LineBorder(Color.black));
      this.panelRate.repaint();
      this.paneForRate.setViewportView(this.panelRate);
      this.paneForRate.repaint();
      this.panelRateCont.setBorder(new TitledBorder(new LineBorder(Color.black, 2, true), "Description Of Goods"));
      this.panelInner.setMinimumSize(new Dimension(w, h));
      this.panelInner.setBorder(new TitledBorder(new LineBorder(Color.black, 2, true), "Invoice Details"));
      this.panelInner.add(this.invNumber, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 7, 0, 0), 0, 0));
      this.panelInner.add(this.invNumberTxt, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 10, 0, 0), 0, 0));
      this.panelInner.add(this.dated, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 25, 0, 0), 0, 0));
      this.panelInner.add(this.datedTxt, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 10, 0, 0), 0, 0));
      this.panelInner.add(this.orderNumber, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 25, 0, 0), 0, 0));
      this.panelInner.add(this.orderNumberTxt, new GridBagConstraints(5, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 10, 0, 0), 0, 0));
      this.panelInner.add(this.add, new GridBagConstraints(6, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 50, 0, 7), 0, 0));
      this.panelInner.add(this.orderDate, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 7, 0, 0), 0, 0));
      this.panelInner.add(this.orderDateTxt, new GridBagConstraints(1, 1, 1, 1, 0.5D, 0.0D, 13, 2, new Insets(10, 10, 0, 0), 0, 0));
      this.panelInner.add(this.to, new GridBagConstraints(4, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 25, 0, 0), 0, 0));
      this.panelInner.add(this.toTxt, new GridBagConstraints(5, 1, 1, 1, 0.5D, 0.0D, 13, 2, new Insets(10, 10, 0, 0), 0, 0));
      this.panelInner.add(this.modify, new GridBagConstraints(6, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 50, 0, 7), 0, 0));
      this.panelInner.add(this.delete, new GridBagConstraints(6, 2, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 50, 0, 7), 0, 0));
      this.panelBtn.add(this.ok, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.panelBtn.add(this.cancel, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 25, 0, 0), 0, 0));
      this.panelBtn.add(this.print, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 25, 0, 0), 0, 0));
      this.panelBtn.add(this.addGoods, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 25, 0, 0), 0, 0));
      this.panelRateCont.add(this.paneForRate, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(5, 5, 5, 5), 0, 0));
      this.panel.add(this.panelInner, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.panel.add(this.panelRateCont, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(10, 0, 0, 0), 0, 0));
      this.panel.add(this.panelBtn, new GridBagConstraints(0, 2, 3, 1, 0.0D, 0.0D, 13, 2, new Insets(25, 0, 40, 0), 0, 0));
      this.enableAndDisableComp(mode);
      this.add(this.viewMenu, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.add(this.pane, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.add(this.panel, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
      this.setDefaultCloseOperation(2);
      this.pack();
      this.add.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.mode = MainFrame.ADD_MODE;
            MainFrame.this.setToDefault();
            MainFrame.this.enableAndDisableComp(MainFrame.mode);
         }
      });
      this.modify.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.mode = MainFrame.MODIFY_MODE;
            MainFrame.this.enableAndDisableComp(MainFrame.mode);
         }
      });
      this.cancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.mode = MainFrame.DISPLAY_MODE;
            MainFrame.this.enableAndDisableComp(MainFrame.mode);
            MainFrame.this.count = 5;
         }
      });
      this.ok.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.this.addAndModifyInvoiceDataInToTable();
         }
      });
      this.delete.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.mode = MainFrame.DELETE_MODE;
            MainFrame.this.deleteSelectedRow();
         }
      });
      this.print.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (MainFrame.mode.equals(MainFrame.SELECTION_MODE) || MainFrame.mode.equals(MainFrame.ADD_MODE)) {
               MainFrame.this.printInvoice();
            }

         }
      });
      this.addGoods.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.this.addDynamicRowInPanel();
         }
      });
      this.orderNumberTxt.addFocusListener(new FocusListener() {
         public void focusLost(FocusEvent arg0) {
         }

         public void focusGained(FocusEvent arg0) {
         }
      });
      this.orderDateTxt.addFocusListener(new FocusListener() {
         public void focusLost(FocusEvent arg0) {
            MainFrame.this.toTxt.setCaretPosition(0);
         }

         public void focusGained(FocusEvent arg0) {
         }
      });
      mn2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            MainFrame.this.showMessage();
         }
      });
      this.table.addMouseListener(new MouseListener() {
         public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
               MainFrame.this.popUpmenu = new JPopupMenu();
               MainFrame.this.popUpmenu.add(MainFrame.this.searchItem);
               this.doPopUp(e);
            }

         }

         public void mousePressed(MouseEvent e) {
         }

         public void mouseExited(MouseEvent e) {
         }

         public void mouseEntered(MouseEvent e) {
         }

         public void mouseClicked(MouseEvent e) {
         }

         public void doPopUp(MouseEvent e) {
            MainFrame.this.popUpmenu.show(e.getComponent(), e.getX(), e.getY());
         }
      });
      this.searchItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.this.showSearchDialog();
         }
      });
      this.search.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            MainFrame.this.searchInTable();
         }
      });
      mn1.addMouseListener(new MouseListener() {
         JPopupMenu pop;

         public void mouseReleased(MouseEvent e) {
         }

         public void mousePressed(MouseEvent e) {
         }

         public void mouseExited(MouseEvent e) {
         }

         public void mouseEntered(MouseEvent e) {
         }

         public void mouseClicked(MouseEvent e) {
            this.pop = new JPopupMenu();
            this.pop.add(MainFrame.this.upload);
            this.doPopUp(e);
         }

         public void doPopUp(MouseEvent e) {
            this.pop.show(e.getComponent(), e.getX(), e.getY());
         }
      });
      this.upload.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            UploadData upData = new UploadData();
            upData.showBrowseWindow();
         }
      });
   }

   private void enableAndDisableComp(String mode) {
      if (mode.equals(DISPLAY_MODE)) {
         this.setToDefault();
         this.ok.setEnabled(false);
         this.table.setEnabled(true);
         this.cancel.setEnabled(false);
         this.invNumberTxt.setEditable(false);
         this.datedTxt.setEditable(false);
         this.orderNumberTxt.setEditable(false);
         this.orderDateTxt.setEditable(false);
         this.toTxt.setEditable(false);
         this.invNumberTxt.setText("");
         this.datedTxt.setText("");
         this.orderNumberTxt.setText("");
         this.orderDateTxt.setText("");
         this.toTxt.setText("");
         this.modify.setEnabled(true);
         this.delete.setEnabled(true);
         this.add.setEnabled(true);
         this.print.setEnabled(true);
         this.addGoods.setEnabled(false);
         this.enableDisableCompGoodsPanel(false, mode);
      } else if (mode.equals(MODIFY_MODE)) {
         this.table.setEnabled(false);
         this.ok.setEnabled(true);
         this.cancel.setEnabled(true);
         this.invNumberTxt.setEditable(false);
         this.datedTxt.setEditable(true);
         this.orderNumberTxt.setEditable(false);
         this.orderDateTxt.setEditable(true);
         this.toTxt.setEditable(true);
         this.modify.setEnabled(false);
         this.delete.setEnabled(false);
         this.add.setEnabled(false);
         this.print.setEnabled(false);
         this.addGoods.setEnabled(true);
         this.enableDisableCompGoodsPanel(true, mode);
      } else if (!mode.equals(DELETE_MODE) && mode.equals(ADD_MODE)) {
         this.ok.setEnabled(true);
         this.table.setEnabled(false);
         this.cancel.setEnabled(true);
         this.invNumberTxt.setEditable(true);
         this.datedTxt.setEditable(true);
         this.orderNumberTxt.setEditable(true);
         this.orderDateTxt.setEditable(true);
         this.toTxt.setEditable(true);
         this.modify.setEnabled(false);
         this.delete.setEnabled(false);
         this.print.setEnabled(false);
         this.add.setEnabled(false);
         this.addGoods.setEnabled(true);
         this.invNumberTxt.setText("");
         this.datedTxt.setText("");
         this.orderNumberTxt.setText("");
         this.orderDateTxt.setText("");
         this.toTxt.setText("");
         this.enableDisableCompGoodsPanel(true, mode);
      }

   }

   public void seedDataModel(int row) {
      mode = SELECTION_MODE;
      this.rowSelected = row;
      this.invNumberTxt.setText(this.tableMod.getValueAt(row, 0) != null ? this.tableMod.getValueAt(row, 0).toString() : "");
      this.datedTxt.setText(this.tableMod.getValueAt(row, 1) != null ? this.tableMod.getValueAt(row, 1).toString() : "");
      this.orderNumberTxt.setText(this.tableMod.getValueAt(row, 2) != null ? this.tableMod.getValueAt(row, 2).toString() : "");
      this.orderDateTxt.setText(this.tableMod.getValueAt(row, 3) != null ? this.tableMod.getValueAt(row, 3).toString() : "");
      this.toTxt.setText(this.tableMod.getValueAt(row, 4) != null ? this.tableMod.getValueAt(row, 4).toString() : "");
      Vector<Vector<Object>> dataGoods = this.dt.getGoods(this.tableMod.getValueAt(row, 0).toString(), this.tableMod.getValueAt(row, 2).toString());
      this.seedDataModelForGoods(dataGoods);
      PoiWriteExcelFile.getTableData(this.rowSelected, this.tableMod, dataGoods);
   }

   public void addDynamicRowInPanel() {
      if (this.count != 50) {
         int row = this.count;
         this.gd[row] = new GoodsDetail();
         this.gd[row].setMaximumSize(new Dimension(w - 50, 29));
         this.gd[row].setPreferredSize(new Dimension(w - 50, 29));
         this.gd[row].setMinimumSize(new Dimension(w - 50, 29));
         this.gd[row].sNo.setText(String.valueOf(row));
         this.gd[row].repaint();
         this.panelRate.add(this.gd[row], new GridBagConstraints(0, row, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
         this.gd[row].revalidate();
         this.panelRate.repaint();
         ++this.count;
         ++compCounter;
      }

   }

   private void enableDisableCompGoodsPanel(boolean flag, String mode) {
      int compCount = this.panelRate.getComponentCount();

      for(int i = 0; i < compCount; ++i) {
         this.gd[i] = (GoodsDetail)this.panelRate.getComponent(i);
         this.gd[i].descriptionTxt.setEditable(flag);
         this.gd[i].quantityTxt.setEditable(flag);
         this.gd[i].rateTxt.setEditable(flag);
         this.gd[i].valueTxt.setEditable(flag);
         this.gd[i].clearbtn.setEnabled(flag);
         if (mode.equals(ADD_MODE)) {
            this.gd[i].descriptionTxt.setText("");
            this.gd[i].quantityTxt.setText("");
            this.gd[i].rateTxt.setText("");
            this.gd[i].valueTxt.setText("");
         } else if (mode.equals(SELECTION_MODE)) {
            this.gd[i].descriptionTxt.setText("");
            this.gd[i].quantityTxt.setText("");
            this.gd[i].rateTxt.setText("");
            this.gd[i].valueTxt.setText("");
         }
      }

   }

   public void seedDataModelForGoods(Vector<Vector<Object>> dataGoods) {
      this.setToDefault();
      Vector countOf = (Vector)dataGoods.get(dataGoods.size() - 1);
      int countOfGoods = (Integer)countOf.get(0);

      if (countOfGoods > 4 && countOfGoods != 5) {
         for(int i = 5; i < countOfGoods; ++i) {
            this.gd[i] = new GoodsDetail();
            this.gd[i].setMaximumSize(new Dimension(w - 50, 29));
            this.gd[i].setPreferredSize(new Dimension(w - 50, 29));
            this.gd[i].setMinimumSize(new Dimension(w - 50, 29));
            this.gd[i].sNo.setText(String.valueOf(i));
            this.gd[i].repaint();
            this.panelRate.add(this.gd[i], new GridBagConstraints(0, i, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
            this.gd[i].revalidate();
            this.gd[i].descriptionTxt.setText("");
            this.gd[i].quantityTxt.setText("");
            this.gd[i].rateTxt.setText("");
            this.gd[i].valueTxt.setText("");
            this.panelRate.repaint();
         }
      } else if (countOfGoods == 5) {
         int i = 5;
         this.gd[i] = new GoodsDetail();
         this.gd[i].setMaximumSize(new Dimension(w - 50, 29));
         this.gd[i].setPreferredSize(new Dimension(w - 50, 29));
         this.gd[i].setMinimumSize(new Dimension(w - 50, 29));
         this.gd[i].sNo.setText(String.valueOf(5));
         this.gd[i].repaint();
         this.panelRate.add(this.gd[i], new GridBagConstraints(0, i, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
         this.gd[i].revalidate();
         this.gd[i].descriptionTxt.setText("");
         this.gd[i].quantityTxt.setText("");
         this.gd[i].rateTxt.setText("");
         this.gd[i].valueTxt.setText("");
         this.panelRate.repaint();
      }

      this.enableDisableCompGoodsPanel(false, mode);

      for(int i = 0; i < dataGoods.size(); ++i) {
         int rowId = 0;
         Vector vec = (Vector)dataGoods.get(i);
         if (vec.get(4) != null) {
            rowId = (Integer)vec.get(0);
         }

         this.gd[rowId - 1].descriptionTxt.setText(vec.get(1).toString());
         this.gd[rowId - 1].quantityTxt.setText(vec.get(2).toString());
         this.gd[rowId - 1].rateTxt.setText(vec.get(3).toString());
         this.gd[rowId - 1].valueTxt.setText(vec.get(4).toString());
      }

   }

   private void addAndModifyInvoiceDataInToTable() {
      int success = 0;
      if (mode.equals(ADD_MODE) || mode.equals(MODIFY_MODE)) {
         if (this.checkValidationOverInvoiceDetail()) {
            int compCount = this.panelRate.getComponentCount();
            Map valGoods = new HashMap();
            AddData addData = new AddData();
            addData.setCompCount(compCount);
            addData.setInvoiceNo(this.invNumberTxt.getText());
            addData.setOrderNo(this.orderNumberTxt.getText());
            addData.setDated(this.datedTxt.getText());
            addData.setOrderDate(this.orderDateTxt.getText());
            addData.setTo(this.toTxt.getText());
            addData.setDelInd("N");
            addData.setUpdatedBy(updatedBy);
            addData.setUpdatedDate(new Date((new java.util.Date()).getTime()));

            for(int i = 0; i < compCount; ++i) {
               this.gd[i] = (GoodsDetail)this.panelRate.getComponent(i);
               Map valForEachGood = new HashMap();
               valForEachGood.put("COUNTER", Integer.valueOf(this.gd[i].sNo.getText()));
               if (this.isvalidValue(this.gd[i])) {
                  valForEachGood.put("COUNTER", Integer.valueOf(this.gd[i].sNo.getText()));
                  valForEachGood.put("DESC", this.gd[i].descriptionTxt.getText());
                  valForEachGood.put("QUANTITY", this.gd[i].quantityTxt.getText());
                  valForEachGood.put("RATE", this.gd[i].rateTxt.getText());
                  valForEachGood.put("TOTALVALUE", this.gd[i].valueTxt.getText());
               }

               valGoods.put(i, valForEachGood);
            }

            addData.setGoodsValues(valGoods);
            if (mode.equals(ADD_MODE)) {
               try {
                  success = this.dt.addInvoiceData(addData);
               } catch (SQLException var7) {
                  var7.printStackTrace();
               }
            } else if (mode.equals(MODIFY_MODE)) {
               success = this.dt.modifyInvoiceData(addData);
            }

            if (success == 1) {
               (new RefreshUI(this.refreshUi)).start();
               if (mode.equals(ADD_MODE)) {
                  JOptionPane.showMessageDialog(this, "Data inserted Succesfuly!", "Operation Success", 1);
                  this.enableComponents();
                  this.getListSelection();
               } else if (mode.equals(MODIFY_MODE)) {
                  JOptionPane.showMessageDialog(this, "Data updated Succesfuly!", "Operation Success", 1);
                  this.enableComponents();
                  this.getListSelection();
               }
            } else {
               JOptionPane.showMessageDialog(this, "Something wrong! \n Please contact app support team.", "Operation Unsuccess", 0);
            }
         } else {
            JOptionPane.showMessageDialog(this, "One or more fields in INVOICE DETAILS section \n are left blank !", "Data Validation", 0);
         }
      }

   }

   private boolean isvalidValue(GoodsDetail gd) {
      return gd.descriptionTxt.getText() != null && !gd.descriptionTxt.getText().trim().equals("") && gd.quantityTxt.getText() != null && !gd.quantityTxt.getText().trim().equals("") && gd.rateTxt.getText() != null && !gd.rateTxt.getText().trim().equals("") && gd.valueTxt.getText() != null && !gd.valueTxt.getText().trim().equals("");
   }

   private void deleteSelectedRow() {
      CommonData deleteData = new CommonData();
      String invNo = this.tableMod.getValueAt(this.rowSelected, 0) != null ? this.tableMod.getValueAt(this.rowSelected, 0).toString() : null;
      String orderNo = this.tableMod.getValueAt(this.rowSelected, 2) != null ? this.tableMod.getValueAt(this.rowSelected, 2).toString() : null;
      if (invNo != null) {
         deleteData.setInvoiceNo(invNo);
         deleteData.setOrderNo(orderNo);
         deleteData.setUpdatedBy(updatedBy);
         deleteData.setUpdatedDate(new Date((new java.util.Date()).getTime()));
         int success = 0;

         try {
            success = this.dt.deleteSelectedRow(deleteData);
         } catch (SQLException var6) {
            var6.printStackTrace();
         }

         if (success == 1) {
            (new RefreshUI(this.refreshUi)).start();
            JOptionPane.showMessageDialog(this, "Selected row deleted Succesfuly!", "Operation Success", 1);
         } else {
            JOptionPane.showMessageDialog(this, "Something wrong! \n Please contact app support team.", "Operation Unsuccess", 0);
         }
      }

   }

   private void setToDefault() {
      this.count = 5;
      this.panelRate.removeAll();

      for(int i = 0; i < 4; ++i) {
         int r = i + 1;
         this.gd[i] = new GoodsDetail();
         this.gd[i].setMaximumSize(new Dimension(w - 50, 29));
         this.gd[i].setPreferredSize(new Dimension(w - 50, 29));
         this.gd[i].setMinimumSize(new Dimension(w - 50, 29));
         this.gd[i].sNo.setText(String.valueOf(r));
         this.gd[i].repaint();
         this.panelRate.add(this.gd[i], new GridBagConstraints(0, i, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
         this.gd[i].revalidate();
      }

   }

   private void printInvoice() {
      this.printInv = PrintInvoiceDialog.getInstance();
      this.printInv.setLocationRelativeTo(this);
      this.printInv.setVisible(true);
   }

   private void showMessage() {
      JOptionPane.showMessageDialog(this, "For app configuration, contact \n Sanorous Support team, \n App Developer -> Vishwas \n  08884377132", "App Support !", 1);
   }

   private void enableComponents() {
      this.ok.setEnabled(false);
      this.table.setEnabled(true);
      this.cancel.setEnabled(false);
      this.invNumberTxt.setEditable(false);
      this.datedTxt.setEditable(false);
      this.orderNumberTxt.setEditable(false);
      this.orderDateTxt.setEditable(false);
      this.toTxt.setEditable(false);
      this.modify.setEnabled(true);
      this.delete.setEnabled(true);
      this.add.setEnabled(true);
      this.print.setEnabled(true);
      this.addGoods.setEnabled(false);
      int compCount = this.panelRate.getComponentCount();

      for(int i = 0; i < compCount; ++i) {
         this.gd[i] = (GoodsDetail)this.panelRate.getComponent(i);
         this.gd[i].descriptionTxt.setEditable(false);
         this.gd[i].quantityTxt.setEditable(false);
         this.gd[i].rateTxt.setEditable(false);
         this.gd[i].valueTxt.setEditable(false);
         this.gd[i].clearbtn.setEnabled(false);
      }

   }

   private void getListSelection() {
      RowListener bt = new RowListener(this);
      bt.setSelectedRow(this.table.getRowCount() - 1);
   }

   private void renewMessage() {
      java.util.Date today = new java.util.Date();
      System.out.println(updatedBy + " logged in on " + today);
      if (today.getDate() >= 2 && today.getMonth() >= 5 && today.getYear() >= 115) {
         JOptionPane.showMessageDialog(this, "Your Subscription for InvoiceGen app finishes today.\n Please renew your agreement !", "App Support !", 1);

         try {
            Thread.currentThread();
            Thread.sleep(200L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }

         System.exit(0);
      } else if (today.getDate() > 2 && today.getMonth() == 4 && today.getYear() == 115) {
         JOptionPane.showMessageDialog(this, "Your Subscription for InvoiceGen app will finish on 2/6/2014.\n Please renew your agreement !", "App Support !", 1);
      }

   }

   private boolean checkValidationOverInvoiceDetail() {
      return this.isInvoiceValNotNull();
   }

   private boolean isInvoiceValNotNull() {
      return this.invNumberTxt.getText() != null && (!this.invNumberTxt.getText().trim().equals("") || this.datedTxt.getText() != null) && (!this.datedTxt.getText().trim().equals("") || this.orderNumber.getText() != null) && (!this.orderDateTxt.getText().trim().equals("") || this.orderDateTxt.getText() != null) && !this.orderDateTxt.getText().trim().equals("");
   }

   private void showSearchDialog() {
      this.searchDialog = new JDialog();
      this.searchDialog.setLayout(new GridLayout(1, 2));
      this.searchDialog.setAlwaysOnTop(true);
      this.search.registerKeyboardAction(this.search.getActionForKeyStroke(KeyStroke.getKeyStroke(32, 0, false)), KeyStroke.getKeyStroke(10, 0, false), 0);
      this.search.registerKeyboardAction(this.search.getActionForKeyStroke(KeyStroke.getKeyStroke(32, 0, true)), KeyStroke.getKeyStroke(10, 0, true), 0);
      this.InvoiceNo = new IGTextField(50, "Invoice No");
      this.searchDialog.setSize(400, 60);
      this.searchDialog.add(this.InvoiceNo);
      this.searchDialog.add(this.search);
      this.searchDialog.setLocationRelativeTo(this);
      this.searchDialog.setTitle("Search Invoice by Invoice No");
      this.searchDialog.setResizable(false);
      this.searchDialog.setVisible(true);
   }

   private void registerAllButtonForKeyStrok(List<JButton> listButton) {
      Iterator var3 = listButton.iterator();

      while(var3.hasNext()) {
         JButton btn = (JButton)var3.next();
         btn.registerKeyboardAction(btn.getActionForKeyStroke(KeyStroke.getKeyStroke(32, 0, true)), KeyStroke.getKeyStroke(10, 0, true), 0);
         btn.registerKeyboardAction(btn.getActionForKeyStroke(KeyStroke.getKeyStroke(32, 0, true)), KeyStroke.getKeyStroke(10, 0, true), 0);
      }

   }

   private void searchInTable() {
      String inv = "";
      boolean success = false;
      if (this.InvoiceNo.getText() != null) {
         inv = this.InvoiceNo.getText().trim();

         for(int row = 0; row <= this.table.getRowCount() - 1; ++row) {
            for(int col = 0; col <= this.table.getColumnCount() - 1; ++col) {
               if (inv.equals(this.table.getValueAt(row, col))) {
                  success = true;
                  this.table.scrollRectToVisible(this.table.getCellRect(row, 0, true));
                  this.table.setRowSelectionInterval(row, row);

                  for(int i = 0; i <= this.table.getColumnCount() - 1; ++i) {
                     this.table.getColumnModel().getColumn(i).setCellRenderer(new HighlightRenderer());
                  }
               }
            }
         }
      }

      this.searchDialog.setVisible(false);
      if (!success) {
         JOptionPane.showMessageDialog(this, "Provided Invoice No doesn't exist!", "Error Message", 0);
      }

   }
}
