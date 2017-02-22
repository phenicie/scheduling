package scheduling;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by phenicie on 2/15/2017.
 */
public class Country {
    //static Map<Integer, String> countryMap = new TreeMap<>();
    //countryMap.put(rs.getInt("countryId"), rs.getString("country"));

    public static Map<Integer, String> countryMap = new HashMap<>();

    private final SimpleStringProperty country;

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set( country );
    }

    public Country(String country){
        this.country = new SimpleStringProperty(country);
    }

    @Override
    public String toString(){
        return this.getCountry();
    }

    public static List getCountries() {
        List<Country> countryList = new ArrayList<>();
        Integer i=0;
        try {
            Connection conn = dbConnect.connect();
            String sql = "SELECT countryId, country FROM country";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            // Add Data
            while (rs.next()){
                countryList.add( new Country( rs.getString( "country" ) ) );
                countryMap.put(i++, rs.getString( "country" ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryList;
    /* TODO

    ** Use static list to populate country drop down on add customer
    */
    }
}
