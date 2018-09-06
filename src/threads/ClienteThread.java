package threads;

import controller.ChatController;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClienteThread extends Thread{
    private String host;
    private int porta;
    private String mensagem = null;
    private ChatController controller;

    public ClienteThread(String host, int porta, ChatController controller) {
        this.host = host;
        this.porta = porta;
        this.controller = controller;
    }

    public void executa() throws UnknownHostException, IOException {
        
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conextou ao servidor: " + this.host);

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream(),this);
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        
        while (true) {
            if(mensagem != null){
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
    
    public void controleMensagem(String msg){
        switch(msg.split(":")[0]){
            case "login":
                controller.login(msg.split(":")[1]);
                break;
            case "lista_usuarios":
                controller.lista_usuarios(msg.split(":")[1]);
                break;
            case "transmitir":
                controller.transmitir(msg.split(":")[1], msg.split(":")[2], msg.split(":")[3]);
                break;
        }
    }

    public void run() {
        try {
            this.executa();
        } catch (IOException ex) {
            Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}