package view;

import controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/Chat.fxml"));
        
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add("/view/Main.css");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/imagens/chat.png"));
        stage.setTitle("CHAT");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}