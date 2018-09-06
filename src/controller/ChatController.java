/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import threads.ClienteThread;
import threads.Servidor;

/**
 * FXML Controller class
 *
 * @author a1700651
 */
public class ChatController implements Initializable {

    @FXML private ImageView img;
    @FXML private TextField nome;
    @FXML private TextField servidor;
    @FXML private TextField mensagem;  
    @FXML private ListView<String> lista = new ListView<String>();
    private ClienteThread cliente;
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void criarServidor(ActionEvent event) throws IOException{
        new Servidor(6666).start();
        this.cliente = new ClienteThread("127.0.0.1", 6666, this);
        cliente.start();
        this.cliente.enviarMensagem("login:"+nome.getText());
    }
    
    @FXML
    private void entrarServidor(ActionEvent event) throws IOException{
        this.cliente = new ClienteThread(servidor.getText(), 6666, this);
        cliente.start();
        this.cliente.enviarMensagem(nome.getText());
    }
    
    public void login(String resultado){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/Mensagem.fxml"));
            fxmlLoader.getNamespace().put("labelText", (resultado.equals("true") ? "O nome de usuário foi registrado com sucesso" : "O nome do usuário não pôde ser registrado pois é inválido ou já está em uso"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle(resultado.equals("true") ? "SUCESSO" : "ERRO");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void lista_usuarios(String clientes){
        lista.setItems(FXCollections.observableArrayList(clientes.split(";")));
    }
    
    public void transmitir(String remetente, String destinatario, String msg){
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/imagens/logo.png");
        img.setImage(image);
    }   
    
}
