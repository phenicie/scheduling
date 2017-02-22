package scheduling;

import javafx.scene.control.Alert;

import java.util.Map;

/**
 * Created by phenicie on 2/21/2017.
 */
public class helpers {
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    static void showAlert(Alert.AlertType type, String message){
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText( message );
        alert.showAndWait();
    }
}


