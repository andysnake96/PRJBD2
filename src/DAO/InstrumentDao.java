package DAO;

import BEAN.InstrumentBean;

import java.sql.*;

public class InstrumentDao {

    private static final String insertInstrument = "insertinstrument";

    public static String addInstrument(InstrumentBean instrumentBean) {
        Statement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String msx = null;
        try {
            String sql = connection.getSqlString(insertInstrument);
            sql += instrumentBean.getName() + "','"+ instrumentBean.getSatellite() +"','" +instrumentBean.getBandString()
                    + "')";


            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.closeConn(conn);
        }
        catch (SQLException se2) {
            msx = se2.getMessage();
            return msx;
        }
        msx = "insert valid";
        return msx;

    }


    public static String takeSatelliteFromInstrument(String nameStr) throws Exception {
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString("takesatellitefrominstrument");
        PreparedStatement stmt =  conn.prepareStatement(sql);
        stmt.setString(1, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            throw new IllegalArgumentException("SATELLITE RELATIVO ALLO STRUMENTO NON TROVATO");
        }
        String nameSatellite = rs.getString("satellite");
        stmt.close();
        connection.closeConn(conn);
        rs.close();
        return nameSatellite;

    }
    }



