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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StarInFilamentAndInRectangle {
    private List<Filament> filaments;
    private StarAndType starsInRectangle;
    private  HashMap<String, Integer> counters;  // per ogni tipo il numero di stelle nel filamento

    public StarInFilamentAndInRectangle() {
        this.counters = new HashMap<>();
    }

    public InfoStarInFilamentAndRectangle execute(Rectangle rectangle)  {
       try {
           this.filaments = DAOFilament.takeAllFilaments();

           this.starsInRectangle = takeStarInRectangle(rectangle);


           for (Filament f : this.filaments) {
               f.setOutline(DAOPoint.takeOutline(f.getId(),f.getInstrument().getName()));  //prendo il contorno dei filamenti
               StarFilament starFilament = new StarFilament(this.starsInRectangle.getStars(), f);
               BeanRF9 beanRF9 = starFilament.starsInFilament();
               updateCounters(beanRF9);
               this.starsInRectangle.removeStars(beanRF9.getStarsInFilament());
               if (this.starsInRectangle.getStars().isEmpty()) {
                   break;
               }
           }
       } catch (SQLException e) {
           return new InfoStarInFilamentAndRectangle("database fault");
       }
        return makeInfo();

    }

    /*
    questo metodo calcola il numero di stelle dentro la regione del rettangolo per ogni tipo, che non sono all'interno
    del contorno di un filamento.
     */

    private InfoStarInFilamentAndRectangle makeInfo() {
        HashMap<String, Integer> nStarNotInFilament = new HashMap<>();
        HashMap<String, Integer> nStarInRectangle = this.starsInRectangle.getCounters();
        Set list = nStarInRectangle.keySet();
        Iterator<String> iter = list.iterator();
        while(iter.hasNext()) {
            String type = iter.next();
            Integer value;
            if(this.counters.containsKey(type))
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
        while(iter.hasNext()) {
            String key = iter.next();
            Integer value = c.get(key);
            if (this.counters.containsKey(key))
                this.counters.put(key, this.counters.get(key) +value);//increment counter
            else
                this.counters.put(key, value);
        }
    }

    private StarAndType takeStarInRectangle(Rectangle rectangle) throws SQLException {
        double h = rectangle.getH();
        double l = rectangle.getL();
        double cLat = rectangle.getGlat();
        double cLon = rectangle.getGlon();
        double glonLeft = cLon - l/2;
        double glonRight = cLon + l/2;
        double glatDown = cLat - h/2;
        double glatUp = cLat + h/2;
        return DAOStar.takeStarInRectangle(glonLeft,glonRight,glatDown, glatUp);
        }

    public static void main(String args[]) {
        Rectangle r = new Rectangle();
        r.setGlat(0);
        r.setGlon(0);
        r.setH(10);
        r.setL(100);
        StarInFilamentAndInRectangle s = new StarInFilamentAndInRectangle();
        InfoStarInFilamentAndRectangle i = s.execute(r);
        System.out.println(i);
    }
    }



