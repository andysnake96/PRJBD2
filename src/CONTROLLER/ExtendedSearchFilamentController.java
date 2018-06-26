//andysnake RF6
package CONTROLLER;

import DAO.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExtendedSearchFilamentController {
    /*
    RF 6 AND RF8 Controller Class to wrap searchByBrightness and Search by region
     */
    public final static String RECTANGLE="RECTANGLE";
    public final static String CIRCLE="CIRCLE";

    public List<List<String>> searchFilamentByBrightnessAndEllipticity(double brightness, double ellipticity) throws SQLException {

        /*search filaments by Contrast(Brightness on extern boundary >(1-Contrast)%
              and by ellitpicity in range */
        double contrastForQuery=1+brightness;
        Connection  connection= Connection.getIstance();
        java.sql.Connection conn= connection.getConn();
        String sqlQuery=connection.getSqlString("filamentByContrastAndEllptcy");
        PreparedStatement stmt= conn.prepareStatement(sqlQuery);
        //stmt.setString();   //TODO SET PLACEHOLDER VALUES HERE!
        return null;
    }

    public List<List<String>> SearchFilamentByRegion (String regionKind) throws SQLException {

        /*search filaments by Contrast(Brightness on extern boundary >(1-Contrast)%
              and by ellitpicity in range */
        String sqlID=new String();
        if(regionKind.equals(RECTANGLE)) {
            sqlID="filamentByRectangle";
        }
        else if (regionKind.equals(CIRCLE)){
            sqlID ="filamentByCircle";
        }
        else
            throw new IllegalArgumentException("INVALID REGION KIND");

        Connection  connection= Connection.getIstance();
        java.sql.Connection conn= connection.getConn();
        String sqlQuery=connection.getSqlString(sqlID);
        PreparedStatement stmt= conn.prepareStatement(sqlQuery);
        //stmt.setString();   //TODO SET PLACEHOLDER VALUES HERE!
        return null;

    }
}
