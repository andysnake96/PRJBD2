package feauture1.Controller;

import DAO.DAOFilament;
import ENTITY.Filament;
import ENTITY.Point;
import DAO.DAOPoint;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.computeFilamentBean;

import java.sql.SQLException;

import static java.lang.Math.abs;


public class ComputeFilament {

    private Filament filament; //not static


    public  InfoFilament computeFilament(computeFilamentBean bean) { //it's not a constructor
        InfoFilament infoFilament = new InfoFilament();
        try {
            this.filament = searchFilament(bean);
            if(this.filament == null) {
                infoFilament.setErrorMessage("filament not exist");
                return infoFilament;
            }
            Point centroide = DAOPoint.computeCentroide(this.filament.getId(), this.filament.getInstrument().getName());
            infoFilament.setGlatCentroide(centroide.getLat());
            infoFilament.setGlonCentroide(centroide.getGlon());
            this.computeExstension(infoFilament);
            infoFilament.setnSeg(this.filament.getnSeg());
        }
        catch (SQLException se) {

            se.printStackTrace();
            infoFilament.setErrorMessage("databse fault");

        }
        finally {
            return infoFilament;
        }

    }

    private void computeExstension(InfoFilament infoFilament) throws SQLException {
        Point point[] = DAOPoint.searchPointMaxMin(filament.getId(), filament.getInstrument().getName());
        Point point1 = point[0];
        Point point2 = point[1];
        Double distLon = abs(point1.getGlon() - point2.getGlon());
        Double distLat = abs(point1.getLat() - point2.getLat());
        infoFilament.setDistLat(distLat);
        infoFilament.setDistLon(distLon);


    }


    private Filament searchFilament(computeFilamentBean bean) throws SQLException {
        if (bean.getType() == 0)
            return DAOFilament.searchFilamentByName(bean.getName());
        else
            return DAOFilament.searchFilamentById(bean.getId(), bean.getNameStr());


    }


    public static void main(String args[]) {
        ComputeFilament f = new ComputeFilament();
        computeFilamentBean bean = new computeFilamentBean(45, "SPIRE");
        System.out.println(f.computeFilament(bean));
    }
}
