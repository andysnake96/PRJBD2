package BOUNDARY;

import CONTROLLER.ExtendedSearchFilamentDAO;

import java.sql.SQLException;
import java.util.List;

public class BoundaryExtendedSrchFil {
    public final double MINELLIPTICITY = 1;
    public final double MAXELLIPTICITY = 10;
    //RF6

    public List<List<String>> searchFilamentByBrightnessAndEllipticity
            (double brightness, double[] ellipticityRange) throws SQLException {
        String errorMSg = null;
        if (brightness < 0)
            errorMSg = " invalid brightness";
        if (ellipticityRange[0] > ellipticityRange[1]) {
            System.err.println("invalid range order...swapping values");
            double a = ellipticityRange[0];
            ellipticityRange[0] = ellipticityRange[1];
            ellipticityRange[1] = a;
        }
        if (ellipticityRange[0] < 1 || ellipticityRange[1] > 10)
            errorMSg = "outOfEllipticityrange values"; //nb orderd asserted before
        if (errorMSg != null) //some input wrong..
            throw new IllegalArgumentException(errorMSg);   //todo gui catch!

        return ExtendedSearchFilamentDAO.searchFilamentByBrightnessAndEllipticity(brightness, ellipticityRange);
    }

    //RF8 -CIRCLE
    public List<List<String>> searchFilamentByCircle(double glat, double glon, double radius) throws SQLException {
        if (radius < 0)
            throw new IllegalArgumentException("invalid radius for region...");
        return ExtendedSearchFilamentDAO.searchFilamentByCircle(glat, glon, radius);
    }

    //RF8 -SQUARE
    public List<List<String>> searchFilamentBySquare(double glat, double glon, double side)
            throws SQLException {
        if (side < 0)
            throw new IllegalArgumentException("invalid side value");
        return ExtendedSearchFilamentDAO.searchFilamentByRectangle(glat, glon, side, side);
    }

    public static void main(String[] args) throws SQLException {
        BoundaryExtendedSrchFil boundary = new BoundaryExtendedSrchFil();
        List<List<String>> out0= boundary.searchFilamentByBrightnessAndEllipticity(0.1,new double[] {1.1,5.3});
        List<List<String>> out1= boundary.searchFilamentBySquare(-0.00288061079447486,5,2);

        List<List<String>> out2= boundary.searchFilamentByCircle(-0.00288061079447486,5,2);
        System.err.println("ciaoGalli");
    }
}
