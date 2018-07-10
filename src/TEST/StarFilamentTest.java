package TEST;

import BEAN.BeanRF9;
import CONTROLLER.StarFilament;
import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;
import ENTITY.Point;
import ENTITY.Star;
import BEAN.ComputeFilamentBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/* test per il requisito 9. preso un filamento specifico, ho due stelle nel database, una che è all'interno del contorno
del filamento e una all'esterno. Cosi si vede che la stella all'interno del contorno del filamento è presente nella lista di
stelle all'interno del filamento. la stella all'esterno invece non è presente.

 */

public class StarFilamentTest {
    private Filament filament;
    private Star starInFilament;
    private Star starNotInFilament;
    private final int IDFIL = 45;
    private final String NAMESTR = "SPIRE";
    private BeanRF9 beanRF9;


    @BeforeEach
    void setUp() {
        try {
            this.filament = DAOFilament.searchFilamentById(IDFIL, NAMESTR);
            if(this.filament == null)
                throw new Exception();
            this.filament.setOutline(DAOPoint.takeOutline(IDFIL, NAMESTR));

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.starNotInFilament = new Star(23813, "HIGALPS005.0014+0.0856", 5.000437, 0.084881, 36.8936, "PROTOSTELLAR", null);
        this.starInFilament = new Star(58996, "HIGALPS013.0230+0.1313", 13.023134, 0.131209, 11.8908, "PRESTELLAR", null);
        ComputeFilamentBean bean = new ComputeFilamentBean(IDFIL, NAMESTR);
        StarFilament starFilament  = new StarFilament(bean);
        this.beanRF9 =  starFilament.starsInFilament();

    }

    @Test
    void starInFilament() {

        double sum = execute(this.starInFilament);
        System.out.println("risultato del calcolo di una stella nel filamento:  "+sum);
        List<Star> starsInFilament = this.beanRF9.getStarsInFilament();
        System.out.println("stella contenuta nel filamento?: "+ starsInFilament.contains(this.starInFilament));
        assert starsInFilament.contains(this.starInFilament);

    }

    /*
    esegue il calcolo che dimostra se le stelle sono all'interno o all'esterno del filamento.
     */

    protected  double execute(Star star) {
        List<Point> ps = this.filament.getOutline().getPoints();
        double sum = 0;
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
        return sum;
    }


    @Test
    void starNotInFilament() {
        double sum = execute(starNotInFilament);
        System.out.println("risultato di una stella non nel filamento: "  + sum);
        List<Star> starsInFilament = this.beanRF9.getStarsInFilament();
        System.out.println("stella contenuta nel filamento?: "+ starsInFilament.contains(this.starNotInFilament));
        assert !starsInFilament.contains(this.starNotInFilament);


    }



}
