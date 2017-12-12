/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber.app;

import java.net.Socket;

/**
 *
 * @author Gearnest
 */
public class Conexiones implements Runnable {
    private Thread thread;
    private Servidor servidor;
    
    public Conexiones(Servidor serv) {
        servidor = serv;
    }    
    @Override
    public void run() {
        System.out.println("Corriendo gestor de conexiones");
        while(servidor.computadoras.size() < 8){
            try {
                // Servidor acepta conecciones
                servidor.sockets.add(servidor.ss.accept());
                System.out.println("Conectado");
                servidor.computadoras.add(new Computadora(servidor, (Socket) servidor.sockets.get(servidor.sockets.size() - 1), "PC - " + servidor.sockets.size()));
                ((Computadora) servidor.computadoras.get(servidor.computadoras.size() - 1)).start();
                ((Computadora) servidor.computadoras.get(servidor.computadoras.size() - 1)).bloquea();
            } catch (Exception e) {
            }
        }
    }
   
    public void start () {
        if (thread == null) {
            System.out.println("Iniciando gestor de conexiones");
            thread = new Thread ();
            thread.start ();
        }
   }
}
