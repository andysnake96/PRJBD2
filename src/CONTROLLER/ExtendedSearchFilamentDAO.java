//andysnake RF6
package CONTROLLER;

import DAO.Connection;
import DAO.DAOFilament;
import ENTITY.Filament;
import ENTITY.Instrument;
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
    public static List<Filament> searchFilamentByBrightnessAndEllipticity
    (double brightness, double[] ellipticityRange) throws SQLException {

        /*search filaments by Contrast(Brightness on extern boundary >(1-Contrast)%
              and by ellitpicity in range  */

        List<Filament> out = new ArrayList<>();
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
            Filament filament= new Filament();

            filament.setName(resultSet.getString("name"));
            filament.setId(resultSet.getInt("id"));
            filament.setInstrument(new Instrument(resultSet.getString("namestr")));
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
    public static List<Filament> searchFilamentByCircle(double glat, double glon, double radius) throws SQLException {

        List<Filament> out = new ArrayList<>();
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
            Filament filament= new Filament();
            filament.setFluxTot(resultSet.getDouble("fluxtot"));
            filament.setEllipticity(resultSet.getDouble("ellipticty"));
            filament.setContrast(resultSet.getDouble("contrast"));
            filament.setDensAvg(resultSet.getDouble("densavg"));
            filament.setName(resultSet.getString("name"));
            filament.setId(resultSet.getInt("id"));
            filament.setInstrument(new Instrument(resultSet.getString("namestr")));
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

    public static List<Filament> searchFilamentByRectangle(double glat, double glon, double height, double width) throws SQLException {
        List<Filament> out = new ArrayList<>();
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

            Filament filament= new Filament();

            filament.setName(resultSet.getString("name"));

            filament.setEllipticity(resultSet.getDouble("ellipticty"));
            filament.setContrast(resultSet.getDouble("contrast"));
            filament.setDensAvg(resultSet.getDouble("densavg"));
            filament.setTempAvg(resultSet.getDouble("tempavg"));
            filament.setId(resultSet.getInt("id"));
            filament.setInstrument(new Instrument(resultSet.getString("namestr")));
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

    public static void main(String[] args) throws SQLException {
        List<Filament> out0 = ExtendedSearchFilamentDAO.searchFilamentByBrightnessAndEllipticity(0.2, new double[]{1.1, 3});
        List<Filament> out1 = ExtendedSearchFilamentDAO.searchFilamentByRectangle(-0.00288061079447488, 11, 2, 2);

        List<Filament> out2 = ExtendedSearchFilamentDAO.searchFilamentByCircle(-0.00288061079447486, 11, 2);
        System.err.println("ciaoGalli");
    }
}
