package TEST;

import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;
import ENTITY.Point;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.ComputeFilamentBean;
import feauture1.Controller.ComputeFilament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ComputeFilamentTest {
    private Filament filament;
    private final int idFil = 52;
    private String nameStr = "SPIRE";
    private InfoFilament infoFilament;

    /*
    prendo un filamento ed eseguo l'operazione che mi ritorna il bean con le informazioni riguardanti
    la posizione del centroide del contorno e l'estensione del contorno e il numero di segmenti.
     */

    @BeforeEach
    void findFilament() {
        try {
            this.filament = DAOFilament.searchFilamentById(idFil, nameStr);
            this.filament.setOutline(DAOPoint.takeOutline(idFil, nameStr));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ComputeFilament f = new ComputeFilament();
        ComputeFilamentBean bean = new ComputeFilamentBean(idFil, nameStr);
        infoFilament =  f.computeFilament(bean);
    }

    @Test
    void testCentroide() {
        Point centroide = makeAvg();
        System.out.println(infoFilament + " " +centroide);
       //cotrollo se il centroide è uguale alla media dei punti del contorno.
        assert infoFilament.getGlatCentroide() == centroide.getLat() && infoFilament.getGlonCentroide() == centroide.getGlon();

        double distLon = makeDistLon();
        double distLat = makeDistLat();
        System.out.println(distLon +" " +distLat);
        assert distLon == infoFilament.getDistLon() && distLat == infoFilament.getDistLat();
    }

    /*
    calcolo l'estensione dle contorno e controlllo se le distanze sono equivalenti
     */
    @Test
    void testExstension() {
        double distLon = makeDistLon();
        double distLat = makeDistLat();
        System.out.println(distLon +" " +distLat);
        assert distLon == infoFilament.getDistLon() && distLat == infoFilament.getDistLat();
    }

    /*
    controllo che il numero dei segmenti sia effettivamente lo stesso
     */
    @Test
    void tesNSeg() {
        assert this.filament.getnSeg() == infoFilament.getnSeg();
    }

    private double makeDistLat() {
        double maxLat = Double.NEGATIVE_INFINITY;
        double minLat = Double.POSITIVE_INFINITY;
        for(Point p: this.filament.getOutline().getPoints()) {
            maxLat = Double.max(p.getLat(), maxLat);
            minLat = Double.min(p.getLat(), minLat);

        }
        return Math.abs(maxLat-minLat);
    }

    private double makeDistLon() {
        double maxLon = Double.NEGATIVE_INFINITY;
        double minLon = Double.POSITIVE_INFINITY;
        for(Point p: this.filament.getOutline().getPoints()) {
            maxLon = Double.max(p.getGlon(), maxLon);
            minLon = Double.min(p.getGlon(), minLon);

        }
        return Math.abs(maxLon-minLon);
    }

    /*
    calcola il centroide come media dei punti del contorno.
     */

    private Point makeAvg() {
        double sumLat=0;
        double sumLon=0;
        int n = 0;
        for(Point p: this.filament.getOutline().getPoints()) {
            sumLat += p.getLat();
            sumLon += p.getGlon();
            n++;
        }
        Point centroide = new Point();
        centroide.setLat(sumLat/n);
        centroide.setGlon(sumLon/n);
        return centroide;
    }

}
