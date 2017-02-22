package scheduling;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by phenicie on 2/21/2017.
 */
public class Appointment {
    private final SimpleIntegerProperty appointmentId;
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty url;
    private final SimpleStringProperty start;
    private final SimpleStringProperty end;


    public Appointment(Integer appointmentId, Integer customerId, String title, String description, String location, String contact, String url, String start, String end) {
        this.appointmentId = new SimpleIntegerProperty( appointmentId );
        this.customerId = new SimpleIntegerProperty( customerId );
        this.title = new SimpleStringProperty( title );
        this.description = new SimpleStringProperty( description );
        this.location = new SimpleStringProperty( location );
        this.contact = new SimpleStringProperty( contact );
        this.url = new SimpleStringProperty( url );
        this.start = new SimpleStringProperty( start );
        this.end = new SimpleStringProperty( end );

    }

// TODO Build Class to match DATABASE



    public ObservableList<Appointment> getAppointments(String interval){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        // TODO Pass Interval (by Week, or by Month) Return Appointments


        return appointments;
    }


    public int getAppointmentId() {
        return appointmentId.get();
    }

    public SimpleIntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set( appointmentId );
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set( customerId );
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
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

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set( start );
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set( end );
    }

}
