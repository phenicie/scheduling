package scheduling;

/**
 * Created by phenicie on 2/21/2017.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static scheduling.helpers.showAlert;
import static scheduling.helpers.timeSelections;
import static scheduling.helpers.timesList;

public class addAppointment {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAppointmentId"
    private TextField txtAppointmentId; // Value injected by FXMLLoader

    @FXML // fx:id="txtTitle"
    private TextField txtTitle; // Value injected by FXMLLoader

    @FXML // fx:id="txtDescription"
    private TextArea txtDescription; // Value injected by FXMLLoader

    @FXML // fx:id="selectCustomer"
    private ComboBox<Customer> selectCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="txtLocation"
    private TextField txtLocation; // Value injected by FXMLLoader

    @FXML // fx:id="txtContact"
    private TextField txtContact; // Value injected by FXMLLoader

    @FXML // fx:id="txtUrl"
    private TextField txtUrl; // Value injected by FXMLLoader

    @FXML // fx:id="dateStart"
    private DatePicker dateStart; // Value injected by FXMLLoader

    @FXML // fx:id="dateEnd"
    private DatePicker dateEnd; // Value injected by FXMLLoader

    @FXML
    private ComboBox<String> selectStartTime;

    @FXML
    private ComboBox<String> selectEndTime;

    @FXML
    void addAppointment(ActionEvent event) {

    }

    /*
        Setters to intialize form from other classes

     */
    public void setTxtAppointmentId(String txtAppointmentId) {
        this.txtAppointmentId.setText( txtAppointmentId );
    }
    public void setTxtTitle(String  txtTitle) {
        this.txtTitle.setText( txtTitle );
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription.setText( txtDescription );
    }

    public void setSelectCustomer(Customer selectCustomer) {
        // TODO Change Customer for EDIT Function
        // Integer index = (Integer) helpers.getKeyFromValue(Country.countryMap, txtCountry);
        //System.out.println("Country map index returned = "+index);

        Integer index = 0;
        this.selectCustomer.getSelectionModel().select( index );

    }

    public void setTxtLocation(String  txtLocation) {
        this.txtLocation.setText( txtLocation );
    }

    public void setTxtContact(String  txtContact) {
        this.txtContact.setText( txtContact );
    }

    public void setTxtUrl(String  txtUrl) {
        this.txtUrl.setText( txtUrl );
    }

    public void setDateStart(String  dateStart) {
        //this.dateStart.setText( dateStart );
    }

    public void setDateEnd(String  dateEnd) {
        //this.dateEnd.setText( dateEnd );
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAppointmentId != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert txtTitle != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert selectCustomer != null : "fx:id=\"selectCustomer\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert txtLocation != null : "fx:id=\"txtLocation\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert txtContact != null : "fx:id=\"txtContact\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert txtUrl != null : "fx:id=\"txtUrl\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert dateStart != null : "fx:id=\"dateStart\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert dateEnd != null : "fx:id=\"dateEnd\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert selectStartTime != null : "fx:id=\"selectStartTime\" was not injected: check your FXML file 'addAppointment.fxml'.";
        assert selectEndTime != null : "fx:id=\"selectEndTime\" was not injected: check your FXML file 'addAppointment.fxml'.";

        this.selectCustomer.getItems().clear();
        //Customer.getCustomers(); // Populate Customer Map
        //List<String> customers = Customer.customerMap.entrySet().stream().map( x->x.getValue()).collect( Collectors.toList());// Get Countries as List
        this.selectCustomer.getItems().addAll( Dashboard.customerData ); // Add list to ComboBox
        this.selectStartTime.getItems().addAll(helpers.timeSelections );
        this.selectEndTime.getItems().addAll(helpers.timeSelections );

        System.out.println("loop customer list");
        for(Customer cust:Dashboard.customerData){
            System.out.println("Customer is "+cust);
        }

    }
    @FXML
    void validateAppointment(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (txtTitle.getText().isEmpty()) {
            showAlert( Alert.AlertType.ERROR, "Invalid Title" );
        }else if (txtDescription.getText().isEmpty()){
            showAlert( Alert.AlertType.ERROR, "Please enter a short description of the meeting" );
        }else if ( txtLocation.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid Meeting Location" );
        }else if ( txtContact.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid Contact" );
        }else if (txtUrl.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid URL" );
        }else if (dateStart.getValue() == null ){
            showAlert( Alert.AlertType.ERROR, "Invalid Start Date" );
        }else if (dateEnd.getValue() == null ){
            showAlert( Alert.AlertType.ERROR, "Invalid End Date" );
        }else if ( selectCustomer.getValue() == null ){
            showAlert( Alert.AlertType.ERROR, "Invalid Customer Selection" );
        }else{
            String appointmentId = txtAppointmentId.getText();
            String title = txtTitle.getText();
            String description = txtDescription.getText();
            String location = txtLocation.getText();
            String contact = txtContact.getText();
            String url = txtUrl.getText();
            // TODO Pare Datetime/Adjust for Timezone
            String startDate = dateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //java.sql.Date sqlDate =java.sql.Date.valueOf(datepicker.getValue());
            String endDate = dateEnd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String customer = selectCustomer.getValue().toString();
            System.out.println("All strings entered - call Create new Appointment");
            System.out.println("User timezone id is :" + TimeZone.getDefault().toZoneId());
            System.out.println("User timezone is :" + ZoneId.systemDefault());
            // TODO Parse Date/Time fields --> Convert to UTC
            // TODO Retrieve customerId from selection
            // TODO Create Appointment Function ** Use Lambda



        }



    }

    @FXML
    void cancelAddAppointment(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Function to return user to dashboard view
        try{
            Parent root = FXMLLoader.load(getClass().getResource( "view/dashboard.fxml" ));
            Scene dashboard = new Scene(root, 800, 600);
            primaryStage.setTitle("Corp. Scheduling");
            primaryStage.setScene( dashboard );
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }




}