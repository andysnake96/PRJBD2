package DAO;

import ENTITY.Filament;
import ENTITY.Outline;
import ENTITY.Point;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPoint {

    private static final String computeCentroide = "computecentroide";
    private static final String computeExstension = "computeexstension";
    private static final String takeOutline = "takeoutline";

    public static Point computeCentroide(int idFil, String nameStr) throws SQLException {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        Point centroide = new Point();
        String sql = connection.getSqlString(computeCentroide);
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return centroide;
        }
        centroide.setLat(rs.getDouble(1));
        centroide.setGlon(rs.getDouble(2));
        stmt.close();
        rs.close();
        return centroide;

    }

    public static Point[] searchPointMaxMin(int idFil, String nameStr) throws SQLException {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        Point points[] = new Point[2];
        Point pointMax = new Point();  //points[0] -> max point points[1] -> min, si parla di cordinate non di un vero e proprio punto
        Point pointMin = new Point();
        String sql = connection.getSqlString(computeExstension);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }

        pointMax.setGlon(rs.getDouble("maxglon"));
        pointMax.setLat(rs.getDouble("maxglat"));
        pointMin.setGlon(rs.getDouble("minglon"));
        pointMin.setLat(rs.getDouble("minglat"));
        points[0] = pointMax;
        points[1] = pointMin;
        stmt.close();
        rs.close();
        return points;

    }

    public static Outline takeOutline(int idFil, String nameStr) throws SQLException { //ritorna tutta la lista di punti del controno di un determinato filamento
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeOutline);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet resultSet =  stmt.executeQuery();

        List<Point> outline = new ArrayList<>();
        while(resultSet.next()) {
            Point point = new Point();
            point.setGlon(resultSet.getDouble("glon"));
            point.setLat(resultSet.getDouble("glat"));
            outline.add(point);
        }
        stmt.close();
        resultSet.close();
        return new Outline(outline);
    }
}




