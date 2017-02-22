/**
 * Sample Skeleton for 'addCustomer.fxml' Controller Class
 */

package scheduling;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.mysql.jdbc.PreparedStatement;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static scheduling.dbConnect.closeRef;
import static scheduling.dbConnect.getConnection;
import static scheduling.helpers.showAlert;


public class addCustomer {
    public static List<Customer> customerList = new ArrayList<>();

    static Connection conn = getConnection();
    static java.sql.PreparedStatement pst = null;
    static ResultSet rs = null;
    // TODO Adjust user based on login
    static String user = "phenicie";

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCustomerId"
    private TextField txtCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustomerName"
    private TextField txtCustomerName; // Value injected by FXMLLoader

    @FXML // fx:id="txtPhone"
    private TextField txtPhone; // Value injected by FXMLLoader

    @FXML // fx:id="txtAddress"
    private TextField txtAddress; // Value injected by FXMLLoader

    @FXML // fx:id="txtAddress2"
    private TextField txtAddress2; // Value injected by FXMLLoader

    @FXML // fx:id="txtCity"
    private TextField txtCity; // Value injected by FXMLLoader

    @FXML // fx:id="txtPostalCode"
    private TextField txtPostalCode; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSubmit"
    private Button btnSubmit; // Value injected by FXMLLoader

    @FXML // fx:id="selectCountry"
    private ChoiceBox<String> selectCountry; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCustomerName != null : "fx:id=\"txtCustomerName\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert txtPhone != null : "fx:id=\"txtPhone\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert txtAddress2 != null : "fx:id=\"txtAddress2\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert txtPostalCode != null : "fx:id=\"txtPostalCode\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert btnCancel != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert btnSubmit != null : "fx:id=\"btnSubmit\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert selectCountry != null : "fx:id=\"selectCountry\" was not injected: check your FXML file 'addCustomer.fxml'.";

