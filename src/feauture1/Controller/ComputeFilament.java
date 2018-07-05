package feauture1.Controller;

import DAO.DAOFilament;
import ENTITY.Filament;
import ENTITY.Point;
import DAO.DAOPoint;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.ComputeFilamentBean;

import java.sql.SQLException;

import static java.lang.Math.abs;

/*
questa classe svolge le specifiche del requisito 5
 */

public class ComputeFilament {

    private Filament filament; //questo attributo si riferisce al filamento inserito dall'utente


    /*
    ottengo tutte le informazioni richieste e l'inserisco nel bean infofilament per riportarle alla grafica
     */

    public  InfoFilament computeFilament(ComputeFilamentBean bean) {
        InfoFilament infoFilament = new InfoFilament();
        try {
            this.filament = searchFilament(bean);
            if(this.filament == null) {
                infoFilament.setErrorMessage("filament not exist");
                return infoFilament;
            }
            Point centroide = DAOPoint.computeCentroide(this.filament.getId(), this.filament.getInstrument().getName());
            if(centroide == null) {
                infoFilament.setErrorMessage("Error, impossible compute centroide!!!");
                return infoFilament;
            }
            infoFilament.setGlatCentroide(centroide.getLat());
            infoFilament.setGlonCentroide(centroide.getGlon());
            this.computeExstension(infoFilament);
            infoFilament.setnSeg(this.filament.getnSeg());
        }
        catch (Exception se) {

            se.printStackTrace();
            infoFilament.setErrorMessage("database fault");

        }
        finally {
            return infoFilament;
        }

    }

    /*
    calcolo la 	distanzatra	il	minimo	e massimo	delle	posizioni	longitudinali,	e	tra	il	minimo	e	massimo
    delle	posizioni latitudinali, eseguendo una query al databse per le posizioni minime e massime
     */

    private void computeExstension(InfoFilament infoFilament) throws SQLException {
        Point point[] = DAOPoint.searchPointMaxMin(filament.getId(), filament.getInstrument().getName());
        if(point == null) {
            infoFilament.setErrorMessage("error, impossible compute exstension!!!");
            return;
        }
        Point point1 = point[0];
        Point point2 = point[1];
        System.out.println(point);

        Double distLon = abs(point1.getGlon() - point2.getGlon());
        Double distLat = abs(point1.getLat() - point2.getLat());
        infoFilament.setDistLat(distLat);
        infoFilament.setDistLon(distLon);


    }

    /*
    l'utente può ricercare il filamento o tramite il nome o tramite l'id.
     */


    private Filament searchFilament(ComputeFilamentBean bean) throws SQLException {
        if (bean.getType() == 0)
            return DAOFilament.searchFilamentByName(bean.getName());
        else
            return DAOFilament.searchFilamentById(bean.getId(), bean.getNameStr());


    }


    public static void main(String args[]) {
        ComputeFilament f = new ComputeFilament();
        ComputeFilamentBean bean = new ComputeFilamentBean(45, "SPIRE");
        System.out.println(f.computeFilament(bean));
    }
}
