//andysnake RF6
package CONTROLLER;

import DAO.Connection;
import DAO.DAOFilament;
import ENTITY.Filament;
import feauture1.Bean.Rectangle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//RB Y<- GLAT ;; X<- GLON..
public class ExtendedSearchFilamentDAO {
    /*
    RF 6 AND RF8 Controller Class to wrap searchByBrightness and Search by region
     */
    public final static String RECTANGLE = "RECTANGLE";
    public final static String CIRCLE = "CIRCLE";

    //RF6
    public static List<List<String>> searchFilamentByBrightnessAndEllipticity
    (double brightness, double[] ellipticityRange) throws SQLException {

        /*search filaments by Contrast(Brightness on extern boundary >(1-Contrast)%
              and by ellitpicity in range  */

        List<List<String>> out = new ArrayList<>();
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

            /* output example
                      name           |   id    |  fluxtot   | ellipticty | contrast |  densavg   | tempavg | namestr | nseg
-------------------------+---------+------------+------------+----------+------------+---------+---------+------
 HiGALFil349.1335+0.1059 | 1020476 | 5.2336e+25 |    2.17391 |   2.1209 | 1.0775e+22 |  19.676 | SPIRE   |    5

             */
            List<String> queryRowOutput = new ArrayList<>();

            queryRowOutput.add(resultSet.getString("name"));
            queryRowOutput.add(String.valueOf(resultSet.getInt("id")));
            queryRowOutput.add(resultSet.getString("namestr"));
            queryRowOutput.add(String.valueOf(resultSet.getInt("nseg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("fluxtot")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("ellipticty")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("contrast")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("densavg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("tempavg")));

            out.add(queryRowOutput);

        }
        while (resultSet.next());

        return out;
    }

    //RF8 -CIRCLE
    public static List<List<String>> searchFilamentByCircle(double glat, double glon, double radius) throws SQLException {

        List<List<String>> out = new ArrayList<>();
        Connection connection = Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sqlQuery = connection.getSqlString("filamentByCircle");
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setDouble(1, glat);
        stmt.setDouble(2, radius);
        stmt.setDouble(3, glon);
        stmt.setDouble(4, radius);

        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next()) {
            System.out.println("empty result   ");
            return null;
        }

        do {
            List<String> queryRowOutput = new ArrayList<>();

            queryRowOutput.add(String.valueOf(resultSet.getInt("id")));
            queryRowOutput.add(resultSet.getString("namestr"));
            queryRowOutput.add(resultSet.getString("name"));
            queryRowOutput.add(String.valueOf(resultSet.getInt("nseg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("fluxtot")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("ellipticty")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("densavg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("tempavg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("contrast")));

            out.add(queryRowOutput);

        }
        while (resultSet.next());
        return out;
    }

    public static List<List<String>> searchFilamentBySquare(double glat, double glon, double side) throws Exception {

        List<List<String>> out= new ArrayList<>();

        Rectangle r = new Rectangle();
        r.setGlat(glat);
        r.setGlon(glon);
        r.setH(side);
        r.setL(side);
        List<Filament> filaments = DAOFilament.takeFilamentsInRectangle(glon - side, glon + side, glat - side, glat + side);
        Iterator<Filament> it = filaments.iterator();
        while (it.hasNext()) {
            List<String> filamentFields= new ArrayList<>();
            Filament filament= it.next();
            //TODO ADD MAPPING
            out.add(filamentFields);
        }
        return out;
    }

        //RF8 -RECTANGLE

    public static List<List<String>> searchFilamentByRectangle(double glat, double glon, double height, double width) throws SQLException {
        List<List<String>> out = new ArrayList<>();
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

            List<String> queryRowOutput = new ArrayList<>();

            queryRowOutput.add(String.valueOf(resultSet.getInt("id")));
            queryRowOutput.add(resultSet.getString("namestr"));
            queryRowOutput.add(resultSet.getString("name"));
            queryRowOutput.add(String.valueOf(resultSet.getInt("nseg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("fluxtot")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("ellipticty")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("contrast")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("densavg")));
            queryRowOutput.add(String.valueOf(resultSet.getDouble("tempavg")));

            out.add(queryRowOutput);


        }

        while (resultSet.next());
        return out;
    }

}
