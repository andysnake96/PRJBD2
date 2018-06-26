package TEST;

import DAO.DAOStar;
import feauture1.Bean.InfoStarInFilamentAndRectangle;
import feauture1.Bean.Rectangle;
import feauture1.Controller.StarInFilamentAndInRectangle;
import feauture1.StarAndType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
caso di test per il requisito 10
 */

public class StarInFilamentAndInRectangleTest {
    private StarAndType starAndType;
    private final double CENTROIDELAT = 25;
    private final double CENTROIDELON = 30;
    private final double  L = 200;
    private final double H = 200;
    private Rectangle r;


    /*
    creo il rettangolo e trovo le stelle dentro questa regione e il numero di stelle per tipo
     */
    @BeforeEach
    void starInRectangle() {
        this.r = new Rectangle();
        r.setGlat(CENTROIDELAT);
        r.setGlon(CENTROIDELON);

        try {
            r.setH(H);
            r.setL(L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double h = r.getH();
        double l = r.getL();
        double cLat = r.getGlat();
        double cLon = r.getGlon();
        double glonLeft = cLon - l / 2;
        double glonRight = cLon + l / 2;
        double glatDown = cLat - h / 2;
        double glatUp = cLat + h / 2;
        try {
            this.starAndType = DAOStar.takeStarInRectangle(glonLeft, glonRight, glatDown, glatUp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    eseguo l'operazione che mi torna le informazioni del numero di stelle per tipo all'interno della regione rispettivamente
    dentro il filamento e fuori dal filamento, controllo se per ogni tipo il totale è uguale al totale di stelle di quel tipo
    all'interno della regione.
     */

    @Test
    void testStarInFilamentAndInRectangle() {
        StarInFilamentAndInRectangle s = new StarInFilamentAndInRectangle();
        InfoStarInFilamentAndRectangle i = s.execute(this.r);
        HashMap<String, Integer> starInFilament = i.getStarsInFilament();
        HashMap<String, Integer> starNotInFilament = i.getStarsNotInFilament();
        HashMap<String, Integer> starInRectangle = this.starAndType.getCounters();
        int sumType;
        Set list = starInFilament.keySet();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {

            String key = iter.next();

            if (starNotInFilament.containsKey(key)) {
                assert starInRectangle.containsKey(key);

                sumType = starInFilament.get(key) + starNotInFilament.get(key);
                assert sumType == starInRectangle.get(key);
            }
            else {
                assert starInRectangle.containsKey(key);
                sumType = starInFilament.get(key);
                assert sumType == starInRectangle.get(key);
            }
        }
        System.out.println(i);
        System.out.println(starAndType);
    }
}
