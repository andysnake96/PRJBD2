package DAO;

import feauture1.Bean.SatelliteBean;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SatelliteDao {

    private static final String insertSatellite = "insertsatellite";
    private static final String insertPartecipation ="insertpartecipation";

    public  static String addSatellite(SatelliteBean satelliteBean) {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String msx = null;
        try {
            String sql = connection.getSqlString(insertSatellite);

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, satelliteBean.getName());

            stmt.setDate(2, Date.valueOf(satelliteBean.getStartDate()));
            if(satelliteBean.getEndDate() == null) {
                stmt.setString(3, "not ended");
                stmt.setDate(4, null);
            }
            else {
                stmt.setString(3, "ended");
                stmt.setDate(4, Date.valueOf(satelliteBean.getEndDate()));
            }
            stmt.executeUpdate();
            for(String agency : satelliteBean.getAgencies()) {
                sql = connection.getSqlString(insertPartecipation);
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,agency);
                stmt.setString(2,satelliteBean.getName());
                stmt.executeUpdate();
            }
            connection.closeConn(conn);
            stmt.close();
        }
     catch (SQLException se2) {
        msx = se2.getMessage();
        return msx;
    }
        msx = "insert valid";
        return msx;

}


}

