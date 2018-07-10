package TEST;

import CONTROLLER.ExtendedSearchFilamentDAO;
import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import ENTITY.Filament;
import BEAN.InfoFilament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static CONTROLLER.parse.Import2DB.HERSCHEL;
import static org.junit.jupiter.api.Assertions.*;

class ExtendedSearchFilamentDAOTest {
    /*
    tests for search filaments queries...
    check if a specified filament (with correct parameters ) is in output of dao methods( called from boundary )
    NB uncorrect inputs checked in boundaries

    test for RF 6,8

     */
    private Filament brightnesNEllipticityCheck;
    private Filament brightnesNEllipticityUnContainedCheck;
    private Filament filamentInSquare;
    private Filament filamentInCircle;
    /*
    OUTLINE STATISTICS HERSHEL; searching in most populatedZone=> largeResult
    min_glat | min_glot | max_glat | max_glon  |       avg_glat       |     avg_glot     |     var_glat      |     var_glon
----------+----------+----------+-----------+----------------------+------------------+-------------------+------------------
 -1.56554 |  4.95194 |  1.49857 | 355.04486 | -0.00288061079447488 | 182.427816483319 | 0.385024071407913 | 22802.4189530986

     */
    private final double glat=-0.00288061079447488; //AVG GLAT
    private final double glon= 11; //taked near the min glat
    private final double radius=2;
    private final  double side=2;
    private final double brightness=20;
    private final double[] ellipticityRange ={1.1,3};

    @BeforeEach
    void setUp() throws Exception {
        brightnesNEllipticityCheck= new Filament(  79358,"HiGALFil052.1054+1.0222", 2.12766,  1.3869, 8.7302E24,
                17.05,  1.1932999999999999E21, 16, "SPIRE");
        brightnesNEllipticityUnContainedCheck= new Filament(318,"SPIRE");
        filamentInCircle = new Filament( 1091,"SPIRE");
        filamentInSquare= new Filament(3362,"SPIRE");

        Import2DB parser = new Parser();

        parser.readCSV(HERSCHEL,"SPIRE"); //MAKE SURE HERSHEL'S CSV IMPORTED IN DB


    }

    @Test
    void searchFilamentByBrightnessAndEllipticity() throws SQLException {
       List<InfoFilament> out = ExtendedSearchFilamentDAO.searchFilamentByBrightnessAndEllipticity(brightness,ellipticityRange);
        boolean filamentDebugContained=false;
        boolean filamentDebugUnContained=false;
       for(int z=0;z<out.size();z++){
           if ( out.get(z).getNameStr().equals(brightnesNEllipticityCheck.getInstrument().getName()) &&
                   (out.get(z).getId() == brightnesNEllipticityCheck.getId()))
               filamentDebugContained=true;

           if ( out.get(z).getNameStr().equals(brightnesNEllipticityUnContainedCheck.getInstrument().getName()) &&
                   (out.get(z).getId() == brightnesNEllipticityUnContainedCheck.getId()))
               filamentDebugUnContained=true;

       }
       assertTrue(filamentDebugContained,"NOT CONTAINED COSTANT FILAMENT WITH RIGHT FEATURE FOR THE QUERY");
        assertFalse(filamentDebugUnContained,"FILAMENT WITH BRIGHTNESS UNDER SELECTED THRESHOLD CONTAINED " +
                "IN RESULT ...");
    }

