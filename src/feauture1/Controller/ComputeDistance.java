package feauture1.Controller;

import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;
import ENTITY.Outline;
import ENTITY.Point;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.computeFilamentBean;

import java.sql.SQLException;
import java.util.List;

public class ComputeDistance {  //classe per il requisito n.11
    private Filament filament;
    private Point PointNMax;
    private Point PointNMin;
    private Outline outline;

    public InfoFilament distanceFromVertex(computeFilamentBean bean) {
        InfoFilament infoFilament = new InfoFilament();

        try {
            this.filament = searchFilament(bean);
            if(this.filament == null) {
                infoFilament.setErrorMessage("filament not exist");
                return infoFilament;
            }
            this.getFields(this.filament.getId(), this.filament.getInstrument().getName());
            infoFilament.setDistVertxUpper(this.distance(this.PointNMax));
            infoFilament.setDistVertxLower(this.distance(this.PointNMin));

        }
        catch (SQLException se) {

            se.printStackTrace();
            infoFilament.setErrorMessage("database fault");

        }
        finally {
            return infoFilament;
        }


}

    private double distance(Point p) {
        double min = Double.POSITIVE_INFINITY;
        double x,y, dist;
        for(Point o: this.outline.getPoints()) {
             x = o.getGlon() - p.getGlon();
             y = o.getLat() - p.getLat();
             dist = Math.sqrt(x*x+y*y);
             min = Double.min(min, dist);
        }
        return min;
    }

    private void getFields(int id, String nameStr) throws SQLException {
        this.PointNMax = DAOPoint.takeVertexUpper(id, nameStr);
        this.PointNMin = DAOPoint.takeVertexLower(id, nameStr);
        this.outline = DAOPoint.takeOutline(id, nameStr);
    }

    private Filament searchFilament(computeFilamentBean bean) throws SQLException {
        if (bean.getType() == 0)
                return DAOFilament.searchFilamentByName(bean.getName());
            else
                return DAOFilament.searchFilamentById(bean.getId(), bean.getNameStr());



    }

    public static void main(String args[]) {
        ComputeDistance c = new ComputeDistance();
        computeFilamentBean bean = new computeFilamentBean(45, "SPIRE");
        System.out.println(c.distanceFromVertex(bean));
    }


}
