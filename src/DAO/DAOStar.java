package DAO;

import ENTITY.Star;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOStar {

    private static final String takeStars = "takestars";

    public static List<Star> takeAllStars() throws SQLException {

        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeStars);
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Star> stars = new ArrayList<>();
        while(rs.next()) {
            Star star = new Star();
            star.setId(rs.getInt("id"));
            star.setName(rs.getString("name"));
            star.setFlux(rs.getDouble("flux"));
            star.setGlat(rs.getDouble(("glat")));
            star.setGlon(rs.getDouble(("glon")));
            star.setType(rs.getString("type"));
            stars.add(star);
        }

        conn.close();
        stmt.close();
        rs.close();
        return stars;


    }
}
