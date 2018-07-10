package CONTROLLER;

import BEAN.BeanRF12;
import BEAN.BeanRF9;
import DAO.Connection;
import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;
import ENTITY.PointSkeleton;
import ENTITY.Star;
import BEAN.ComputeFilamentBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DistanceMinStarFilSpine {
    /*
    RF12, Compute min distance star<->spine by query ( min distance getted from query in skeleton table)
    or by evalutating min distance from skeleton point,
     */
    private Filament filament;
    public static final String distanceComputationQuery="QUERY";
    public static final String distanceComputationLocal="LOCAL";  // costant string to change computation mode of distance star<->spine

    public  void  setDistanceComputationMode(String distanceComputationMode) {
        this.distanceComputationMode = distanceComputationMode;
    }

    private String distanceComputationMode=distanceComputationLocal;  //default mode=local


    public DistanceMinStarFilSpine(int idFil, String nameStr) throws SQLException,IllegalArgumentException  {
        this.filament = DAOFilament.searchFilamentById(idFil,nameStr);
        if(filament==null)
            throw new IllegalArgumentException("invaild idFil or name instrument");
        List<PointSkeleton> mainSpine= DAOPoint.takeFilamentSpine(filament.getId(),filament.getInstrument().getName());
        //compute min distance star<-> mainSpine just taken
        filament.setPointSkeletonSpine(mainSpine);
    }
    public DistanceMinStarFilSpine(String nameFil) throws SQLException,IllegalArgumentException {
        this.filament = DAOFilament.searchFilamentByName(nameFil);
        if(filament==null)
            throw new IllegalArgumentException(" Filamento inserito non trovato ");
        List<PointSkeleton> mainSpine= DAOPoint.takeFilamentSpine(filament.getId(),filament.getInstrument().getName());
        //compute min distance star<-> mainSpine just taken
        filament.setPointSkeletonSpine(mainSpine);
    }

    private double computeMinDistanceStar2SpineLocal(Star star) throws SQLException {
        /*compute min distance with queried main spine points getted from filament...*/
        List<PointSkeleton> mainSpine= filament.getPointSkeletonSpine();
        double skGlat= mainSpine.get(0).getPoint().getLat();
        double skGlot= mainSpine.get(0).getPoint().getGlon();
        double minDist= Math.sqrt(Math.pow(Math.abs(star.getGlat()-skGlat),2)
                                 + Math.pow(Math.abs(star.getGlon()-skGlot),2));
                            //initialized with first evalutation

        for(int j=1;j<mainSpine.size();j++){
            skGlat= mainSpine.get(j).getPoint().getLat();
            skGlot= mainSpine.get(j).getPoint().getGlon();
            double dist= Math.sqrt(Math.pow(Math.abs(star.getGlat()-skGlat),2)
                                    + Math.pow(Math.abs(star.getGlon()-skGlot),2));
            if(dist<minDist)
                minDist=dist;                                                   //update mindist

        }
        return minDist;
    }

        private double computeMinDistanceStar2SpineQuery(Star star) throws SQLException {

        //old... longer waiting time,evalutate distance from specilized query in skeleton table
        Connection connection = Connection.getIstance();
        String sql = connection.getSqlString("skeletonMainSpineMinDistance");
        java.sql.Connection conn= connection.getConn();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, star.getGlat());
        stmt.setDouble(2, star.getGlon());
        stmt.setInt(3,filament.getId());
        stmt.setString(4,filament.getInstrument().getName());
        ResultSet resultSet = stmt.executeQuery();

        if(!resultSet.next()){
            System.err.println("invalid filament   EMPTY RESULT");
        }
        double result= resultSet.getDouble(1);
        stmt.close();
        resultSet.close();
        connection.closeConn(conn);
        return result; // minDistanceComputed with query*/

    }

    public List<BeanRF12> allStarMinDist() throws SQLException,IllegalArgumentException {
        //for all star(in a fil) evalutate min distance to filament spine and return infos
        List<BeanRF12> out= new ArrayList<>();
        StarFilament getStarContainedController= new StarFilament(
                new ComputeFilamentBean(filament.getId(),filament.getInstrument().getName()));
        BeanRF9 beanRF9= getStarContainedController.starsInFilament();
        List<Star> starsInFilament = beanRF9.getStarsInFilament();
        if(starsInFilament==null || starsInFilament.size()==0)
            throw new IllegalArgumentException("non ci sono stelle all'interno del filamento");
        System.out.println("stars#="+starsInFilament.size());
        for (int j=0;j<starsInFilament.size();j++){
            Star star= starsInFilament.get(j);
            double dist;
            //check min distance computation mode
            if(this.distanceComputationMode.equals(distanceComputationLocal))
                dist= this.computeMinDistanceStar2SpineLocal(star);
            else
                dist=this.computeMinDistanceStar2SpineQuery(star);

            BeanRF12 starInfos= new BeanRF12(star.getGlat(),star.getGlon(),star.getFlux(),dist,star.getName());
            out.add(starInfos);
        }
        return  out;

    }


}
