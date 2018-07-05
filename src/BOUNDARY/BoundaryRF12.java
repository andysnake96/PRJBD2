package BOUNDARY;

import CONTROLLER.DistanceMinStarFilSpine;

import java.sql.SQLException;
import java.util.List;

public class BoundaryRF12 {


    public List<List<String>> allStarInFilMinDist(int idFil,String nameStr) throws SQLException {
        DistanceMinStarFilSpine controller= new DistanceMinStarFilSpine(idFil,nameStr);
        return controller.allStarMinDist();
    }
    public List<List<String>> allStarInFilMinDist(String nameFil) throws SQLException {
        DistanceMinStarFilSpine controller = new DistanceMinStarFilSpine(nameFil);
        return controller.allStarMinDist();
    }
    public  static  void  main(String[] args) throws SQLException {
        BoundaryRF12 boundaryRF12= new BoundaryRF12();

        boundaryRF12.allStarInFilMinDist(45,"SPIRE");
        System.err.println("ciao");

    }
    }
