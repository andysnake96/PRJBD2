package DAO;

import ENTITY.Star;
import feauture1.StarAndType;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class DAOStar {

    private static final String takeStars = "takestars";
    private static final String takeStarsInRectangle = "starinrectangle"

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

    /*
    restituisce le stelle all'interno di un rettangolo dove glatLeft e glatRight sono rispettivamente
    la latitudine più esterne a sinistra e destra, glonDown e glonUp sono invece le logitudini limite sopra e sotto del
    rettangolo. Tra l'altro conta anche il numero di stelle per ogni tipo
     */

    public static StarAndType takeStarInRectangle(double glatLeft, double glatRight, double glonDown, double glonUp) { //resituisce le stelle all'interno di un rettangol
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeStarsInRectangle);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, glatLeft);
        stmt.setDouble(2, glatRight);
        stmt.setDouble(3, glonDown);
        stmt.setDouble((4, glonUp));
        ResultSet rs = stmt.executeQuery();

        List<Star> stars = new ArrayList<>();
        HashMap<String, Integer> counters = new HashMap<>()
        while(rs.next()) {
            Star star = new Star();
            star.setId(rs.getInt("id"));
            star.setName(rs.getString("name"));
            star.setFlux(rs.getDouble("flux"));
            star.setGlat(rs.getDouble(("glat")));
            star.setGlon(rs.getDouble(("glon")));
            String starType = rs.getString("type");
            star.setType(starType);

            if (counters.containsKey(starType))
                counters.put(starType, counters.get(starType) + 1);//increment counter
            else
                this.counters.put(starType, 1);
            stars.add(star);
        }
        StarAndType starAndType = new StarAndType(stars, counters);

        conn.close();
        stmt.close();
        rs.close();
        return starAndType;
    }
}
