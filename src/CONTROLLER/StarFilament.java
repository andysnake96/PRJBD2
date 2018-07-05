package CONTROLLER;

import BEAN.BeanRF9;
import DAO.DAOFilament;
import DAO.DAOPoint;
import DAO.DAOStar;
import ENTITY.Filament;
import ENTITY.Outline;
import ENTITY.Point;
import ENTITY.Star;
import feauture1.Bean.ComputeFilamentBean;

import java.lang.Math;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StarFilament {

    private List<Star> stars;
    private Filament filament;   //initialized from constructor
    private HashMap<String, Integer> counters;
    private HashMap<Filament, List<Star>> bindings; //for future extensions of functional requirements
    private final double INPAR = 0.01;
    private String messageError;

    public StarFilament(ComputeFilamentBean bean) {

        this.counters = new HashMap<>();
        this.bindings = new HashMap<>();
        this.messageError = takeStarFilament(bean);
    }

    public StarFilament(List<Star> stars, Filament filament) {
        this.stars = stars;
        this.filament = filament;
        this.counters = new HashMap<>();
        this.bindings = new HashMap<>();
    }

    private String takeStarFilament(ComputeFilamentBean bean)  {

        try {
            this.stars = DAOStar.takeAllStars();
            this.filament = searchFilament(bean);
            if (this.filament == null) {
                return "Filament don't exist";
            }
            Outline outline = DAOPoint.takeOutline(this.filament.getId(), this.filament.getInstrument().getName());
            this.filament.setOutline(outline);
        }
        catch (SQLException se) {

                se.printStackTrace();
                return "database fault";

        }
        return null;
    }


    public BeanRF9 starsInFilament() {
        //String message = takeStarFilament(bean);
        List<Point> ps = this.filament.getOutline().getPoints();
        List<Star> starsInFilament = new ArrayList<>();
        if(this.messageError != null) {
            return new BeanRF9(this.messageError);
        }
        for (int s = 0; s < this.stars.size(); s++) {
            double sum = 0;
            Star star = this.stars.get(s);
            for (int p = 0; p < ps.size() - 1; p++) {
                Point point0 = ps.get(p);
                Point point1 = ps.get(p + 1);
                double cli = point0.getLat();
                double cbi = point0.getGlon();
                double cli1 = point1.getLat();
                double cbi1 = point1.getGlon();
                double stl = star.getGlat();
                double stb = star.getGlon();
                double num = ((cli - stl) * (cbi1 - stb) - (cbi - stb) * (cli1 - stl));
                double den = (cli - stl) * (cli1 - stl) + (cbi - stb) * (cbi1 - stb);
                sum += Math.atan(num / den);

            }
            sum = Math.abs(sum);
            if (sum >= INPAR) {
                updateMap(star);
               // bindStar(this.stars.get(s), this.filament);  //bind filament (only 1 in this metod) -> star
                starsInFilament.add(star);

            }
        }
        BeanRF9 beanRF9 = new BeanRF9(this.counters, starsInFilament);
        beanRF9.setFilament(this.filament);
        return beanRF9;

    }

    private void bindStar(Star star, Filament filament) {  //TODO per ora lo tolto ma potrebbe servire
        List<Star> starsOfFilament = this.bindings.get(filament);
        starsOfFilament.add(star);
    }

    private void updateMap(Star star) {
        String starType = star.getType();
        if (this.counters.containsKey(starType))
            this.counters.put(starType, this.counters.get(starType) + 1);//increment counter
        else
            this.counters.put(starType, 1);


    }


    private Filament searchFilament(ComputeFilamentBean bean) throws SQLException {
        if (bean.getType() == 0)
            return DAOFilament.searchFilamentByName(bean.getName());
        else
            return DAOFilament.searchFilamentById(bean.getId(), bean.getNameStr());


    }

    public static void main(String args[]) {
        ComputeFilamentBean bean = new ComputeFilamentBean(45, "SPIRE");
        StarFilament starFilament  = new StarFilament(bean);
        BeanRF9 beanRF9 =  starFilament.starsInFilament();
        System.out.println(beanRF9);
    }
}




