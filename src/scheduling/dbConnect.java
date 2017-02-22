package scheduling;

import java.sql.*;

/**
 * Created by phenicie on 2/13/2017.
 */
public class dbConnect {
    private static Connection conn;
    private static String url = "jdbc:mysql://52.206.157.109:3306/U03QFw";
    private static String user = "U03QFw";
    private static String pass = "53688050999";

    public static Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch (ClassNotFoundException cnfe){
            System.err.println("Error : "+cnfe.getMessage());
        }catch ( InstantiationException ie){
            System.err.println("Error : "+ie.getMessage());
        }catch (IllegalAccessException iae){
            System.err.println("Error : "+ iae.getMessage());
        }

        try{
            conn = DriverManager.getConnection(url, user, pass);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;

    }
    public static Connection getConnection() {
        try{
            if(conn !=null && !conn.isClosed())
                return conn;
            connect();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeRef(PreparedStatement ps, ResultSet rs){
        try{
            ps.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void closeRef(PreparedStatement ps){
        try{
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
