package BOUNDARY;

import CONTROLLER.BeanRF12;
import CONTROLLER.DistanceMinStarFilSpine;

import java.sql.SQLException;
import java.util.List;

public class BoundaryRF12 {


    public List<BeanRF12> allStarInFilMinDist(int idFil, String nameStr) throws SQLException {
        DistanceMinStarFilSpine controller= new DistanceMinStarFilSpine(idFil,nameStr);
        return controller.allStarMinDist();
    }
    public List<BeanRF12> allStarInFilMinDist(String nameFil) throws SQLException {
        DistanceMinStarFilSpine controller = new DistanceMinStarFilSpine(nameFil);
        return controller.allStarMinDist();
    }

    }
