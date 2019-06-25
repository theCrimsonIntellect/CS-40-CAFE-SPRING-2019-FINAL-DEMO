package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    // this is the main document
    @Override
    public void start(Stage primaryStage) throws Exception{

        // this loads the sample.fxml file to root
        // sample.fxml contains elements from the scene builder (table, chairs, score card, place mat, messaging)
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root,1240,800);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        // launches the javaFX application
        launch(args);
    }
}
