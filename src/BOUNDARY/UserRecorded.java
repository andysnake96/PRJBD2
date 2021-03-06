package BOUNDARY;

import BEAN.*;
import BEAN.BeanRF12;
import CONTROLLER.*;
import CONTROLLER.ComputeFilament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRecorded {



    private ComputeDistance computeDistance;
    private ComputeFilament computeFilament;
    private CONTROLLER.searchFilamentByNSeg searchFilamentByNSeg;
    private StarInFilamentAndInRectangle starInFilamentAndInRectangle;
    private final int DIMRANGE = 3;

    //rf5
    public InfoFilament recoveryInfoFilament(ComputeFilamentBean bean) {
        this.computeFilament = new ComputeFilament();
        return this.computeFilament.computeFilament(bean);
    }
    //rf7
    public List<InfoFilament> searchFilamentNSeg(RangeNSeg range) {
        List<InfoFilament> infos = new ArrayList<>();
        if(range.getnSegMax() < range.getnSegMin()) {
            InfoFilament infoError = new InfoFilament();
            infoError.setErrorMessage("incorrect range");
            infos.add(infoError);
            return infos;
        }
        if(range.getnSegMax()-range.getnSegMin() < DIMRANGE) {
            InfoFilament infoError = new InfoFilament();
            infoError.setErrorMessage("The interval must be at least "+ DIMRANGE+" in size" );
            infos.add(infoError);
            return infos;
        }
        this.searchFilamentByNSeg = new searchFilamentByNSeg();
        infos = this.searchFilamentByNSeg.executeSearch(range);
        return infos;
    }
    //rf10
    public InfoStarInFilamentAndRectangle starInFormationInRectangle(Rectangle rectangle) {
        InfoStarInFilamentAndRectangle info;
        this.starInFilamentAndInRectangle = new StarInFilamentAndInRectangle();
        info = this.starInFilamentAndInRectangle.execute(rectangle);
        return info;
    }
    //rf11
    public InfoFilament distanceVertexOutline(ComputeFilamentBean bean) {
        InfoFilament info;
        this.computeDistance = new ComputeDistance();
        info = this.computeDistance.distanceFromVertex(bean);
        return info;
    }

    //rf9
    public BeanRF9 starsInFilament(ComputeFilamentBean computeFilamentBean) {
        StarFilament starFilament = new StarFilament(computeFilamentBean);

        return starFilament.starsInFilament();
    }

    //as rf from here..

    //rf12
    public List<BeanRF12> allStarInFilMinDist(int idFil, String nameStr) throws SQLException,IllegalArgumentException  {
        DistanceMinStarFilSpine controller= new DistanceMinStarFilSpine(idFil,nameStr);
        return controller.allStarMinDist();
    }
    public List<BeanRF12> allStarInFilMinDist(String nameFil) throws SQLException,IllegalArgumentException  {
        DistanceMinStarFilSpine controller = new DistanceMinStarFilSpine(nameFil);
        return controller.allStarMinDist();
    }

    //RF6
    public final double MINELLIPTICITY = 1;
    public final double MAXELLIPTICITY = 10;


    public List<InfoFilament> searchFilamentByBrightnessAndEllipticity
            (double brightness, double[] ellipticityRange) throws SQLException,IllegalArgumentException {
        String errorMSg = null;
        if (brightness < 0)
            errorMSg = " BRILLANZA NON VALIDA";
        if (ellipticityRange[0] > ellipticityRange[1]) {
            System.err.println("ORDINE DI ESTREMI RANGE NON VALIDO ,\n INVERSIONE ESTREMI...");
            double a = ellipticityRange[0];
            ellipticityRange[0] = ellipticityRange[1];
            ellipticityRange[1] = a;
        }
        if (ellipticityRange[0] < 1 || ellipticityRange[1] > 10)
            errorMSg = "VALORI NON AMMISSIMIBILI PER ELLITTICITA \n (POSSIBILI VALORI IN (0,10))"; //nb orderd asserted before
        if (errorMSg != null) //some input wrong..
            throw new IllegalArgumentException(errorMSg);

        return ExtendedSearchFilamentDAO.searchFilamentByBrightnessAndEllipticity(brightness, ellipticityRange);
    }

    //RF8 -CIRCLE
    public List<InfoFilament> searchFilamentByCircle(double glat, double glon, double radius) throws SQLException {
        if (radius < 0)
            throw new IllegalArgumentException("RAGGIO NON VALIDO...");
        return ExtendedSearchFilamentDAO.searchFilamentByCircle(glat, glon, radius);
    }

    //RF8 -SQUARE
    public List<InfoFilament> searchFilamentBySquare(double glat, double glon, double side)
            throws SQLException {
        if (side < 0)
            throw new IllegalArgumentException("LATO NON VALIDO");
        return ExtendedSearchFilamentDAO.searchFilamentByRectangle(glat, glon, side, side);
    }
}
