/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber.app;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gearnest
 */
final class Registro { 
    public String equipo;
    public Date inicio;
    public Date fin;
    public float dinero;

    public Registro(String equipo, Date inicio, float dinero) {
        this.equipo = equipo;
        this.inicio = inicio;
        this.dinero = dinero;
    }
    
    public float tiempo(char type){
        float diff = -1;
        switch(type){
            case 's':
                diff = (fin.getTime() - inicio.getTime())/1000;
                break;
            case 'm':
                diff = (fin.getTime() - inicio.getTime())/(1000*60);
                break;
            case 'h':
                diff = (fin.getTime() - inicio.getTime())/(1000*60*60);
                break;
        }
        return diff;
    }
    
    public String tiempoStr(){
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return String.format("%s:%s:%s", decimalFormat.format(tiempo('h')), decimalFormat.format(tiempo('m')), decimalFormat.format(tiempo('s')));
    }
}

public class Computadora implements Runnable {
    
    private Thread thread, t;
    private Servidor servidor;
    private Socket socket;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    
    public JFrame frame;
    public String nombrePC;
    public int index;
    public boolean estatus;
    public String msgIn;
    public Registro registroActual;
    public ArrayList registros;
    
    public javax.swing.JPanel PC;
    public javax.swing.JButton bloquearBtn;
    public javax.swing.JLabel computadoraLabel;
    public javax.swing.JButton desbloquearBtn;
    public javax.swing.JLabel noDisponibleLabel;
    public javax.swing.JTextField tiempoPCText;
    
    String status = "bloqueado";
    

    public Computadora(JFrame f, Servidor serv, Socket s, String nombre, int i) {
        frame = f;
        servidor = serv;
        socket = s;
        index = i;
        try {
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
        }catch(Exception e){} 
        
        nombrePC = nombre;
        System.out.println("Creando " +  nombrePC );
        
        // Grafico
        PC = new javax.swing.JPanel();
        computadoraLabel = new javax.swing.JLabel();
        desbloquearBtn = new javax.swing.JButton();
        tiempoPCText = new javax.swing.JTextField();
        bloquearBtn = new javax.swing.JButton();
        noDisponibleLabel = new javax.swing.JLabel();
    }    
    
    @Override
    public void run() {
        System.out.println("Corriendo " +  nombrePC );
        msgIn = "Conectado";
        try{
            while (!msgIn.equals("Desconecta")) {
                try {
                    msgIn = streamIn.readUTF();
                    if(msgIn.equals("Bloquea")){
                        bloquea2();
                    }
                    System.out.println(msgIn);
                } catch (Exception e) {
                }
            }
        }catch(Exception e){}
        System.out.println("Thread " + nombrePC + " terminando.");
        if(t.isAlive()){
            t.stop();
            JOptionPane.showMessageDialog(null,
            "El cliente se ha desconectado, deteniendo hilos.",
            "Bloqueado",
            JOptionPane.INFORMATION_MESSAGE);
        }
        servidor.eliminaComputadora(nombrePC, index);
    }
   
    public void start () {
        if (thread == null) {
            System.out.println("Iniciando " +  nombrePC );
            thread = new Thread (this, nombrePC);
            thread.start ();
        }
   }
    
    public void bloquea(boolean notify){
        try{
            String msgout = "Bloquea";
            if(notify)
                streamOut.writeUTF(msgout);
            desbloquearBtn.setVisible(true);
            bloquearBtn.setVisible(false);
            tiempoPCText.setVisible(false);
        }catch(Exception e){}
    }
    
