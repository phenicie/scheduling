package scheduling;

import java.sql.Connection;
import javafx.beans.property.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scheduling.dbConnect.closeRef;
import static scheduling.dbConnect.getConnection;

/**
 * Created by phenicie on 2/13/2017.
 */
public class Customer {
    public static Map<Integer, String> customerMap = new HashMap<>(); //List of Customers generated for GUI Combo Boxes


    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty phone;
    private final SimpleIntegerProperty addressId;
    private final SimpleStringProperty address;
    private final SimpleStringProperty address2;
    private final SimpleIntegerProperty cityId;
    private final SimpleStringProperty city;
    private final SimpleStringProperty postalCode;
    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty country;


    public Customer(Integer customerId, String name, String phone, Integer addressId, String address, String address2, Integer cityId, String city, String postalCode, Integer countryId, String country) {
        this.customerId = new SimpleIntegerProperty( customerId );
        this.customerName = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty( phone );
        this.addressId = new SimpleIntegerProperty( addressId );
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.cityId = new SimpleIntegerProperty( cityId );
        this.city = new SimpleStringProperty( city );
        this.postalCode = new SimpleStringProperty( postalCode );
        this.countryId = new SimpleIntegerProperty( countryId );
        this.country = new SimpleStringProperty( country );
    }

    public void deleteCustomer(){
        System.out.println("Delete customer function hit!!");
        Connection conn = getConnection();
        java.sql.PreparedStatement pst = null;

        System.out.println("Customer is : "+ this.getCustomerName());
        try{
            pst = conn.prepareStatement("DELETE from address where addressId = ?");
            pst.setInt(1, this.getAddressId() );
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeRef(pst);
        }

        try{
            pst = conn.prepareStatement("DELETE from customer where customerId = ?");
            pst.setInt(1, this.getCustomerId());
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeRef(pst);
        }

    }
    public void addToDB(String user) {
        Connection conn = getConnection();
        java.sql.PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            pst = conn.prepareStatement( "INSERT into customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                    "VALUES( ?, ?, ?, 1, now(), ?, now(), ?)");
            pst.setInt(1, this.getCustomerId());
            pst.setString(2, this.getCustomerName());
            pst.setInt(3, this.getAddressId());
            pst.setString(4, user);
            pst.setString(5, user);
            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            closeRef(pst);
        }
    }
    @Override
    public String toString(){
        return "ID : "+this.getCustomerId()+"Name : "+this.getCustomerName().toString();
    }
    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress2() {
        return address2.get();
    }

    public SimpleStringProperty address2Property() {
        return address2;
    }

    public int getAddressId() {
        return addressId.get();
    }

    public SimpleIntegerProperty addressIdProperty() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId.set( addressId );
    }

    public int getCityId() {
        return cityId.get();
    }

    public SimpleIntegerProperty cityIdProperty() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId.set( cityId );
    }

    public int getCountryId() {
        return countryId.get();
    }

    public SimpleIntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId.set( countryId );
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public SimpleStringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }


}

