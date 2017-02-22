package scheduling;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Created by phenicie on 2/11/2017.
 */
public class Login {
    static Locale l = Locale.getDefault();
     /* Testing Code for Local - l18n
    static Locale l = new Locale("fr");*/

    public static ResourceBundle bundle = getBundle("scheduling/Bundle", l);

    @FXML
    private TextField txtUsername; // value will be injected by the FXMLLoader
    @FXML
    private PasswordField txtPassword; // value will be injected by the FXMLLoader

    public void validateLogin(ActionEvent actionEvent ) throws SQLException, ClassNotFoundException {
        if (txtUsername.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, bundle.getString("invalid_username"));
        }else if (txtPassword.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, bundle.getString("invalid_password"));
        }else {
            String user = txtUsername.getText();
            String pass = txtPassword.getText();
            if (dbLogin( user, pass )) {
                showAlert( Alert.AlertType.INFORMATION, bundle.getString( "welcome_txt" ) );
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                try {
                    showDashboard( appStage );
                }catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                showAlert( Alert.AlertType.ERROR, bundle.getString( "invalid_credentials" ) );
            }
        }
    }

    private void showDashboard(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource( "view/dashboard.fxml" ));
        primaryStage.setTitle("Corp. Scheduling");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    private void showAlert(Alert.AlertType type, String message){
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText( message );
        alert.showAndWait();
    }

    private boolean dbLogin(String username, String pass) throws SQLException, ClassNotFoundException {
        Connection conn = dbConnect.getConnection();
        PreparedStatement pst;
        ResultSet rs;

        try {
            pst = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, pass);
            rs = pst.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}