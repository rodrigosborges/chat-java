package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread{
    private ServerSocket servidor;
    private int porta;
            
    public Servidor(int porta){
        this.porta = porta;
    }
    
    @Override
    public void run(){
        try {
            servidor = new ServerSocket(porta);
            Socket cliente = servidor.accept();
            while (true) {
                PrintStream saida = new PrintStream(cliente.getOutputStream());
                Scanner entrada = new Scanner(cliente.getInputStream());
                String mensagem = entrada.nextLine();
                System.out.println("O cliente digitou: " + mensagem);
            }   
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
