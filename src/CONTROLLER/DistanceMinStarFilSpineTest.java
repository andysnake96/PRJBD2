package CONTROLLER;

import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static CONTROLLER.parse.Import2DB.HERSCHEL;
import static org.junit.jupiter.api.Assertions.*;

class DistanceMinStarFilSpineTest {
    private int idFilForTest=73532;
    private String nameStrForTest="SPIRE";

    @BeforeEach
    void setUp() throws Exception {

        Import2DB parser = new Parser();

        parser.readCSV(HERSCHEL,"SPIRE"); //MAKE SURE HERSHEL'S CSV IMPORTED IN DB


    }
    @Test
    void allStarMinDist() throws Exception {
        /*
        calculate distance for set of star (inside a filament specified)
        achiving distance spine<->filament by 2 different way ( by query or by set of skeleton point obj and evalutating distance
         */
        DistanceMinStarFilSpine controller = new DistanceMinStarFilSpine(idFilForTest,nameStrForTest);
        controller.setDistanceComputationMode(DistanceMinStarFilSpine.distanceComputationQuery);
        List<BeanRF12> outQueryMode= controller.allStarMinDist();
        controller.setDistanceComputationMode(DistanceMinStarFilSpine.distanceComputationLocal);
        List<BeanRF12> outLocalMode= controller.allStarMinDist();
        assertEquals(outLocalMode.size(),outQueryMode.size());
        for(int x=0;x<outLocalMode.size();x++){
            BeanRF12 query_i_result=outQueryMode.get(x);
            BeanRF12 local_i_result=outLocalMode.get(x);
            assertEquals(query_i_result.getStarName(),local_i_result.getStarName());
            assertEquals(query_i_result.getDistance(),local_i_result.getDistance());    //check for eatch star :same distance obtained
        }
    }
}