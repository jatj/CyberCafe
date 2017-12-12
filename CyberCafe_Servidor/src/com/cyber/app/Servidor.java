/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber.app;

import java.awt.Component;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gearnest
 */
public class Servidor extends javax.swing.JFrame {

    /**
     * Creates new form Servidor
     */
    static String IP = "";
    public static int PUERTO = 1201;
    public static int PRECIO = 5;
    static ServerSocket ss;
    static ArrayList sockets = new ArrayList<Socket>();
    static Thread coneccionThread;
    static ArrayList computadoras = new ArrayList<Computadora>();
    Component[] comps;
    boolean foundIp = false;
    String ip, interfaceStr;
    JFrame frame;
    
    //Bitacora
    DefaultTableModel registrosModel;
    
    public Servidor() {
        frame = this;
        initComponents();
        //initServer();
        noConnection.setVisible(false);
        puertoText.setText(String.valueOf(PUERTO));
        precioText.setText(String.valueOf(PRECIO));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        equipoPanel = new javax.swing.JPanel();
        configPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        puertoText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        precioText = new javax.swing.JTextField();
        conectarBtn = new javax.swing.JToggleButton();
        noConnection = new javax.swing.JLabel();
        bitácoraPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrosTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        equipoPanel.setLayout(new java.awt.GridLayout(2, 4, 5, 5));

        jLabel1.setText("Puerto: ");

        puertoText.setText("jTextField1");

        jLabel2.setText("Precio por hora: ");

        precioText.setText("jTextField1");
        precioText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioTextActionPerformed(evt);
            }
        });

        conectarBtn.setText("Iniciar");
        conectarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conectarBtnMouseClicked(evt);
            }
        });
        conectarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(puertoText, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precioText, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(conectarBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(puertoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(precioText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(conectarBtn))
        );

        equipoPanel.add(configPanel);

        noConnection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noConnection.setText("No hay computadoras conectadas");
        equipoPanel.add(noConnection);

        jTabbedPane1.addTab("Equipos", equipoPanel);

        bitácoraPanel.setLayout(new java.awt.GridLayout(1, 0));

        registrosModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PC", "Inicio", "Fin", "Costo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        registrosTable.setModel(registrosModel);
        jScrollPane1.setViewportView(registrosTable);

        bitácoraPanel.add(jScrollPane1);

        jTabbedPane1.addTab("Bitácora", bitácoraPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void precioTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioTextActionPerformed

    private void conectarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conectarBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_conectarBtnMouseClicked

    private void conectarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarBtnActionPerformed
        // TODO add your handling code here:
        if(!isNumeric(puertoText.getText())){
            JOptionPane.showMessageDialog(this,
            "No es un puerto válido",
            "Error conectando",
            JOptionPane.ERROR_MESSAGE);
        }else{
             if(JOptionPane.showConfirmDialog(frame,
            "Estas seguro que deseas iniciar el servidor",
            "Iniciar CyberCafe",
            JOptionPane.YES_NO_OPTION) == 0){
                PUERTO = Integer.parseInt(puertoText.getText());
                System.out.println("Iniciando Server Puerto:" + PUERTO);
                Thread t = new Thread() {
                    public void run() {
                        initServer();
                    }
                };
                t.start();            
            }
        }       
    }//GEN-LAST:event_conectarBtnActionPerformed

    
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
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Servidor serv = new Servidor();
                
                serv.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                serv.setLocationRelativeTo(null);  
                serv.setVisible(true);
                
            }
        });
    }
    
    public void initServer(){
        // Obtiene información de las interfaces de red con ips
        try{
            ip = "<html>No hay computadoras conectadas, <br>porfavor asegurate que esten conecadas a alguna de las siguientes IPs.";
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)){
                foundIp = false;
                interfaceStr = "<br><br>Interfaz " + netint.getName() + ":";
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    foundIp = true;
                    interfaceStr += "<br>" + inetAddress;
                }
                if(foundIp)
                    ip += interfaceStr;
            }
            ip += "<html>";
            equipoPanel.remove(configPanel);
            noConnection.setText(ip);
            noConnection.setVisible(true);
        } catch (Exception e){}
        
        //Inicializa servidor
        try {
            ss = new ServerSocket(PUERTO);
        } catch (Exception e) {
        }
        Servidor s = this;
        computadoras = new ArrayList();
        sockets = new ArrayList();
        //Inicializa socket
        coneccionThread = new Thread(new Runnable() {
            public void run() {
                System.out.println("Corriendo Gestor de conexiones");
                while (computadoras.size() < 8) {
                    try {
                        // Servidor acepta conecciones
                        sockets.add(ss.accept());
                        System.out.println("Conección realizada");
                        computadoras.add(new Computadora(frame, s, (Socket) sockets.get(sockets.size() - 1), "PC - " + sockets.size(),sockets.size() - 1));
                        ((Computadora) computadoras.get(computadoras.size() - 1)).start();
                        // Inicializa componentes
                        ((Computadora) computadoras.get(computadoras.size() - 1)).setUpComponents();
                        // Manda bloquear
                        ((Computadora) computadoras.get(computadoras.size() - 1)).bloquea(true);
                    } catch (Exception e) {
                    }

                }
            }
        });
        coneccionThread.start();
    }
    
    public void eliminaComputadora(String nombre, int index){
        computadoras.remove(index);
        sockets.remove(index);

        equipoPanel.remove(comps[index]);
        equipoPanel.validate();
        equipoPanel.repaint();

        System.out.println("PC "+nombre+" eliminada");
        System.out.println("Cuenta "+computadoras.size());
        if(computadoras.isEmpty()){
            System.out.println("No hay conecciones");
            equipoPanel.add(noConnection);
            equipoPanel.validate();
            equipoPanel.repaint();
        }
        comps = equipoPanel.getComponents();
    }
    
    public void agregaPCPanel(javax.swing.JPanel PC){
        if(computadoras.size() == 1){
            equipoPanel.remove(noConnection);
            this.pack();
            this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            this.setLocationRelativeTo(null); 
        }
        
        equipoPanel.add(PC);
        equipoPanel.validate();

        equipoPanel.repaint();
        comps = equipoPanel.getComponents();
    }
    
    public void addRegistro(Registro reg){
        System.out.println("Agregando");
        List<String> list = new ArrayList<String>();

        list.add(reg.equipo);
        list.add(reg.inicio.toString());
        list.add(reg.fin.toString());
        list.add(String.valueOf(reg.dinero));
        DefaultTableModel model = (DefaultTableModel) registrosTable.getModel();
        
        model.addRow(list.toArray());
        //registrosTable.setModel(registrosModel);
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
    private javax.swing.JPanel bitácoraPanel;
    private javax.swing.JToggleButton conectarBtn;
    private static javax.swing.JPanel configPanel;
    private javax.swing.JPanel equipoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel noConnection;
    private static javax.swing.JTextField precioText;
    private static javax.swing.JTextField puertoText;
    private javax.swing.JTable registrosTable;
    // End of variables declaration//GEN-END:variables
}
