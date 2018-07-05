package DAO;

import ENTITY.Filament;
import ENTITY.Outline;
import ENTITY.Point;
import ENTITY.PointSkeleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPoint {

    private static final String computeCentroide = "computecentroide";
    private static final String computeExstension = "computeexstension";
    private static final String takeOutline = "takeoutline";
    private static final String queryVertexUpper = "queryvertexupper";
    private static final String queryVertexLower = "queryvertexlower";

    /*
            questa funziona esegue una query in cui ritorna un punto che ha come cordiate la media delle latitutidini e
            longitudini di tutti punti del contorno del filamento
     */

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
            return null;
        }
        centroide.setLat(rs.getDouble(1));
        centroide.setGlon(rs.getDouble(2));
        stmt.close();
        connection.closeConn(conn);
        rs.close();
        return centroide;

    }

    /*
    questa funzione esegue una query dove ottiene le posizioni massime e minime longitudinali e latitudinali

     */

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
        connection.closeConn(conn);
        rs.close();

        return points;

    }
    /*
    questa funzione esegue una query per prendere tutti i punti del contorno del filamento.
     */

    public static Outline takeOutline(int idFil, String nameStr) throws SQLException {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeOutline);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        Outline outline = takeOutline(stmt);
        connection.closeConn(conn);
        stmt.close();

        return outline;
    }

    public static Outline takeOutline(PreparedStatement stmt) throws SQLException {
        ResultSet resultSet =  stmt.executeQuery();

        List<Point> outline = new ArrayList<>();
        while(resultSet.next()) {
            Point point = new Point();
            point.setGlon(resultSet.getDouble("glon"));
            point.setLat(resultSet.getDouble("glat"));
            outline.add(point);
        }
        resultSet.close();
        return new Outline(outline);
    }



    /*
    prende il punto del segmente con numero progressivo più alto.
     */

    public static Point takeVertexUpper(int idFil, String nameStr) throws SQLException {  //torna i vertici del filamento
        PreparedStatement stmt;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();

        Point pointNMax = new Point();

        String sql = connection.getSqlString(queryVertexUpper);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }

        pointNMax.setGlon(rs.getDouble("glon"));
        pointNMax.setLat(rs.getDouble("glat"));

        connection.closeConn(conn);
        stmt.close();
        rs.close();
        return pointNMax;

    }

    /*
    prende il punto del segmento del filamento considerato con il numero progressivo più basso
     */

    public static Point takeVertexLower(int idFil, String nameStr) throws SQLException {
        PreparedStatement stmt;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();

        Point pointNMin = new Point();

        String sql = connection.getSqlString(queryVertexLower);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }

        pointNMin.setGlon(rs.getDouble("glon"));
        pointNMin.setLat(rs.getDouble("glat"));

        connection.closeConn(conn);
        stmt.close();
        rs.close();
        return pointNMin;

    }

    public static List<PointSkeleton> takeFilamentSpine(int idFil,String nameStr) throws SQLException {
        List<PointSkeleton> out = new ArrayList<>();
        Filament filament = DAOFilament.searchFilamentById(idFil, nameStr);
        Connection connection = Connection.getIstance();
        String sql = connection.getSqlString("takeSkeletonPointsFilament");
        PreparedStatement stmt = connection.getConn().prepareStatement(sql);
        stmt.setInt(1, idFil);
        stmt.setString(2, nameStr);
        ResultSet resultSet = stmt.executeQuery();
        if (!resultSet.next()) {
            System.out.println("empty result.. no spine point for (?) filament");
            return null;
        }
        do {
            out.add(new PointSkeleton(
                    new Point(resultSet.getDouble("glat"), resultSet.getDouble("glon")),
                            resultSet.getInt("idseg"),
                            resultSet.getInt("n"),
                            resultSet.getDouble("flux"),
                            resultSet.getString("type")));
        }
        while (resultSet.next());

        return out;

    }

}




