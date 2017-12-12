/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

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

    public Computadora(Servidor serv, Socket s, String nombre) {
        servidor = serv;
        socket = s;
        try {
            streamIn = new DataInputStream(socket.getInputStream());
            streamOut = new DataOutputStream(socket.getOutputStream());
        }catch(Exception e){} 
        
        nombrePC = nombre;
        System.out.println("Creando " +  nombrePC );
    }    
    
    @Override
    public void run() {
        System.out.println("Corriendo " +  nombrePC );
        try{
            while (!msgIn.equals("Desconecta")) {
                try {
                    msgIn = streamIn.readUTF();
                    System.out.println(msgIn);
                } catch (Exception e) {
                }
            }
        }catch(Exception e){}
        System.out.println("Thread " +  nombrePC + " terminando.");
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
}
