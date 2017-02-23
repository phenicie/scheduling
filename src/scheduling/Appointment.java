package scheduling;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static scheduling.dbConnect.closeRef;
import static scheduling.dbConnect.getConnection;

/**
 * Created by phenicie on 2/21/2017.
 */
public class Appointment {
    static Connection conn = getConnection();
    static java.sql.PreparedStatement pst = null;
    static ResultSet rs = null;
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private Customer customer;
    private final SimpleIntegerProperty appointmentId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty url;
    private final SimpleObjectProperty start;
    private final SimpleObjectProperty end;


    public Appointment(Customer customer, Integer appointmentId, Integer customerId, String title, String description, String location, String contact, String url, Timestamp start, Timestamp end) {
        // TODO Save Reminder Details
        this.customer = customer;
        this.appointmentId = new SimpleIntegerProperty( appointmentId );
        //this.customerId = new SimpleIntegerProperty( customerId );
        this.title = new SimpleStringProperty( title );
        this.description = new SimpleStringProperty( description );
        this.location = new SimpleStringProperty( location );
        this.contact = new SimpleStringProperty( contact );
        this.url = new SimpleStringProperty( url );
        this.start = new SimpleObjectProperty( start );
        this.end = new SimpleObjectProperty( end );

    }

    public Appointment(){
        this.appointmentId = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.description= new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.contact = new SimpleStringProperty();
        this.url= new SimpleStringProperty();
        this.start= new SimpleObjectProperty();
        this.end = new SimpleObjectProperty();
    }

    public void sqlUpdateAppointment(Boolean newAppointment, String user){
        if(newAppointment){
            // This is a new Appointment INSERT to DB
            try{
                pst = conn.prepareStatement("INSERT into appointment (customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy, appointmentId)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?)");
                pst.setInt(1, this.getCustomer().getCustomerId());
                pst.setString(2, this.getTitle() );
                pst.setString(3, this.getDescription() );
                pst.setString(4, this.getLocation() );
                pst.setString(5, this.getContact() );
                pst.setString(6, this.getUrl() );
                pst.setTimestamp(7, this.getStart() );
                pst.setTimestamp(8, this.getEnd() );
                pst.setString(9, user);
                pst.setString(10, user);
                pst.setInt(11, this.getAppointmentId());

                pst.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                closeRef(pst);
            }
        }else{
            // This is an existing Appointment UPDATE into DB
            try{
                pst = conn.prepareStatement("UPDATE appointment set customerId = ?, title=?, description=?, location=?, contact=?, url=?, start=?, end=?, lastUpdate=now(), lastUpdateBy=? WHERE appointmentId = ?");
                pst.setInt(1, this.getCustomer().getCustomerId());
                pst.setString(2, this.getTitle() );
                pst.setString(3, this.getDescription() );
                pst.setString(4, this.getLocation() );
                pst.setString(5, this.getContact() );
                pst.setString(6, this.getUrl() );
                pst.setTimestamp(7, Timestamp.from(this.getStart().toInstant()) );
                pst.setTimestamp(8, Timestamp.from(this.getEnd().toInstant()) );
                pst.setInt(1, this.getAppointmentId());
                pst.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                closeRef(pst);
            }
        }
    }


    // TODO Build Observable List of Appointments for Table
    public ObservableList<Appointment> getAppointments(String interval){
        try{
            Connection conn = dbConnect.connect();
            String sql = "Select * from appointment";
            ResultSet rs = conn.createStatement().executeQuery( sql );
            // Add Data
            while(rs.next()) {
                Integer appointmentId = rs.getInt("appointmentId");
                Integer customerId = rs.getInt("customerId");
                // TODO GET Customer OBJECT from ID
                Customer customer = Customer.customerMap.get(customerId);
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String url = rs.getString("url");
                Timestamp start = rs.getTimestamp("start");
                Timestamp end = rs.getTimestamp("end");
                // Create Customer Objects from MYSQL Results
                appointments.add( new Appointment(customer, appointmentId, customerId, title, description, location, contact, url, start, end ) );
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        return appointments;
    }


    public static Integer getAppointmentMaxId(){
        Connection conn = getConnection();
        java.sql.PreparedStatement pst = null;
        ResultSet rs = null;
        Integer appointmentId = null;
        try {
            pst = conn.prepareStatement( "SELECT MAX(appointmentId) as appointmentId FROM appointment" );
            rs = pst.executeQuery();
            if(rs.next()){
                appointmentId =rs.getInt( "appointmentId" );
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeRef(pst, rs);

        }
        return ++appointmentId;
    }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer ; }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public SimpleIntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set( appointmentId );
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        System.out.println( "Title was sent as : "+title);
        this.title.set( title );
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set( description );
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set( location );
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set( contact );
    }

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set( url );
    }

    public Timestamp getStart() {
        return (Timestamp) start.get();
    }

    public SimpleObjectProperty startProperty() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start.set( start );
    }

    public Timestamp getEnd() {
        return (Timestamp) end.get();
    }

    public SimpleObjectProperty endProperty() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end.set( end );
    }

}
