package pos.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.accessibility.AccessibleRole;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import pos.classes.DBmysqlconnection;


public class TakeOrder extends javax.swing.JFrame {
DBmysqlconnection s;
void fillpnlmenu(String names){
               int i = s.getCatagoryID(names);
               Vector vi = null;
               if(names.equals("Deals")){
                   
                    vi =  s.getDealsItems();
               }else{
                   vi =  s.getItemsInVector(i);
               }
               
               Vector name =(Vector) vi.elementAt(0);
               Vector price = (Vector) vi.elementAt(1);
               jPanel3.removeAll();
               jPanel3.repaint();
               jPanel3.validate();
               
               
               for (int k = 0; k < name.size(); k++) {
                   
        
            JButton l = new JButton();
            l.setText(name.elementAt(k).toString() + " @ " + price.elementAt(k).toString()+ "");
            Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 0);
            Font font = new Font("Tw cen MT",Font.BOLD, 16);
            l.setSize(new Dimension(100, 50));
            l.setBorder(border);
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            l.setFont(font);
            l.setBackground(new Color(102,102,255));
            l.setOpaque(false);
            
            l.addMouseListener(new java.awt.event.MouseAdapter() {
            
            
            int i =1;
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            
                
                
            DefaultTableModel f = (DefaultTableModel) tblOrder.getModel();
            String name =l.getText().substring(0, l.getText().indexOf('@'));
            String price = l.getText().substring(l.getText().indexOf('@')+1);
            boolean status=checkfornameintable(name);
            double priceind = Double.parseDouble(price);
            
            if(status!=true){
            
            Object[] row = {name,1,priceind};

            f.addRow(row);
                
            }else{
            for (int i = f.getRowCount() - 1; i >= 0; --i) {
            if (f.getValueAt(i, 0).equals(name)) {
                    int a =  Integer.parseInt(f.getValueAt(i, 1).toString());
                    int b  = a +1;
                    f.setValueAt(b, i,1);
                    
                }
            }      
            }
            
            for (int i = f.getRowCount() - 1; i >= 0; --i) {
        
            if (f.getValueAt(i, 0).equals(name)) {
                    int a =  Integer.parseInt(f.getValueAt(i, 1).toString());
                    f.setValueAt(a*priceind, i,2);
                    
                }
            
            }
//             int row = names.indexOf(l.getText());
//            int a = Integer.parseInt(f.getValueAt(row, 1).toString());
//            a++;
//            Object aa = a;
//            f.setValueAt(aa, row, 1);
            double sum=0;
            for (int i = f.getRowCount() - 1; i >= 0; --i) {
                sum = sum + Double.parseDouble(f.getValueAt(i, 2).toString());
                
                txtTotal.setText(Double.toString(sum));
            }
                
                
                
            }
            
        });
        
        jPanel3.add(l);
        jPanel3.repaint();
        jPanel3.validate();
        
               
               }
               
        
        
    }
private void label(){
        Vector v = new Vector();
        
        v=s.getcatagoriesInVector();
        
        for (int i = 0; i < v.size(); i++) {
            
        
            JButton l1 = new JButton();
        l1.setText(v.elementAt(i).toString());
        Border border = BorderFactory.createLineBorder(new Color (255, 255, 255), 1);
        Font font = new Font("Tw cen MT",Font.PLAIN, 20);
        
        l1.setSize(200, 90);
        l1.setBorder(border);
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        l1.setFont(font);
        l1.setBackground(new Color(100,102,255));
        l1.setOpaque(false);
        l1.setMinimumSize(new Dimension(150, 70));
        l1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillpnlmenu(l1.getText());
               
            }
        });
        jPanel2.add(l1);
        
        
        
        
        }
    }
