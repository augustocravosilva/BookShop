/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package storeguiapp;

import logic.Client;
import applicationejbAPI.StoreBeanRemote;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import simple.SimpleBook;

/**
 *
 * @author augusto
 */
public class GuiFrame extends javax.swing.JFrame {
    private StoreBeanRemote storeBean = lookupStoreBeanRemote();
    
    private MyPrinter printer = new MyPrinter();
    
    /**
     * Creates new form GuiFrame
     */
    public GuiFrame() {
        initComponents();
        updateClientList();
        updateBookList(false);
        quantity_spinner.setValue(1);
        updateButtons();
    }
    
    private List<Client> clients;
    void updateClientList() {
        clients = storeBean.getClients();
        ArrayList<String> clientNames = new ArrayList();
        for (Client client : clients) {
            clientNames.add(client.getFullname());
        }
        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(clientNames.toArray()));
    }
    
    private void updateBookList(boolean keepSelectedItem){
        int pos = booksCombo.getSelectedIndex();
        List<SimpleBook> books = storeBean.getBooks();
        booksCombo.setModel(new javax.swing.DefaultComboBoxModel(books.toArray()));
        if(keepSelectedItem)
            booksCombo.setSelectedIndex(pos);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        booksCombo = new javax.swing.JComboBox();
        quantity_spinner = new javax.swing.JSpinner();
        sell_button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        clientCombo = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        add_stock_button = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        order_button = new javax.swing.JButton();
        print_checkbox = new javax.swing.JCheckBox();
        list_sells_button = new javax.swing.JButton();
        list_orders_button = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        edit_client_button = new javax.swing.JButton();
        book_info_button = new javax.swing.JButton();
        add_book_button = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Store");
        setBackground(new java.awt.Color(153, 204, 255));
        setName("store"); // NOI18N

        booksCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Loading" }));
        booksCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                booksComboActionPerformed(evt);
            }
        });

        quantity_spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                quantity_spinnerStateChanged(evt);
            }
        });

        sell_button.setText("Sell");
        sell_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sell_buttonActionPerformed(evt);
            }
        });

        jLabel1.setText("Book");

        jLabel2.setText("New Sell - Client");

        clientCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Loading" }));
        clientCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientComboActionPerformed(evt);
            }
        });

        jButton2.setText("New Client");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        add_stock_button.setText("Add to Stock");
        add_stock_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_stock_buttonActionPerformed(evt);
            }
        });

        jLabel3.setText("Incoming package");

        order_button.setText("Order");
        order_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                order_buttonActionPerformed(evt);
            }
        });

        print_checkbox.setText("Print");

        list_sells_button.setText("List Sells");
        list_sells_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list_sells_buttonActionPerformed(evt);
            }
        });

        list_orders_button.setText("List Orders");
        list_orders_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list_orders_buttonActionPerformed(evt);
            }
        });

        edit_client_button.setText("Edit Client");
        edit_client_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_client_buttonActionPerformed(evt);
            }
        });

        book_info_button.setText("Info");
        book_info_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                book_info_buttonActionPerformed(evt);
            }
        });

        add_book_button.setText("Add Book");
        add_book_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_book_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(list_sells_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(list_orders_button)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 14, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(add_stock_button)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(edit_client_button)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(print_checkbox))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(sell_button)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(order_button))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(add_book_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(booksCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(book_info_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(quantity_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(booksCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantity_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(book_info_button)
                    .addComponent(add_book_button))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add_stock_button)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell_button)
                    .addComponent(jLabel2)
                    .addComponent(order_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(print_checkbox)
                    .addComponent(edit_client_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(list_sells_button)
                    .addComponent(list_orders_button))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void booksComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_booksComboActionPerformed
        updateButtons();
    }//GEN-LAST:event_booksComboActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NewClientFrame cf = new NewClientFrame(this);
        cf.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updateClientList();
        updateBookList(false);
        updateButtons();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void add_stock_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_stock_buttonActionPerformed
        storeBean.receiveStock(((SimpleBook)booksCombo.getSelectedItem()).isbn, (int)quantity_spinner.getValue());
        updateBookList(true);
    }//GEN-LAST:event_add_stock_buttonActionPerformed
    
    private void clientComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clientComboActionPerformed

    private void sell_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sell_buttonActionPerformed
        SimpleBook b = (SimpleBook) booksCombo.getSelectedItem();
        int quantity = (Integer) quantity_spinner.getValue();
        Client c = clients.get(clientCombo.getSelectedIndex());
        boolean result = storeBean.buyBook(b.isbn,quantity, c.getId(), b.price*quantity);
        if(result)
        {
            if(print_checkbox.isSelected())
                printer.print(String.format("RECEIPT\n\n\nClient: %s\n%s\n%s\n\nBook: %s\nQuantity: %d\nTotal: %.2f\n\nThank you",c.getFullname(),c.getAdress(),c.getEmail(),b.title,quantity,b.price*quantity));
            else JOptionPane.showMessageDialog(this,  "Sold successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);  
        }else{
            JOptionPane.showMessageDialog(this, "There was a problem processing this order. Try refreshing.", "Error", JOptionPane.ERROR_MESSAGE);  
        }
        updateBookList(true);
    }//GEN-LAST:event_sell_buttonActionPerformed

    private void quantity_spinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_quantity_spinnerStateChanged
        if((Integer) quantity_spinner.getValue() < 1)
        {
            quantity_spinner.setValue(1);
        }
        updateButtons();
           
    }//GEN-LAST:event_quantity_spinnerStateChanged

    private void updateButtons() {
        if(((SimpleBook)booksCombo.getSelectedItem()).stock < (int)quantity_spinner.getValue())
        {
            order_button.setEnabled(true);
            sell_button.setEnabled(false);
        }else{
            order_button.setEnabled(false);
            sell_button.setEnabled(true);
        }
    }

    private void order_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_order_buttonActionPerformed
        SimpleBook b = (SimpleBook) booksCombo.getSelectedItem();
        int quantity = (Integer) quantity_spinner.getValue();
        Client c = clients.get(clientCombo.getSelectedIndex());
        storeBean.orderBook(b.isbn,quantity, c.getId());
        JOptionPane.showMessageDialog(this,  "Ordered successfully.", "Information", JOptionPane.INFORMATION_MESSAGE);  
    }//GEN-LAST:event_order_buttonActionPerformed

    private void edit_client_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_client_buttonActionPerformed
        int cid = clients.get(clientCombo.getSelectedIndex()).getId();
        NewClientFrame cf = new NewClientFrame(this,1);
        cf.setVisible(true);
    }//GEN-LAST:event_edit_client_buttonActionPerformed

    private void list_sells_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list_sells_buttonActionPerformed
        new ListOrders(false).setVisible(true);
    }//GEN-LAST:event_list_sells_buttonActionPerformed

    private void list_orders_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list_orders_buttonActionPerformed
        new ListOrders(true).setVisible(true);
    }//GEN-LAST:event_list_orders_buttonActionPerformed

    private void book_info_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_book_info_buttonActionPerformed
        SimpleBook sb = (SimpleBook)booksCombo.getSelectedItem();
        JOptionPane.showMessageDialog(this, String.format(" ISBN: %s\n Title: %s\n Price: %.2f\n Stock: %d", sb.isbn,  sb.title,sb.price, sb.stock),"Book Details",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_book_info_buttonActionPerformed

    private void add_book_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_book_buttonActionPerformed
        String isbn = (String)JOptionPane.showInputDialog(this, "Input ISBN", "New Book", JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(storeBean.addBook(isbn)){
            updateBookList(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error adding book. Are you sure the isbn is valid?");
        }
    }//GEN-LAST:event_add_book_buttonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_book_button;
    private javax.swing.JButton add_stock_button;
    private javax.swing.JButton book_info_button;
    private javax.swing.JComboBox booksCombo;
    private javax.swing.JComboBox clientCombo;
    private javax.swing.JButton edit_client_button;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton list_orders_button;
    private javax.swing.JButton list_sells_button;
    private javax.swing.JButton order_button;
    private javax.swing.JCheckBox print_checkbox;
    private javax.swing.JSpinner quantity_spinner;
    private javax.swing.JButton sell_button;
    // End of variables declaration//GEN-END:variables
    
    
    private StoreBeanRemote lookupStoreBeanRemote() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StoreBeanRemote) c.lookup("java:global/Application/Application-ejb/StoreBean!applicationejbAPI.StoreBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public class MyPrinter implements Printable {
 
        private String text;
        
        public int print(Graphics g, PageFormat pf, int page) throws
                                                            PrinterException {

            if (page > 0) { /* We have only one page, and 'page' is zero-based */
                return NO_SUCH_PAGE;
            }

            /* User (0,0) is typically outside the imageable area, so we must
             * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());

            /* Now we perform our rendering */
            String lines[] = text.split("\n");
            for(int i = 0; i < lines.length; i++)
                g.drawString(lines[i], 50, 50+i*20);

            /* tell the caller that this page is part of the printed document */
            return PAGE_EXISTS;
        }
        
        public void print(String text)
        {
            this.text = text;
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                     job.print();
                } catch (PrinterException ex) {
                 /* The job did not successfully complete */
                }
            }
        }
    
    }
    
    
}
