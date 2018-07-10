package CONTROLLER;

import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;

import ENTITY.Point;
import BEAN.InfoFilament;
import BEAN.ComputeFilamentBean;

import java.sql.SQLException;


public class ComputeDistance {  //classe per il requisito n.11
    private Filament filament;
    private Point PointNMax;  //punto del segmento con numero progrssivo massimo
    private Point PointNMin;  //punto del segmento con numero progressivo minimo


    public InfoFilament distanceFromVertex(ComputeFilamentBean bean) {
        InfoFilament infoFilament = new InfoFilament();

        try {
            this.filament = searchFilament(bean);
            if(this.filament == null) {
                infoFilament.setErrorMessage("filament not exist");
                return infoFilament;
            }
            this.getFields(this.filament.getId(), this.filament.getInstrument().getName());
            if(this.PointNMax == null || this.PointNMin == null) {
                infoFilament.setErrorMessage("error Vertex not found!!!");
                return infoFilament;
            }
            infoFilament.setDistVertxUpper(this.distance(this.PointNMax));
            infoFilament.setDistVertxLower(this.distance(this.PointNMin));

        }
        catch (SQLException se) {  //in caso di eccezzione avverto l'utente

            se.printStackTrace();
            infoFilament.setErrorMessage("database fault");

        }

        finally {
            return infoFilament;
        }


}
    /*
    funzione che calcola la distanza euclidea del vertice dal contorno.
     */

    private double distance(Point p) {
        double min = Double.POSITIVE_INFINITY;
        double x,y, dist;

        for(Point o: this.filament.getOutline().getPoints()) {
             x = o.getGlon() - p.getGlon();

             y = o.getLat() - p.getLat();
             dist = Math.sqrt(x*x+y*y);

             min = Double.min(min, dist);

        }
        return min;
    }

    /*
    prende i vertici del segmento e setta il contorno del filamento con delle query al database
     */
    private void getFields(int id, String nameStr) throws SQLException {
        this.PointNMax = DAOPoint.takeVertexUpper(id, nameStr);
        this.PointNMin = DAOPoint.takeVertexLower(id, nameStr);
        this.filament.setOutline( DAOPoint.takeOutline(id, nameStr));
    }

    /*
    funzione che cerca il filamento in base al nome o all'id e  lo strumento in corrispondenza ai dati inseriti
    dall'utente
     */

    private Filament searchFilament(ComputeFilamentBean bean) throws SQLException {
        if (bean.getType() == 0)
                return DAOFilament.searchFilamentByName(bean.getName());
            else
                return DAOFilament.searchFilamentById(bean.getId(), bean.getNameStr());



    }

    public static void main(String args[]) {
        ComputeDistance c = new ComputeDistance();
        ComputeFilamentBean bean = new ComputeFilamentBean(59, "SPIRE");
        System.out.println(c.distanceFromVertex(bean));
    }


}
