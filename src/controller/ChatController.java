/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import threads.ClienteTeste;
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
    private ClienteTeste cliente = new ClienteTeste("127.0.0.1", 6666);
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void criarServidor(ActionEvent event) throws IOException{
        this.cliente.enviarMensagem(nome.getText());
    }
    
    @FXML
    private void entrarServidor(ActionEvent event) throws IOException{
        this.cliente.enviarMensagem(nome.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/imagens/logo.png");
        img.setImage(image);
//        new Servidor(6666).run();
        cliente.start();
    }   
    
}