        this.selectCountry.getItems().clear();
        Country.getCountries(); // Populate Country Map
        List<String> countries = Country.countryMap.entrySet().stream().map(x->x.getValue()).collect(Collectors.toList());// Get Countries as List
        this.selectCountry.getItems().addAll( countries ); // Add list to ComboBox

    }

    /*
    Setters to initiate fields from other controllers
     */
    public void setTxtCustomerId( String txtCustomerId){
        this.txtCustomerId.setText( txtCustomerId );
    }
    public void setTxtCustomerName(String txtCustomerName) {
        this.txtCustomerName.setText( txtCustomerName );
    }

    public void setTxtPhone(String txtPhone) {
        this.txtPhone.setText( txtPhone );
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress.setText( txtAddress );
    }

    public void setTxtAddress2(String txtAddress2) {
        this.txtAddress2.setText( txtAddress2 );
    }

    public void setTxtCity(String txtCity) {
        this.txtCity.setText( txtCity );
    }

    public void setTxtPostalCode(String txtPostalCode) { this.txtPostalCode.setText( txtPostalCode ); }

    public void setSelectCountry(String txtCountry) {
        Integer index = (Integer) helpers.getKeyFromValue(Country.countryMap, txtCountry);
        System.out.println("Country map index returned = "+index);
        this.selectCountry.getSelectionModel().select( index );
    }


    @FXML
    void cancelAddCustomer(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showDashboard(primaryStage);
    }


    @FXML
    void validateCustomer(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (txtCustomerName.getText().isEmpty()) {
            showAlert( Alert.AlertType.ERROR, "Invalid Customer Name" );
        }else if (txtPhone.getText().isEmpty()){
            showAlert( Alert.AlertType.ERROR, "Invalid Phone Number" );
        }else if ( txtAddress.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid Address" );
        }else if ( txtCity.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid City" );
        }else if (txtPostalCode.getText().isEmpty() ){
            showAlert( Alert.AlertType.ERROR, "Invalid Postal Code" );
        }else if ( selectCountry.getValue() == null ){
            showAlert( Alert.AlertType.ERROR, "Invalid Country Selection" );
        }else{
            String customerId = txtCustomerId.getText();
            String name = txtCustomerName.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String address2 = txtAddress2.getText();
            String city = txtCity.getText();
            String postalCode = txtPostalCode.getText();
            String country = selectCountry.getValue().toString();
            System.out.println("All strings entered - call Create new Customer");
            // Create new customer record
            createCustomer( customerId, name, phone, address, address2, city, postalCode, country );


             Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             showDashboard(primaryStage);
        }



    }


    static void createCustomer(String id, String name, String phone, String address, String address2, String city, String postalCode, String country) throws SQLException, ClassNotFoundException {
        if(id != null && !id.isEmpty()){
            // This Customer already exists -- Update existing records.

            Integer addressId = getExistingCustomerAddress( Integer.parseInt( id ));
            Integer cityId = getExistingCustomerCity( addressId );
            System.out.println("Existing cityId = "+cityId);
            Integer countryId = getCountryId(country);
            System.out.println("Existing countryId = "+countryId);
            updateCustomer(id, name);
            updateCustomerAddress(addressId, address, address2, postalCode, phone);
            updateCustomerCity(cityId, countryId, city);
        }else{
            // New Customer - Get MAX ID and Create new Record
            Integer countryId = getCountryId(country);
            Integer cityId = getCityId(city, countryId);
            Integer addressId = addAddress(address, address2, cityId, postalCode, phone);
            Integer customerId = getCustomerId();
            Customer customer = new Customer( customerId, name, phone, addressId, address, address2, cityId, city, postalCode, countryId, country );
            customer.addToDB( user );
        }
        // TODO Test make sure customer was created successfully
    }

    private static void updateCustomerAddress(Integer addressId, String address, String address2, String postalCode, String phone){
        try{
            pst = conn.prepareStatement("UPDATE address set address=?, address2=?, postalCode=?, phone=? where addressId = ?");
            pst.setString(1, address);
            pst.setString(2, address2);
            pst.setString(3, postalCode);
            pst.setString(4, phone);
            pst.setInt(5, addressId);
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeRef(pst);
        }
    }

    public static void updateCustomerCity(Integer cityId, Integer countryId, String city){
        try{
            pst = conn.prepareStatement("UPDATE city set city=?, countryId=? where cityId = ?");
            pst.setString(1, city);
            pst.setInt(2, countryId);
            pst.setInt(3, cityId);
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeRef(pst);
        }

    }

    private static Integer getExistingCustomerAddress(Integer customerId){
        Integer addressId=null;
        try {
            pst = conn.prepareStatement( "SELECT addressId FROM customer WHERE customerId=?" );
            pst.setInt( 1, customerId );
            rs = pst.executeQuery();
            if(rs.next()){
                addressId =rs.getInt( "addressId" );
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeRef(pst, rs);
        }

        return addressId;
    }
    private static Integer getExistingCustomerCity(Integer addressId){
        Integer cityId=null;
        try {
            pst = conn.prepareStatement( "SELECT cityId FROM address WHERE addressId=?" );
            pst.setInt( 1, addressId );
            rs = pst.executeQuery();
            if(rs.next()){
                cityId =rs.getInt( "cityId" );
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeRef(pst, rs);
        }

        return cityId;
    }
    private static void updateCustomer(String id, String name){
        try {
            pst = conn.prepareStatement( "UPDATE customer set customerName = ?, lastUpdate=now(), lastUpdateBy=? where customerId=? and customerName != ?");
            pst.setString(1, name);
            pst.setString( 2, user );
            pst.setInt(3, Integer.parseInt( id ));
            pst.setString(4, name);
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeRef(pst, rs);
        }

    }

    private static Integer getCountryId(String country){
        Integer countryId = null;
        // GET COUNTRY ID
        try {
            pst = conn.prepareStatement( "SELECT countryId FROM country WHERE lower(country)=?" );
            pst.setString( 1, country.trim().toLowerCase() );
            rs = pst.executeQuery();
            if(rs.next()){
                countryId =rs.getInt( "countryId" );
            }
            // TODO Set default if no ID is returned

        }catch (Exception e){

            e.printStackTrace();
        }finally {
            closeRef(pst, rs);

        }

        return countryId;

    }

    private static Integer getCityId(String city, Integer countryId) {
        Integer cityId = null;
        // GET CITY ID
        try {
            // City does not exist returning New cityId
            pst = conn.prepareStatement("SELECT max(cityId) AS cityId FROM city");
            rs = pst.executeQuery();
            if (rs.next()) {
                cityId = (rs.getInt("cityId")) + 1;  // Create new cityId
            }
            // INSERT NEW City
            try {
                pst = conn.prepareStatement("INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                        "VALUES( ?, ?, ?, now(), ?, now(), ?) ");
                pst.setInt(1, cityId);
                pst.setString(2, city);
                pst.setInt(3, countryId);
                pst.setString(4, user);
                pst.setString(5, user);
                pst.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cityId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityId;
    }


    private static Integer addAddress(String address, String address2, Integer cityId, String postalCode, String phone){
        Integer addressId = null;
        System.out.println(" addAddress Started for address " + address);
        // GET NEW ADDRESS ID
        try{
            pst = conn.prepareStatement( "select max(addressId) as addressid from address" );
            rs = pst.executeQuery();
            if(rs.next()){
                addressId = (rs.getInt( "addressid" ))+1;
            }
            //TODO Handle if nothing is returned

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            closeRef(pst, rs);
        }

        // Add Address Record using new ID
        // INSERT ADDRESS
        try{
            pst = conn.prepareStatement( "INSERT into address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                    "VALUES( ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)");
            pst.setInt(1, addressId);
            pst.setString(2, address);
            pst.setString(3, address2);
            pst.setInt(4, cityId);
            pst.setString(5, postalCode);
            pst.setString(6, phone);
            pst.setString(7, user);
            pst.setString(8, user);
            pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            closeRef(pst, rs);
        }

        return addressId;
    }

    private static Integer getCustomerId(){
        /*
        Query Database to find highest customerID and Return ID+1
         */
        Integer customerId = null;
        try{
            pst=conn.prepareStatement( "select max(customerId) as customerId from customer" );
            rs = pst.executeQuery(  );
            if(rs.next()){
                customerId = (rs.getInt( "customerId" ))+1 ;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            closeRef(pst, rs);
        }
        return customerId;
    }

    // Function to return user to dashboard view
    void showDashboard(Stage primaryStage){
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
