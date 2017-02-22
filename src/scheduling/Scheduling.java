package scheduling; /**
 * Created by phenicie on 2/11/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Scheduling extends Application {

    public static void main(String[] args) {
        launch( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource( "view/login.fxml" ));
        primaryStage.setTitle("Corp. Scheduling");
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }
}
