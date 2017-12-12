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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author Gearnest
 */
final class Registro { 
    public String equipo;
    public Date inicio;
    public Date fin;
    public float dinero;
    
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
}

public class Computadora implements Runnable {
    
    private Thread thread;
    private Servidor servidor;
    private Socket socket;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;
    
    public String nombrePC;
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
    

    public Computadora(Servidor serv, Socket s, String nombre) {
        servidor = serv;
        socket = s;
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
                    System.out.println(msgIn);
                } catch (Exception e) {
                }
            }
        }catch(Exception e){}
        System.out.println("Thread " + nombrePC + " terminando.");
        servidor.eliminaComputadora(nombrePC);
    }
   
    public void start () {
        if (thread == null) {
            System.out.println("Iniciando " +  nombrePC );
            thread = new Thread (this, nombrePC);
            thread.start ();
        }
   }
    
    public void bloquea(){
        try{
            String msgout = "Bloquea";
            streamOut.writeUTF(msgout);
        }catch(Exception e){}
    }
    
    public void desBloquea(){
        try{
            String msgout = "Desbloquea";
            streamOut.writeUTF(msgout);
        }catch(Exception e){}
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

        tiempoPCText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tiempoPCText.setText("00:00:00");

        bloquearBtn.setText("Bloquear");

        noDisponibleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDisponibleLabel.setText("No disponible");
        noDisponibleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
}
