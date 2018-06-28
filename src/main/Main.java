package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = 
           FXMLLoader.load(getClass().getResource("/vista/Project.fxml"));
        if(root==null)
            System.out.println("FALLASTE");
        
        Scene scene = new Scene(root);
        stage.setTitle("Intranet");
        stage.setScene(scene);
        stage.show();
        
    }
    
   public static void main(String[] args) {
      launch(args);
   }
}