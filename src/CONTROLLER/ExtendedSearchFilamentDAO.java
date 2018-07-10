//andysnake RF6
package CONTROLLER;

import DAO.Connection;
import BEAN.InfoFilament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//RB Y<- GLAT ;; X<- GLON..
public class ExtendedSearchFilamentDAO {
    /*
    RF 6 AND RF8 Controller Class to wrap searchByBrightness and Search by region
     */
    public final static String RECTANGLE = "RECTANGLE";
    public final static String CIRCLE = "CIRCLE";

    //RF6
    public static List<InfoFilament> searchFilamentByBrightnessAndEllipticity
    (double brightness, double[] ellipticityRange) throws SQLException {

        /*search filaments by Contrast(Brightness on extern boundary >(1-Contrast)%
              and by ellitpicity in range  */

        List<InfoFilament> out = new ArrayList<>();
        Connection connection = Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sqlQuery = connection.getSqlString("filamentByContrastAndEllptcy");
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setDouble(1, brightness);
        stmt.setDouble(2, ellipticityRange[0]);
        stmt.setDouble(3, ellipticityRange[1]);
        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next()) {
            System.out.println("empty result  ");
            return null;
        }

        do {
            InfoFilament filament= new InfoFilament();

            filament.setName(resultSet.getString("name"));
            filament.setId(resultSet.getInt("id"));
            filament.setNameStr( resultSet.getString("namestr"));
            filament.setnSeg(resultSet.getInt("nseg"));
            filament.setFluxTot(resultSet.getDouble("fluxtot"));
            filament.setEllipticity(resultSet.getDouble("ellipticty"));
            filament.setContrast(resultSet.getDouble("contrast"));
            filament.setDensAvg(resultSet.getDouble("densavg"));
            filament.setTempAvg(resultSet.getDouble("tempavg"));

            out.add(filament);

        }
        while (resultSet.next());
        resultSet.close();
        connection.closeConn(conn);
        stmt.close();
        return out;
    }

    //RF8 -CIRCLE
    public static List<InfoFilament> searchFilamentByCircle(double glat, double glon, double radius) throws SQLException {

        List<InfoFilament> out = new ArrayList<>();
        Connection connection = Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sqlQuery = connection.getSqlString("filamentByCircle");
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setDouble(1, glat);
        stmt.setDouble(2, glon);
        stmt.setDouble(3, radius);

        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next()) {
            System.out.println("empty result   ");
            return null;
        }

        do {
            InfoFilament filament= new InfoFilament();
            filament.setFluxTot(resultSet.getDouble("fluxtot"));
            filament.setEllipticity(resultSet.getDouble("ellipticty"));
            filament.setContrast(resultSet.getDouble("contrast"));
            filament.setDensAvg(resultSet.getDouble("densavg"));
            filament.setName(resultSet.getString("name"));
            filament.setId(resultSet.getInt("id"));
            filament.setNameStr(resultSet.getString("namestr"));
            filament.setnSeg(resultSet.getInt("nseg"));

            filament.setTempAvg(resultSet.getDouble("tempavg"));

            out.add(filament);

        }
        while (resultSet.next());
        resultSet.close();
        connection.closeConn(conn);
        stmt.close();
        return out;
    }


    //RF8 -RECTANGLE

    public static List<InfoFilament> searchFilamentByRectangle(double glat, double glon, double height, double width) throws SQLException {
        List<InfoFilament> out = new ArrayList<>();
        Connection connection = Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sqlQuery = connection.getSqlString("filamentByRectangle");
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setDouble(1, glat);
        stmt.setDouble(2, height / 2);
        stmt.setDouble(3, glon);
        stmt.setDouble(4, width / 2);

        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next()) {
            System.out.println("empty result");
            return null;
        }
        do {

            InfoFilament filament= new InfoFilament();

            filament.setName(resultSet.getString("name"));

            filament.setEllipticity(resultSet.getDouble("ellipticty"));
            filament.setContrast(resultSet.getDouble("contrast"));
            filament.setDensAvg(resultSet.getDouble("densavg"));
            filament.setTempAvg(resultSet.getDouble("tempavg"));
            filament.setId(resultSet.getInt("id"));
            filament.setNameStr(resultSet.getString("namestr") );
            filament.setnSeg(resultSet.getInt("nseg"));
            filament.setFluxTot(resultSet.getDouble("fluxtot"));

            out.add(filament);


        }

        while (resultSet.next());
        connection.closeConn(conn);
        resultSet.close();
        stmt.close();
        return out;
    }

}
