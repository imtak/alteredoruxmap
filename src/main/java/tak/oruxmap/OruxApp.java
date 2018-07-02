package tak.oruxmap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tak.oruxmap.ui.MainPane;


public class OruxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainPane mainPane = new MainPane(primaryStage);
        primaryStage.setTitle("Tak - Altered Oruxmap");
        primaryStage.setScene(new Scene(mainPane, 600, 400));
        primaryStage.show();
    }
}
