package threads;

import controller.ChatController;
import java.io.DataOutputStream;
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

public class ClienteThread{
    private String host;
    private int porta;
    private String mensagem = "";
    private ChatController controller;
    private PrintStream saida;

    public ClienteThread(String host, int porta, ChatController controller) {
        this.host = host;
        this.porta = porta;
        this.controller = controller;
    }

    public boolean executa() throws UnknownHostException, IOException {
        try{
            Socket cliente = new Socket(this.host, this.porta);
            // thread para receber mensagens do servidor
            Recebedor r = new Recebedor(cliente.getInputStream(),this);
            new Thread(r).start();

            // lê msgs do teclado e manda pro servidor
            this.saida = new PrintStream(cliente.getOutputStream());

            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public void enviarMensagem(String mensagem){
        saida.println(mensagem);
   }
    
    public synchronized void controleMensagem(String msg){
        switch(msg.split(":")[0]){
            case "login":
                controller.login(msg.split(":")[1]);
                break;
            case "lista_usuarios":
                controller.lista_usuarios(msg.split(":")[1]);
                break;
            case "transmitir":
                System.out.println(msg);
                controller.transmitir(msg.split(":",4)[1], msg.split(":",4)[2], msg.split(":",4)[3]);
                break;
        }
    }
}