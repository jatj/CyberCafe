/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Gearnest
 */
public class Cliente extends javax.swing.JFrame {

    /**
     * Creates new form Cliente
     */
    static String IP = "127.0.0.1";
    static int PUERTO = 1201;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static boolean connected = false;
    // bloqueado desbloqueado
    static String status = "bloqueado";
    
    public Cliente() {
        initComponents();
        statusLabel.setVisible(false);
        ipText.setText(IP);
        puertoText.setText(String.valueOf(PUERTO));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        configPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ipText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        puertoText = new javax.swing.JTextField();
        conectaBtn = new javax.swing.JToggleButton();
        tituloLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Bloqueado");

        jLabel1.setText("IP: ");

        ipText.setText("jTextField1");

        jLabel2.setText("Puerto: ");

        puertoText.setText("jTextField1");
        puertoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puertoTextActionPerformed(evt);
            }
        });

        conectaBtn.setText("Conectar");
        conectaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conectaBtnMouseClicked(evt);
            }
        });
        conectaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectaBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puertoText, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(conectaBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(puertoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(conectaBtn))
        );

        tituloLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloLabel.setText("Cyber Cafe");
        tituloLabel.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(configPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tituloLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloLabel)
                .addGap(32, 32, 32)
                .addComponent(configPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 128, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        connected = false;
        try{
            System.out.println("Cerrando");
            String msgout="Desconecta";
            dout.writeUTF(msgout);
            s.close();
        }catch(Exception e){}
    }//GEN-LAST:event_formWindowClosing

    private void puertoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puertoTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_puertoTextActionPerformed

    private void conectaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectaBtnActionPerformed
        // TODO add your handling code here:
        IP = ipText.getText();
        if(!validaIPv4(IP)){
            JOptionPane.showMessageDialog(this,
            "No es una dirección IPv4",
            "Error conectando",
            JOptionPane.ERROR_MESSAGE);
        }else if(!isNumeric(puertoText.getText())){
            JOptionPane.showMessageDialog(this,
            "No es un puerto válido",
            "Error conectando",
            JOptionPane.ERROR_MESSAGE);
        }else{
            PUERTO = Integer.parseInt(puertoText.getText());
            System.out.println("Conectando");
            Thread t = new Thread() {
                public void run() {
                    initClient();
                }
            };
            t.start();            
        }
    }//GEN-LAST:event_conectaBtnActionPerformed

    private void conectaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conectaBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_conectaBtnMouseClicked
    
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
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }
    
    public static void initClient(){
        try {
            s = new Socket(IP, PUERTO);
            
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            System.out.println("Conectado");
            connected = true;
            tituloLabel.setVisible(false);
            configPanel.setVisible(false);
            statusLabel.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
            "No pudimos conectar con el socket",
            "Error conectando",
            JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String msgin = "";
            while (!msgin.equals("exit") && connected) {

                msgin = din.readUTF();
                System.out.println(msgin);
                switch (msgin) {
                    case "Desbloquea":
                        desbloquea();
                        break;
                    case "Bloquea":
                        bloquea();
                        break;
                }
            }
            s.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
            "Ocurrio un error durante la comunicación",
            "Error socket",
            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void desbloquea(){
        try{
            status = "desbloqueado";
            statusLabel.setText(status);
        }catch(Exception ex){}
    }
    
    public static void bloquea(){
        try{
            status = "bloqueado";
            statusLabel.setText(status);
        }catch(Exception ex){}
    }
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validaIPv4(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton conectaBtn;
    private static javax.swing.JPanel configPanel;
    private static javax.swing.JTextField ipText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JTextField puertoText;
    private static javax.swing.JLabel statusLabel;
    private static javax.swing.JLabel tituloLabel;
    // End of variables declaration//GEN-END:variables
}
