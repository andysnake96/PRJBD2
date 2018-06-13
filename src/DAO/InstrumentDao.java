package DAO;

import feauture1.Bean.InstrumentBean;

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

    }