    public void bloquea2(){
        try{
            String msgout = "Bloquea2";
            streamOut.writeUTF(msgout);
            desbloquearBtn.setVisible(true);
            bloquearBtn.setVisible(false);
            tiempoPCText.setVisible(false);
            if(t.isAlive()){
                t.stop();
                JOptionPane.showMessageDialog(null,
                "El cliente ha decidido bloquearse, serian $"+servidor.PRECIO+" pesos.",
                "Bloqueado",
                JOptionPane.INFORMATION_MESSAGE);
                registroActual.dinero = registroActual.tiempo('m') * servidor.PRECIO;
                if(registroActual.tiempo('m') == 0){
                    registroActual.dinero = servidor.PRECIO;
                }
                servidor.addRegistro(registroActual);
            }
        }catch(Exception e){}
    }
    
    
    public void desbloquear(java.awt.event.ActionEvent evt){
        String str = JOptionPane.showInputDialog(frame, "Dame el tiempo de uso en minutos", "Desbloquear", JOptionPane.PLAIN_MESSAGE);
        if(isNumeric(str)){
            int mins = Integer.parseInt(str);
            if(mins>0){
                try {
                    String msgout = "Desbloquea";
                    streamOut.writeUTF(msgout);
                    streamOut.writeUTF(str);
                    streamOut.writeUTF(String.valueOf(servidor.PRECIO));
                    desbloquearBtn.setVisible(false);
                    bloquearBtn.setVisible(true);
                    tiempoPCText.setVisible(true);
                    status = "desbloqueado";
                    registroActual = new Registro(nombrePC, new Date(), 0);
                    t = new Thread() {
                        public void run() {
                            registroActual.fin = new Date();
                            while (status.equals("desbloqueado") && registroActual.tiempo('m') < mins) {
                                registroActual.fin = new Date();
                                tiempoPCText.setText(registroActual.tiempoStr());
                            }
                            System.out.println(servidor.PRECIO);
                            System.out.println(registroActual.tiempo('m'));
                            registroActual.dinero = registroActual.tiempo('m') * servidor.PRECIO;
                            if(registroActual.tiempo('m') == 0){
                                registroActual.dinero = servidor.PRECIO;
                            }
                            
                            servidor.addRegistro(registroActual);
                            JOptionPane.showMessageDialog(frame,
                            "Se ha terminado el tiempo de desbloqueo, tiene un costo de $" + registroActual.dinero +" pesos, presione de nuevo desbloquear",
                            "Tiempo terminado",
                            JOptionPane.INFORMATION_MESSAGE);
                            bloquea(false);
                        }
                    };
                    t.start();
                } catch (Exception e) {
                }
            }else{
                JOptionPane.showMessageDialog(frame,
                "Los minutos tienen que ser mayores a 0",
                "Error desbloqueando",
                JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(frame,
            "No es un numero valido",
            "Error desbloqueando",
            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void bloquear(java.awt.event.ActionEvent evt){
        if(JOptionPane.showConfirmDialog(frame,
        "Estas seguro que deseas bloquear la computadora: " + nombrePC,
        "Bloquear",
        JOptionPane.YES_NO_OPTION) == 0){
            status = "bloqueado";
            try {
                String msgout = "Bloquea";
                streamOut.writeUTF(msgout);
                desbloquearBtn.setVisible(true);
                bloquearBtn.setVisible(false);
                tiempoPCText.setVisible(false);
                registros.add(registroActual);
                registroActual.dinero = registroActual.tiempo('m') * servidor.PRECIO;
                if(registroActual.tiempo('m') == 0){
                    registroActual.dinero = servidor.PRECIO;
                }
                servidor.addRegistro(registroActual);
            } catch (Exception e) {
            }
        }
    }
    
    public void setUpComponents(){
        System.out.println("Configurando el PC");
        PC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        computadoraLabel.setBackground(new java.awt.Color(255, 255, 255));
        computadoraLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Image imgPC =new javax.swing.ImageIcon(getClass().getResource("/com/cyber/imagenes/pc.png")).getImage();
        ImageIcon iconPC = new ImageIcon(imgPC.getScaledInstance(150,150,Image.SCALE_SMOOTH));
        computadoraLabel.setIcon(iconPC);
        computadoraLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        desbloquearBtn.setText("Desbloquear");
        desbloquearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                   desbloquear(evt);
            }
        });
        

        tiempoPCText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tiempoPCText.setText("00:00:00");
        tiempoPCText.setVisible(false);

        bloquearBtn.setText("Bloquear");
        bloquearBtn.setVisible(false);
        bloquearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                   bloquear(evt);
            }
        });

        noDisponibleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDisponibleLabel.setText("No disponible");
        noDisponibleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        noDisponibleLabel.setVisible(false);

        javax.swing.GroupLayout PC1Layout = new javax.swing.GroupLayout(PC);
        PC.setLayout(PC1Layout);
        PC1Layout.setHorizontalGroup(
            PC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PC1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(computadoraLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tiempoPCText, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(bloquearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(desbloquearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(noDisponibleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PC1Layout.setVerticalGroup(
            PC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PC1Layout.createSequentialGroup()
                .addComponent(computadoraLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(tiempoPCText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bloquearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desbloquearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noDisponibleLabel))
        );
        servidor.agregaPCPanel(PC);
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
}