    @Test
    void searchFilamentByCircle() throws SQLException {
        /*
        assert filament with all outline points contained in circle is in the function under test result
         points  outline filament

         select *,SQRT(POW(ABS(-0.00288061079447488 - (glat)),2)+POW(ABS(11 -(glon)),2)) as CENTER_DISTANCE from outline where idfil = 1091;
         idfil | namestr |   glon   |   glat   | center_distance
        -------+---------+----------+----------+------------------
          1091 | SPIRE   | 12.26979 | -0.99157 | 1.60930834597587
          1091 | SPIRE   | 12.26979 | -0.98677 | 1.60636389846486
          1091 | SPIRE   | 12.26659 | -0.98518 | 1.60286066709916
          1091 | SPIRE   |  12.2666 | -0.98039 | 1.59993755065157
          1091 | SPIRE   |  12.2634 | -0.97879 | 1.59642685267428
          1091 | SPIRE   |  12.2634 | -0.96441 | 1.58767702203753
          1091 | SPIRE   | 12.26021 | -0.96281 |  1.5841697119818
          1091 | SPIRE   | 12.26022 | -0.93087 | 1.56502995334851
          1091 | SPIRE   | 12.26341 | -0.92927 | 1.56665316153021
          1091 | SPIRE   | 12.26341 | -0.91809 | 1.56006828510484
          1091 | SPIRE   | 12.28577 |  -0.9181 | 1.57823668164117
          1091 | SPIRE   | 12.28897 | -0.92129 | 1.58269373761346
          1091 | SPIRE   | 12.28897 | -0.92608 | 1.58547808976645
          1091 | SPIRE   | 12.29216 | -0.92768 | 1.58900326490379
          1091 | SPIRE   | 12.29216 | -0.95004 | 1.60211996247478
          1091 | SPIRE   | 12.28896 | -0.95164 | 1.60048813185404
          1091 | SPIRE   | 12.29216 | -0.95324 | 1.60401385101598
          1091 | SPIRE   | 12.29215 | -0.99157 | 1.62700901375118
                                                // EVERY DISTANCE < RADIUS=2

         */
        List<InfoFilament> out= ExtendedSearchFilamentDAO.searchFilamentByCircle(glat,glon,radius);
        boolean filamentDebugContained=false;
        for(int z=0;z<out.size();z++){
            if ( out.get(z).getNameStr().equals(filamentInCircle.getInstrument().getName()) &&
                    (out.get(z).getId() == filamentInCircle.getId()))
                filamentDebugContained=true;

        }
        assertTrue(filamentDebugContained,"NOT CONTAINED COSTANT FILAMENT WITH RIGHT FEATURE FOR THE QUERY");

    }

    @Test
    void searchFilamentBySquare() throws SQLException {
        /*
        assert filament with all outline points contained in square is in the function under test result
        points outline  filament 3362
        andysnake=# select * from outline where idfil= 3362;
         idfil | namestr |   glon   |   glat
        -------+---------+----------+----------
          3362 | SPIRE   | 10.97609 | -0.58878
          3362 | SPIRE   |  10.9697 | -0.58239
          3362 | SPIRE   |  10.9697 |  -0.5744
          3362 | SPIRE   |  10.9681 | -0.57281
          3362 | SPIRE   | 10.96012 |  -0.5728
          3362 | SPIRE   | 10.95692 | -0.56961
          3362 | SPIRE   | 10.95693 | -0.54086
          3362 | SPIRE   | 10.96172 | -0.54086
          3362 | SPIRE   | 10.96332 | -0.53926
          3362 | SPIRE   | 10.96332 | -0.53447
          3362 | SPIRE   | 10.98888 | -0.53448
          3362 | SPIRE   | 10.99047 | -0.53767
          3362 | SPIRE   | 10.99367 | -0.53767
          3362 | SPIRE   | 10.99527 | -0.53608
          3362 | SPIRE   | 10.99527 | -0.53129
          3362 | SPIRE   | 11.01763 | -0.53129
          3362 | SPIRE   | 11.01763 | -0.53608
          3362 | SPIRE   | 11.01922 | -0.53768
          3362 | SPIRE   | 11.02402 | -0.53768
          3362 | SPIRE   | 11.02721 | -0.54088
          3362 | SPIRE   |  11.0272 | -0.56324
          3362 | SPIRE   | 11.02401 | -0.56643
          3362 | SPIRE   | 11.00963 | -0.56643
          3362 | SPIRE   | 10.99845 | -0.57761
          3362 | SPIRE   | 10.99845 | -0.58879

         */
        List<InfoFilament> out= ExtendedSearchFilamentDAO.searchFilamentByRectangle(glat,glon,side,side);
        boolean filamentDebugContained=false;
        for(int z=0;z<out.size();z++){
            if ( out.get(z).getNameStr().equals(filamentInSquare.getInstrument().getName()) &&
                    (out.get(z).getId() == filamentInSquare.getId()))
                filamentDebugContained=true;

        }
        assertTrue(filamentDebugContained,"NOT CONTAINED COSTANT FILAMENT WITH RIGHT FEATURE FOR THE QUERY");

    }
}