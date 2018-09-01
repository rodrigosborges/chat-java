package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread{
    
    String ip;
    int porta = 6666;
    Socket cliente;
    
    public Cliente(String ip) throws IOException{
        this.ip = ip;
    }
    
    public void enviarMensagem(String mensagem){
        try{
            PrintStream saida=new PrintStream(cliente.getOutputStream());
            saida.println(mensagem);
        }catch(IOException e){}
    }
    
    @Override
    public void run(){
        Scanner teclado = new Scanner(System.in);
        try {
            this.cliente = new Socket(this.ip, this.porta);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintStream saida;
        try {
            saida = new PrintStream(cliente.getOutputStream());
        
     
            String mensagem = "";
            while (true) {
                System.out.print("Digite algo a ser enviado ao servidor: ");
                mensagem = teclado.nextLine();
                saida.println(mensagem);
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
