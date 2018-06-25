package feauture1.Controller;

import BEAN.BeanRF9;
import CONTROLLER.StarFilament;
import DAO.DAOFilament;
import DAO.DAOPoint;
import DAO.DAOStar;
import ENTITY.Filament;
import ENTITY.Star;
import feauture1.Bean.InfoStarInFilamentAndRectangle;
import feauture1.Bean.Rectangle;
import feauture1.StarAndType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StarInFilamentAndInRectangle {
    private List<Filament> filaments;  //tutti i filamenti contenuti nel database
    private StarAndType starsInRectangle;
    private HashMap<String, Integer> counters;  // per ogni tipo il numero di stelle nel filamento
    private static final String takeOutline = "takeoutline";

    public StarInFilamentAndInRectangle() {
        this.counters = new HashMap<>();
    }

    public InfoStarInFilamentAndRectangle execute(Rectangle rectangle) {
        try {


            this.takeStarAndFilamentInRectangle(rectangle);
            //Instauro una connessione con il database per ottenere i contorni dei filamenti.
            DAO.Connection connection = DAO.Connection.getIstance();
            java.sql.Connection conn = connection.getConn();
            String sql = connection.getSqlString(takeOutline);
            PreparedStatement stmt = conn.prepareStatement(sql);
            /*
            Vedo le stelle che sono sono all'nterno dei filamenti utilizzando la classe starFilament.
             */

            for (Filament f : this.filaments) {
                stmt.setInt(1, f.getId());
                stmt.setString(2, f.getInstrument().getName());
                f.setOutline(DAOPoint.takeOutline(stmt));
                //f.setOutline(DAOPoint.takeOutline(f.getId(),f.getInstrument().getName()));  //prendo il contorno dei filamenti

                StarFilament starFilament = new StarFilament(this.starsInRectangle.getStars(), f);
                BeanRF9 beanRF9 = starFilament.starsInFilament();
                updateCounters(beanRF9);
               /*
               le stelle che già sono state trovate all'interno di un filamento vengono scartate, se tutte le stelle sono
               all'interno di un filamento posso uscire dal ciclo.
                */
                this.starsInRectangle.removeStars(beanRF9.getStarsInFilament());
                if (this.starsInRectangle.getStars().isEmpty()) {
                    break;
                }
            }
            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new InfoStarInFilamentAndRectangle("database fault");
        }
        return makeInfo();

    }

    private void findOutline(Filament f) throws SQLException {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        java.sql.Connection conn = connection.getConn();
        String sql = connection.getSqlString(takeOutline);

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, f.getId());
        stmt.setString(2, f.getInstrument().getName());
        f.setOutline(DAOPoint.takeOutline(stmt));
        connection.closeConn(conn);
        stmt.close();
    }

    /*
    questo metodo calcola il numero di stelle dentro la regione del rettangolo, per ogni tipo, che non sono all'interno
    del contorno di un filamento.
     */

    private InfoStarInFilamentAndRectangle makeInfo() {
        HashMap<String, Integer> nStarNotInFilament = new HashMap<>();
        HashMap<String, Integer> nStarInRectangle = this.starsInRectangle.getCounters();
        Set list = nStarInRectangle.keySet();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String type = iter.next();
            Integer value;
            if (this.counters.containsKey(type))
                value = nStarInRectangle.get(type) - this.counters.get(type);
            else
                value = nStarInRectangle.get(type);
            nStarNotInFilament.put(type, value);
        }
        InfoStarInFilamentAndRectangle info = new InfoStarInFilamentAndRectangle();
        info.setStarsInFilament(this.counters);
        info.setStarsNotInFilament(nStarNotInFilament);
        return info;
    }

    /*
    questo metodo aggiorna l'hasmap che tiene conto del numero di stelle in un contorno di un filamento per ogni tipo
     */

    private void updateCounters(BeanRF9 beanRF9) {
        HashMap<String, Integer> c = beanRF9.getCounters();
        Set list = c.keySet();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Integer value = c.get(key);
            if (this.counters.containsKey(key))
                this.counters.put(key, this.counters.get(key) + value);//increment counter
            else
                this.counters.put(key, value);
        }
    }

    /*
    questa funzone calcola l'intervallo dello spazio in cui le stelle devono stare per essere all'interno del rettangolo,
    la latitudine non deve essere distante dal centroide più dell'altezza del rettangolo diviso due. e la latitudine non
    deve essere distante dal centroide più della metà del lato del rettangolo.
     */

    private void takeStarAndFilamentInRectangle(Rectangle rectangle) throws SQLException {
        double h = rectangle.getH();
        double l = rectangle.getL();
        double cLat = rectangle.getGlat();
        double cLon = rectangle.getGlon();
        double glonLeft = cLon - l / 2;
        double glonRight = cLon + l / 2;
        double glatDown = cLat - h / 2;
        double glatUp = cLat + h / 2;
        this.starsInRectangle = DAOStar.takeStarInRectangle(glonLeft, glonRight, glatDown, glatUp);
        this.filaments = DAOFilament.takeFilamentsInRectangle(glonLeft, glonRight, glatDown, glatUp);
    }


    public static void main(String args[]) {
        Rectangle r = new Rectangle();
        r.setGlat(0);
        r.setGlon(0);
        try {
            r.setH(2);
            r.setL(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StarInFilamentAndInRectangle s = new StarInFilamentAndInRectangle();
        InfoStarInFilamentAndRectangle i = s.execute(r);
        System.out.println(i);
    }
    }