private void table(){
     for (int i = 1; i <= s.getnooftables(); i++) {
            
        
        JButton l1 = new JButton();
        l1.setText(" "+ Integer.toString(i)+" ");
        Border border = BorderFactory.createLineBorder(new Color (255, 255, 255), 1);
        Font font = new Font("Tw cen MT",Font.BOLD, 15);
        
        l1.setSize(200, 90);
        l1.setBorder(border);
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        l1.setFont(font);
        l1.setBackground(new Color(102,102,255));
        l1.setOpaque(false);
        l1.setMinimumSize(new Dimension(150, 70));
        l1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                
                if(l1.isEnabled()){
                    txtTable.setText(l1.getText());
                    l1.setEnabled(false);
                }else{
                    
                    l1.setEnabled(true);
                    txtTable.setText("0");
                    
                  }
                
                
                
            }
        });
        pnltable.add(l1);
        
        
        
        
        }
}    
public TakeOrder(DBmysqlconnection s) {
        this.s = s;
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TakeOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TakeOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TakeOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TakeOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        GridLayout experimentLayout = new GridLayout(4,2,5,5);
        GridLayout experimentLayout3 = new GridLayout(2,2,5,5);
        jPanel3.setLayout(experimentLayout);
        jPanel2.setLayout(experimentLayout3);
        GridLayout experimentLayout2 = new GridLayout(10,2);
        pnltable.setLayout(experimentLayout2);
//        JTableHeader h = tblOrder.getTableHeader();
//        h.setBackground(Color.red);
//        h.setForeground(Color.WHITE);
//        h.setSize(10, 39);
        label();
        table();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlCategory = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pnltable = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnTakeAway = new javax.swing.JButton();
        btnDineIn = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtTable = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCashReceived = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCashReturn = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnSeeStatus = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        btnSeeStatus1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jLabel3.setBackground(new java.awt.Color(228, 243, 255));
        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CATEGORY");
        jLabel3.setOpaque(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("INDEX");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCategory.setBackground(new java.awt.Color(255, 255, 255));
        pnlCategory.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(204, 204, 204)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout pnlCategoryLayout = new javax.swing.GroupLayout(pnlCategory);
        pnlCategory.setLayout(pnlCategoryLayout);
        pnlCategoryLayout.setHorizontalGroup(
            pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlCategoryLayout.setVerticalGroup(
            pnlCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );

        jPanel1.add(pnlCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 940, 110));

        tblOrder.setAutoCreateRowSorter(true);
        tblOrder.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblOrder.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Items", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrder.setGridColor(new java.awt.Color(255, 255, 255));
        tblOrder.setRowHeight(20);
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrder);
        if (tblOrder.getColumnModel().getColumnCount() > 0) {
            tblOrder.getColumnModel().getColumn(0).setResizable(false);
            tblOrder.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblOrder.getColumnModel().getColumn(1).setResizable(false);
            tblOrder.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblOrder.getColumnModel().getColumn(2).setResizable(false);
            tblOrder.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 300, 560));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  Take Order");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 40));

        pnlMenu.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        pnlMenu.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(jPanel3);

        pnlMenu.add(jScrollPane5);

        jPanel1.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 940, 350));

        jLabel4.setBackground(new java.awt.Color(255, 236, 228));
        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TABLE");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 0, new java.awt.Color(204, 204, 204)));
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, 110, 30));

        jLabel9.setBackground(new java.awt.Color(228, 243, 255));
        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Categories");
        jLabel9.setOpaque(true);
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 940, 30));

        pnltable.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnltableLayout = new javax.swing.GroupLayout(pnltable);
        pnltable.setLayout(pnltableLayout);
        pnltableLayout.setHorizontalGroup(
            pnltableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        pnltableLayout.setVerticalGroup(
            pnltableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(pnltable);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 70, 110, 630));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTakeAway.setBackground(new java.awt.Color(153, 255, 153));
        btnTakeAway.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnTakeAway.setText("Take Away");
        btnTakeAway.setContentAreaFilled(false);
        btnTakeAway.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTakeAway.setOpaque(true);
        btnTakeAway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTakeAwayActionPerformed(evt);
            }
        });
        jPanel4.add(btnTakeAway, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 11, 145, 36));

        btnDineIn.setBackground(new java.awt.Color(153, 255, 153));
        btnDineIn.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnDineIn.setText("Dine In");
        btnDineIn.setContentAreaFilled(false);
        btnDineIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDineIn.setOpaque(true);
        btnDineIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDineInActionPerformed(evt);
            }
        });
        jPanel4.add(btnDineIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 11, 145, 36));

        btnCancel.setBackground(new java.awt.Color(153, 255, 153));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setOpaque(true);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 11, 145, 36));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 58, 455, 10));

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel5.setText("Table");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 82, -1, -1));

        txtTable.setEditable(false);
        txtTable.setBackground(new java.awt.Color(255, 255, 255));
        txtTable.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txtTable.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTable.setText("0");
        txtTable.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));
        txtTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(txtTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 79, 116, 35));

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setText("0.00");
        txtTotal.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));
        txtTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 127, 35));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel6.setText("Total");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, -1, -1));

        txtCashReceived.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtCashReceived.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCashReceived.setText("0.00");
        txtCashReceived.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));
        txtCashReceived.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCashReceived.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCashReceivedActionPerformed(evt);
            }
        });
        txtCashReceived.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCashReceivedKeyReleased(evt);
            }
        });
        jPanel4.add(txtCashReceived, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 181, 53));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel7.setText("CASH RECEIVED");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel8.setText("CASH RETURN");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        txtCashReturn.setEditable(false);
        txtCashReturn.setBackground(new java.awt.Color(255, 255, 255));
        txtCashReturn.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtCashReturn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCashReturn.setText("0.00");
        txtCashReturn.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 204, 153)));
        txtCashReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCashReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCashReturnActionPerformed(evt);
            }
        });
        jPanel4.add(txtCashReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 60, 181, 53));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 12, 100));

        btnSeeStatus.setBackground(new java.awt.Color(102, 102, 255));
        btnSeeStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSeeStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnSeeStatus.setText("Status");
        btnSeeStatus.setContentAreaFilled(false);
        btnSeeStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeeStatus.setOpaque(true);
        btnSeeStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSeeStatusMouseClicked(evt);
            }
        });
        btnSeeStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeeStatusActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeeStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 181, 53));
        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -30, -1, -1));

        btnSeeStatus1.setBackground(new java.awt.Color(102, 102, 255));
        btnSeeStatus1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSeeStatus1.setForeground(new java.awt.Color(255, 255, 255));
        btnSeeStatus1.setText("Delete");
        btnSeeStatus1.setContentAreaFilled(false);
        btnSeeStatus1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeeStatus1.setOpaque(true);
        btnSeeStatus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSeeStatus1MouseClicked(evt);
            }
        });
        btnSeeStatus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeeStatus1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeeStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 70, 181, 53));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 1350, 130));

        jLabel10.setBackground(new java.awt.Color(228, 243, 255));
        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MENU");
        jLabel10.setOpaque(true);
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 940, 30));

        jMenu1.setText("File");

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_MASK));
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Stock");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 699, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCashReceivedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCashReceivedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCashReceivedActionPerformed

    private void txtCashReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCashReturnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCashReturnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Menu m = new Menu(s);
        m.setVisible(true);
        m.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowClosed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseClicked

    private void btnSeeStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeStatusMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeeStatusMouseClicked

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
        DefaultTableModel f = (DefaultTableModel)this.tblOrder.getModel();
        int row = this.tblOrder.getSelectedRow();
        
        int a = Integer.parseInt(f.getValueAt(row, 1).toString());
        double unitprice = Double.parseDouble(f.getValueAt(row, 2).toString())/a;
        a--;
        Object a1 = a;
        Object a2 = a*unitprice;
        f.setValueAt(a1, row, 1);
        f.setValueAt(a2, row, 2);
        if(a==0){
        f.removeRow(row);
        txtTotal.setText("0.00");
        }
        
         double sum=0;
            for (int i = f.getRowCount() - 1; i >= 0; --i) {
                
                sum = sum + Double.parseDouble(f.getValueAt(i, 2).toString());
                
                txtTotal.setText(Double.toString(sum));
            }
    }//GEN-LAST:event_tblOrderMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        txtTotal.setText("0.00");
        txtTable.setText("0");
        DefaultTableModel f = (DefaultTableModel)this.tblOrder.getModel();
        f.setRowCount(0);
        txtCashReceived.setText("0.00");
        txtCashReturn.setText("0.00");
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSeeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeeStatusActionPerformed
        SalesReport_1 sr = new SalesReport_1(s);
        sr.setVisible(true);
        sr.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnSeeStatusActionPerformed

public PageFormat getPageFormat(PrinterJob pj)
{
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double middleHeight =8.0;  
    double headerHeight = 2.0;                  
    double footerHeight = 3.0;                  
    double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
    double height = convert_CM_To_PPI(headerHeight+middleHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(                    
        0,
        5,
        width,            
        height - convert_CM_To_PPI(1)
    );   //define boarder size    after that print area width is about 180 points
            
    pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
    pf.setPaper(paper);    

    return pf;
}
    
protected static double convert_CM_To_PPI(double cm) {            
	        return toPPI(cm * 0.393600787);            
}
 
protected static double toPPI(double inch) {            
	        return inch * 72d;            
}

public class BillPrintable implements Printable {
    
   
    
    
  public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
  throws PrinterException 
  {    
      
                
        
      int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    

            double width = pageFormat.getImageableWidth();                    
           
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 

            ////////// code by alqama//////////////

            FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        //    int idLength=metrics.stringWidth("000000");
            //int idLength=metrics.stringWidth("00");
            int idLength=metrics.stringWidth("000");
            int amtLength=metrics.stringWidth("000000");
            int qtyLength=metrics.stringWidth("00000");
            int priceLength=metrics.stringWidth("000000");
            int prodLength=(int)width - idLength - amtLength - qtyLength - priceLength-17;

        //    int idPosition=0;
        //    int productPosition=idPosition + idLength + 2;
        //    int pricePosition=productPosition + prodLength +10;
        //    int qtyPosition=pricePosition + priceLength + 2;
        //    int amtPosition=qtyPosition + qtyLength + 2;
            
            int productPosition = 0;
            int discountPosition= prodLength+5;
            int pricePosition = discountPosition +idLength+10;
            int qtyPosition=pricePosition + priceLength + 4;
            int amtPosition=qtyPosition + qtyLength;
            
            
              
        try{
            /*Draw Header*/
            int y=20;//20
            int yShift = 10;//10
            int headerRectHeight=15;
            int headerRectHeighta=40;
            
            ///////////////// Product names Get ///////////
//                String  pn1a=pn1.getText();
//                String pn2a=pn2.getText();
//                String pn3a=pn3.getText();
//                String pn4a=pn4.getText();
            ///////////////// Product names Get ///////////
                
            
            ///////////////// Product price Get ///////////
//                int pp1a=Integer.valueOf(pp1.getText());
//                int pp2a=Integer.valueOf(pp2.getText());
//                int pp3a=Integer.valueOf(pp3.getText());
//                int pp4a=Integer.valueOf(pp4.getText());
//                int sum=pp1a+pp2a+pp3a+pp4a;
            ///////////////// Product price Get ///////////
                
             g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawString("-------------------------------------",12,y);y+=yShift;
            g2d.drawString("           BAITHAK O BAHLOOL         ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString("     Order Number = "+s.getorderid()+"         ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Food Name              QTY   T.Price ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
//            for (int i = 0; i <= tblOrder.getRowCount()-1; i++) {
//                g2d.drawString(""+tblOrder.getValueAt(i, 0)+"         "+tblOrder.getValueAt(i, 1)+"       "+tblOrder.getValueAt(i, 2)+"",0,y);y+=yShift;
//            }
            
            for (int i = 0; i <= tblOrder.getRowCount()-1; i++) {
                g2d.drawString(tblOrder.getValueAt(i, 0).toString(),14,y);
                g2d.drawString(tblOrder.getValueAt(i, 1).toString(),140,y);
                g2d.drawString(tblOrder.getValueAt(i, 2).toString(),170,y);
                y+=yShift;
            }
            
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Total amount:                "+txtTotal.getText()+"",10,y);y+=yShift;
            g2d.drawString(" Table Number:                "+txtTable.getText()+"",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString("Date = "+s.getDate(s.getorderid())+"   ",10,y);y+=yShift;
            g2d.drawString("Time = "+s.getTime(s.getorderid())+"     ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("    THANKS TO VISIT OUR RESTUARANT   ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
                   
           
             
           
            
//            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
//            g2d.drawString("Customer Shopping Invoice", 30,y);y+=yShift; 
          

    }
    catch(Exception r){
    r.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }


    
    
    
    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
            System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtCashReceivedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCashReceivedKeyReleased
            if(txtCashReceived.getText().equals("")){}else{
        try{
        double a = Double.parseDouble(txtCashReceived.getText());
        double b = (double) Double.parseDouble(txtTotal.getText());
        txtCashReturn.setText(Double.toString(a-b));
        }catch(Exception c){
            throw c;
        }}
    }//GEN-LAST:event_txtCashReceivedKeyReleased

    private void btnDineInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDineInActionPerformed
        if(tblOrder.getRowCount()>0){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            
            String date = dtf.format(now);
            String time = dtf2.format(now);
            
         try{           
            boolean b = s.InsertDataInOrder(Integer.parseInt(txtTable.getText().replaceAll("\\s+","")),date, Double.parseDouble(txtTotal.getText()),"Active","Dine In",time);
            if(b){
               
               int a = s.getorderid();
                for (int j = 0; j < tblOrder.getRowCount(); j++) {
                    double rate = Double.parseDouble(tblOrder.getValueAt(j, 2).toString())/Integer.parseInt(tblOrder.getValueAt(j, 1).toString());
                        s.InsertDataInSubOrder(a,tblOrder.getValueAt(j, 0).toString(),Integer.parseInt(tblOrder.getValueAt(j, 1).toString()),rate ,Double.parseDouble(tblOrder.getValueAt(j, 2).toString()));
                }
        
                 PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
            
            
             pj.setCopies(2);
             pj.print();
            
          
        }
         catch (Exception ex) {
                 ex.printStackTrace();
        }
        
                txtTotal.setText("0.00");
        txtTable.setText("0");
        DefaultTableModel f = (DefaultTableModel)this.tblOrder.getModel();
        f.setRowCount(0);
        txtCashReceived.setText("0.00");
        txtCashReturn.setText("0.00");
               
               
           }else{
               
           }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
                }
         
         
         
            
        
        }
    }//GEN-LAST:event_btnDineInActionPerformed

    private void btnTakeAwayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTakeAwayActionPerformed
                    if(tblOrder.getRowCount()>0){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
             
            String date = dtf.format(now);
            String time = dtf2.format(now);
            
         try{           
            boolean b = s.InsertDataInOrder(Integer.parseInt(txtTable.getText().replaceAll("\\s+","")),date, Double.parseDouble(txtTotal.getText()),"Active","TAKE AWAY",time);
            if(b){
               
               int a = s.getorderid();
                for (int j = 0; j < tblOrder.getRowCount(); j++) {
                    double rate = Double.parseDouble(tblOrder.getValueAt(j, 2).toString())/Integer.parseInt(tblOrder.getValueAt(j, 1).toString());
                        s.InsertDataInSubOrder(a,tblOrder.getValueAt(j, 0).toString(),Integer.parseInt(tblOrder.getValueAt(j, 1).toString()),rate ,Double.parseDouble(tblOrder.getValueAt(j, 2).toString()));
                }
                
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
            
            
             pj.setCopies(2);
             pj.print();
            
          
        }
         catch (Exception ex) {
                 ex.printStackTrace();
        }
        
        txtTotal.setText("0.00");
        txtTable.setText("0");
        DefaultTableModel f = (DefaultTableModel)this.tblOrder.getModel();
        f.setRowCount(0);
        txtCashReceived.setText("0.00");
        txtCashReturn.setText("0.00");
                 
               
               
           }else{
               
           }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
                }
            
        
        }
    }//GEN-LAST:event_btnTakeAwayActionPerformed

    private void btnSeeStatus1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeStatus1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeeStatus1MouseClicked

    private void btnSeeStatus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeeStatus1ActionPerformed
                
        
        DefaultTableModel f = (DefaultTableModel) tblOrder.getModel();
                int i = tblOrder.getSelectedRow();
                if(i>=0);
                f.removeRow(i);
    }//GEN-LAST:event_btnSeeStatus1ActionPerformed

    private boolean checkfornameintable(String name){
     DefaultTableModel f = (DefaultTableModel) tblOrder.getModel();
        
    for (int i = f.getRowCount() - 1; i >= 0; --i) {
             if(f.getValueAt(i, 0).toString().equals(name)){
                 return true;
             }
                 }
    
    return false;
    }
   
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDineIn;
    private javax.swing.JButton btnSeeStatus;
    private javax.swing.JButton btnSeeStatus1;
    private javax.swing.JButton btnTakeAway;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JPanel pnlCategory;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnltable;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtCashReceived;
    private javax.swing.JTextField txtCashReturn;
    private javax.swing.JTextField txtTable;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
