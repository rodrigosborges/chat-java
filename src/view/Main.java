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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Chat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add("/view/Main.css");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/imagens/chat.png"));
        stage.setTitle("CHAT");
        stage.setResizable(false);
        
        //chama a função que finaliza a thread quando fecha o programa
        ChatController controller = loader.getController();
        stage.setOnHidden(e -> controller.shutdown());
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}