package scheduling; /**
 * Created by phenicie on 2/11/2017.
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;

import static scheduling.helpers.showAlert;

public class Dashboard extends Application {
    public static ObservableList<Customer> customerData;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Button btnDeleteCust;

    @FXML // fx:id="btnAddCustomer"
    private Button btnAddCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnEditCustomer"
    private Button btnEditCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<Customer> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="columnCustomerId"
    private TableColumn<Customer, Integer> columnCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="columnCustomerName"
    private TableColumn<Customer, String> columnCustomerName; // Value injected by FXMLLoader

    @FXML // fx:id="columnCustomerPhone"
    private TableColumn<Customer, String> columnPhone; // Value injected by FXMLLoader

    @FXML // fx:id="columnAddressId"
    private TableColumn<Customer, Integer> columnAddressId; // Value injected by FXMLLoader

    @FXML // fx:id="columnAddress"
    private TableColumn<Customer, String> columnAddress; // Value injected by FXMLLoader

    @FXML // fx:id="columnAddress2"
    private TableColumn<Customer, String> columnAddress2; // Value injected by FXMLLoader

    @FXML // fx:id="columnCityId"
    private TableColumn<Customer, Integer> columnCityId; // Value injected by FXMLLoader

    @FXML // fx:id="columnCity"
    private TableColumn<Customer, String> columnCity; // Value injected by FXMLLoader

    @FXML // fx:id="columnPostalCode"
    private TableColumn<Customer, String> columnPostalCode; // Value injected by FXMLLoader

    @FXML // fx:id="columnCountryId"
    private TableColumn<Customer, Integer> columnCountryId; // Value injected by FXMLLoader

    @FXML // fx:id="columnCountry"
    private TableColumn<Customer, String> columnCountry; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert btnAddCustomer != null : "fx:id=\"btnAddCustomer\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCustomerId != null : "fx:id=\"columnCustomerId\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCustomerName != null : "fx:id=\"columnCustomerName\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnPhone != null : "fx:id=\"columnPhone\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnAddressId != null : "fx:id=\"columnAddressId\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnAddress != null : "fx:id=\"columnAddress\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnAddress2 != null : "fx:id=\"columnAddress2\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCityId != null : "fx:id=\"columnCityId\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCity != null : "fx:id=\"columnCity\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnPostalCode != null : "fx:id=\"columnPostalCode\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCountryId != null : "fx:id=\"columnCountryId\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert columnCountry != null : "fx:id=\"columnCountry\" was not injected: check your FXML file 'dashboard.fxml'.";

        columnCustomerId.setCellValueFactory( new PropertyValueFactory<>( "customerId" ) );
        columnCustomerName.setCellValueFactory( new PropertyValueFactory<>( "customerName" ) );
        columnPhone.setCellValueFactory( new PropertyValueFactory<>( "phone" ));
        columnAddressId.setCellValueFactory( new PropertyValueFactory<>( "addressId" ) );
        columnAddress.setCellValueFactory( new PropertyValueFactory<>("address"));
        columnAddress2.setCellValueFactory( new PropertyValueFactory<>("address2"));
        columnCityId.setCellValueFactory( new PropertyValueFactory<>( "cityId" ) );
        columnCity.setCellValueFactory( new PropertyValueFactory<>("city"));
        columnPostalCode.setCellValueFactory( new PropertyValueFactory<>("postalCode"));
        columnCountryId.setCellValueFactory( new PropertyValueFactory<>( "countryId" ) );
        columnCountry.setCellValueFactory( new PropertyValueFactory<>("country"));

        customerData = getCustomerData();

       try{
            tableView.setItems(customerData);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @FXML
    void showEditCustomer(ActionEvent event) {
        Stage primaryStage = null;
        final Parent root;
        final FXMLLoader fxmlLoader;
        try {
            Customer customer = tableView.getSelectionModel().getSelectedItems().get(0);
            if(customer == null){
                showAlert( Alert.AlertType.ERROR, "Please select a customer record first" );
            }else {
                fxmlLoader = new FXMLLoader( getClass().getResource( "view/addCustomer.fxml" ) );
                primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle( "Edit Customer Record" );
                // TODO Change LABEL to edit
                root = fxmlLoader.load();
                addCustomer controller = fxmlLoader.getController();
                controller.setTxtCustomerId( String.valueOf( customer.getCustomerId() ) );
                controller.setTxtCustomerName( customer.getCustomerName() );
                controller.setTxtAddress( customer.getAddress() );
                controller.setTxtAddress2( customer.getAddress2() );
                controller.setTxtCity( customer.getCity() );
                controller.setTxtPhone( customer.getPhone() );
                controller.setTxtPostalCode( customer.getPostalCode() );
                // GET ID For Customers Country to

                controller.setSelectCountry( customer.getCountry() );
                Scene addCustomerScene = new Scene( root, 800, 600 );
                primaryStage.setScene( addCustomerScene );
                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    @FXML
    void deleteCustomer(ActionEvent event) {
        ObservableList<Customer> customerSelected, allCustomers;
        try {
            customerSelected = tableView.getSelectionModel().getSelectedItems();
            if (customerSelected.size() == 0) {
                showAlert(Alert.AlertType.ERROR, "Please select a customer record first");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer");
                alert.setHeaderText("Aren you sure you want to delete this customer?");
                alert.setContentText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    allCustomers = tableView.getItems();
                    // TODO Combine
                    customerSelected.forEach(Customer::deleteCustomer);
                    customerSelected.forEach(allCustomers::remove);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    void showAddCustomer(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource( "view/addCustomer.fxml" ));
        Scene addCustomerScene = new Scene(root, 800, 600);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Add New Customer");
        primaryStage.setScene( addCustomerScene );
        primaryStage.show();

    }

    @FXML
    void showAddAppointment(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource( "view/addAppointment.fxml" ));
        Scene addAppointmentScene = new Scene(root, 800, 600);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Add New Customer Appointment");
        primaryStage.setScene( addAppointmentScene );
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch( args );
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource( "view/dashboard.fxml" ));
        primaryStage.setTitle("Corp. Scheduling");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    // Get Customer Records From Database --> Return ObeservableList for TableView
    public ObservableList<Customer> getCustomerData(){
        customerData = FXCollections.observableArrayList();
        try{
            Connection conn = dbConnect.connect();
            String sql = "Select cust.customerId as customerId, customerName, phone, cust.addressId as addressId, address, address2, a.cityId as cityId, city, postalCode, c.countryId as countryId, country from customer cust " +
                    "join address a on cust.addressId = a.addressId " +
                    "join city c on c.cityId = a.cityId " +
                    "join country ct on ct.countryId = c.countryId";
            ResultSet rs = conn.createStatement().executeQuery( sql );
            // Add Data
            while(rs.next()) {
                // Create Customer Objects from MYSQL Results
                customerData.add( new Customer(
                        rs.getInt( "customerId" ),
                        rs.getString( "customerName" ),
                        rs.getString( "phone" ),
                        rs.getInt("addressId"),
                        rs.getString( "address" ),
                        rs.getString( "address2" ),
                        rs.getInt("cityId"),
                        rs.getString( "city" ),
                        rs.getString( "postalCode" ),
                        rs.getInt("countryId"),
                        rs.getString( "country" )
                ) );
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        return customerData;
    }




}
