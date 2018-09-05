package threads;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTeste extends Thread{
    private String host;
    private int porta;
    private String mensagem = null;

    public ClienteTeste(String host, int porta) {
        this.host = host;
        this.porta = porta;
    }

    public void executa() throws UnknownHostException, IOException {
        
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conextou ao servidor: " + this.host);

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream());
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        
        while (true) {
            if(mensagem == null){} else {
                if(mensagem.equals("sair")){
                    saida.println(mensagem);
                    saida.close();
                    cliente.close();
                }else{
                    saida.println(mensagem);
                    mensagem = null;
                }
            }
        }
    }
    
    public void enviarMensagem(String mensagem){
        this.mensagem = mensagem;
    }

    public void run() {
        try {
            this.executa();
        } catch (IOException ex) {
            Logger.getLogger(ClienteTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}