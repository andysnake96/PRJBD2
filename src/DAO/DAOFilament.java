package DAO;

import ENTITY.Filament;
import ENTITY.Instrument;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOFilament {

    private static final String searchFilamentByName = "searchfilamentbyname";
    private static final String searchFilamentById = "searchfilamentbyid";
    private static final String searchFilamentByRangeNSeg = "searchfilamentbyrangenseg";
    private static final String takeFilamentsInRectangle = "takefilamentsinrectangle";

    public static Filament searchFilamentByName(String name) throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(searchFilamentByName);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,name);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Filament filament = new Filament();
        filament.setName(rs.getString("name"));
        filament.setId(rs.getInt("id"));
        filament.setInstrument(new Instrument(rs.getString("nameStr")));
        filament.setnSeg(rs.getInt("nseg"));
        stmt.close();
        connection.closeConn(conn);
        return filament;
    }



    public static Filament searchFilamentById(int id, String nameStr) throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(searchFilamentById);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);
        stmt.setString(2, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        Filament filament = new Filament();
        filament.setName(rs.getString("name"));
        filament.setId(rs.getInt("id"));
        filament.setInstrument(new Instrument(rs.getString("nameStr")));
        filament.setnSeg(rs.getInt("nseg"));
        stmt.close();
        connection.closeConn(conn);
        return filament;
    }

    /*
    esegue la query che torna tutti i filamenti che hanno un numero di segmenti compresi in quel intervallo
     */

    public static List<Filament> searchByRangeNSeg(int nSegMin, int nSegMax) throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(searchFilamentByRangeNSeg);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, nSegMin);
        stmt.setInt(2, nSegMax);
        ResultSet rs = stmt.executeQuery();
        List<Filament> filaments = new ArrayList<>();
        while(rs.next()) {
            Filament filament = new Filament();
            filament.setName(rs.getString("name"));
            filament.setId(rs.getInt("id"));
            filament.setInstrument(new Instrument(rs.getString("nameStr")));
            filament.setnSeg(rs.getInt("nseg"));
            filament.setContrast(rs.getDouble("contrast"));
            filament.setDensAvg(rs.getDouble("densavg"));
            filament.setTempAvg(rs.getDouble("tempavg"));
            filament.setEllipticity(rs.getDouble("ellipticty"));
            filament.setFluxTot(rs.getDouble("fluxtot"));
            filaments.add(filament);

        }
        stmt.close();
        connection.closeConn(conn);
        rs.close();
        return filaments;
    }

    /*
    questa funzione esegue una query che restituisce tutti i filamenti.
     */
/*
    public static List<Filament> takeAllFilaments() throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeFilaments);
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Filament> filaments = new ArrayList<>();
        while (rs.next()) {
            Filament filament = new Filament();
            filament.setName(rs.getString("name"));
            filament.setId(rs.getInt("id"));
            filament.setInstrument(new Instrument(rs.getString("nameStr")));
            filament.setnSeg(rs.getInt("nseg"));
            filament.setContrast(rs.getDouble("contrast"));
            filament.setDensAvg(rs.getDouble("densavg"));
            filament.setTempAvg(rs.getDouble("tempavg"));
            filament.setEllipticity(rs.getDouble("ellipticty"));
            filament.setFluxTot(rs.getDouble("fluxtot"));
            filaments.add(filament);
        }
        stmt.close();
        connection.closeConn(conn);
        rs.close();
        return filaments;
    }
    */

    /*
    in questa funzione prendo i filamnti che hanno almeno un punto contorno all'interno di una regione rettangolare
     */

    public static List<Filament> takeFilamentsInRectangle(double glonLeft, double glonRight, double glatDown, double glatUp) throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeFilamentsInRectangle);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, glonLeft);
        stmt.setDouble(2, glonRight);
        stmt.setDouble(3, glatDown);
        stmt.setDouble(4, glatUp);

        ResultSet rs = stmt.executeQuery();

        List<Filament> filaments = new ArrayList<>();
        while (rs.next()) {
            Filament filament = new Filament();
            filament.setName(rs.getString("name"));
            filament.setId(rs.getInt("id"));
            filament.setInstrument(new Instrument(rs.getString("nameStr")));
            filament.setnSeg(rs.getInt("nseg"));
            filament.setContrast(rs.getDouble("contrast"));
            filament.setDensAvg(rs.getDouble("densavg"));
            filament.setTempAvg(rs.getDouble("tempavg"));
            filament.setEllipticity(rs.getDouble("ellipticty"));
            filament.setFluxTot(rs.getDouble("fluxtot"));
            filaments.add(filament);
        }

        stmt.close();
        connection.closeConn(conn);
        rs.close();
        return filaments;
    }

}
