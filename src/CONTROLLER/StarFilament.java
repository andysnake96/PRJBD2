package CONTROLLER;

import BEAN.BeanRF9;
import CONTROLLER.parse.Parser;
import CONTROLLER.parse.Parser2DB;
import ENTITY.Filament;
import ENTITY.Outline;
import ENTITY.Point;
import ENTITY.Star;
import java.lang.Math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StarFilament {

    private List<Star> star;
    private List<Filament> filaments;   //initialized from constructor
    private HashMap<String,Integer> counters;
    private HashMap<Filament,List<Star >> bindings; //for future extensions of functional requirements
    private final double  INPAR=0.01;

    public BeanRF9 starsInFilament(){
        Filament filament = new Filament();
        List<Star> stars = new ArrayList<>();
        Outline outline = filament.getOutline();
        //TODO simulazione senza DAO
        List<Point> ps = outline.getPoints();
        for(int s=0;s<stars.size();s++){
            double sum=0;
            Star star = stars.get(s);
            for(int p=0;p<ps.size()-1;p++){
                Point point0 = ps.get(p);
                Point point1 = ps.get(p+1);
                int cli= point0.getLat();
                int cbi = point0.getGlon();
                int cli1 = point1.getLat();
                int cbi1 = point1.getGlon();
                int stl = star.getGlat();
                int stb = star.getGlon();
                double num= ((cli-stl)*(cbi1-stb)-(cbi-stb)*(cli1-stl));
                double den=(cli-stl)*(cli1-stl)+(cbi-stb)*(cbi1-stb);
                sum+=Math.atan(num/den);

            }
            sum = Math.abs(sum);
            if (sum>=INPAR) {
                updateMap(stars.get(s));
                bindStar(stars.get(s),filament);  //bind filament (only 1 in this metod) -> star
            }
        }
        BeanRF9 beanRF9= new BeanRF9(this.counters);

        return beanRF9;

    }

    private void bindStar(Star star, Filament filament) {
        List<Star> starsOfFilament= this.bindings.get(filament);
        starsOfFilament.add(star);
    }

    private void updateMap(Star star) {
        String starType = star.getType();
        if ( this.counters.containsKey(starType)  )
            this.counters.put(starType,this.counters.get(starType)+1);//increment counter
        else
            this.counters.put(starType,1);


        }
    }


