/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    @FXML private TextField nome, servidor, mensagem;
    @FXML private ListView<String> lista = new ListView<String>(),mensagens = new ListView<String>();
    @FXML private ArrayList<String> destinatarios = new ArrayList<String>();
    @FXML private Label destinatarioslist, msg1, msg2;
    @FXML private Button criar, conectar;
    private ClienteThread cliente;
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void criarServidor(ActionEvent event) throws IOException{
        try{
            new Servidor(6666).start();
            this.cliente = new ClienteThread("127.0.0.1", 6666, this);
            cliente.executa();
            this.cliente.enviarMensagem("login:"+nome.getText());
        }catch(Exception e){
            this.mensagem("ERRO","Não foi possível criar o servidor");
        }
    }
    
    @FXML
    private void entrarServidor(ActionEvent event) throws IOException{
        this.cliente = new ClienteThread(servidor.getText(), 6666, this);
        boolean rp = cliente.executa();
        if(rp){
            this.cliente.enviarMensagem("login:"+nome.getText());
        }else
            this.mensagem("ERRO","Não foi possível encontrar o servidor");
    }
    
    public void atualizaDestinatarios(){
        String todos = "";
        for(String destinatario : destinatarios)
            todos += todos.equals("") ? destinatario : ";"+destinatario;
        this.destinatarioslist.setText(todos);
    }
    
    public void login(String resultado){
        this.mensagem((resultado.equals("true") ? "SUCESSO" : "ERRO"),(resultado.equals("true") ? "O nome de usuário foi registrado com sucesso" : "O nome do usuário não pôde ser registrado pois é inválido ou já está em uso"));
        if(resultado.equals("true")){
            nome.setDisable(true);criar.setDisable(true);conectar.setDisable(true);servidor.setDisable(true);msg1.setDisable(true);msg2.setDisable(true);
        }
    }
    
    public void lista_usuarios(String clientes){
        lista.setItems(FXCollections.observableArrayList((";"+clientes).split(";")));
        styleUsuarios();
    }
    
    public void styleMensagens(){
        mensagens.setCellFactory(lv -> new ListCell<String>() {
            private final Label label = new Label();
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                }else{
                    label.setText(item);
                    label.setWrapText(true);
                    label.setMaxWidth(450);
                    label.setTextAlignment(TextAlignment.JUSTIFY);
                    setGraphic(label);
                    if(item.startsWith("| Para:"))
                        setStyle("-fx-alignment: CENTER-RIGHT");
                    else
                        setStyle("-fx-alignment: CENTER-LEFT");
                }
            }
        });
    }
    //mensagens.getItems().get(mensagens.getItems().size()-1
    
    public void styleUsuarios(){
        lista.setCellFactory(lv -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item.equals("")) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image("/imagens/online.png"));
                    setText(item);
                    setGraphic(imageView);
                }
            }
        });
    }
    
    public void transmitir(String remetente, String destinatario, String msg){
        mensagens.getItems().add("| De: "+remetente+" | Para: "+destinatario+" | : "+msg);
        this.styleMensagens();
        URL som = getClass().getResource("/imagens/msn.wav");
        AudioClip audio = Applet.newAudioClip(som);
        audio.play();
    }
    
    @FXML
    private void enviarMensagem(ActionEvent event) throws IOException{
        if(!destinatarioslist.getText().equals("") && !mensagem.getText().equals("")){
            this.cliente.enviarMensagem("mensagem:"+this.destinatarioslist.getText()+":"+mensagem.getText());
            this.mensagens.getItems().add("| Para: "+this.destinatarioslist.getText()+" | : "+mensagem.getText());
            mensagem.setText("");
            this.destinatarios.clear();
            this.destinatarios.add("*");
            atualizaDestinatarios();
            this.styleMensagens();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/imagens/logo.png");
        img.setImage(image);
        this.destinatarios.add("*");
        this.atualizaDestinatarios();
        lista.setOnMousePressed(e -> {
            String item = this.lista.getSelectionModel().getSelectedItem();
            if(this.destinatarios.contains("*") && !nome.getText().equals(item) && item != null){             
                this.destinatarios.clear();
                this.destinatarios.add(item);
                this.atualizaDestinatarios();
            }else{
                if(!destinatarios.contains(item) && !nome.getText().equals(item) && item != null){
                    if(destinatarios.contains("*"))
                        this.destinatarios.clear();
                    this.destinatarios.add(item);   
                    this.atualizaDestinatarios();
                }else if(destinatarios.contains(item)){
                    this.destinatarios.remove(item);
                    if(this.destinatarios.isEmpty())
                        this.destinatarios.add("*");
                    this.atualizaDestinatarios();
                }
            }
        });
    }   
    
    public void shutdown() {
        System.exit(0);
    }
    
    public void mensagem(String titulo, String texto){
        Platform.runLater(()-> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/Mensagem.fxml"));
            fxmlLoader.getNamespace().put("labelText", texto);
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle(titulo);
                scene.getStylesheets().add("/view/Main.css");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
}
