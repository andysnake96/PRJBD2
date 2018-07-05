package CONTROLLER;

import BEAN.BeanRF9;
import DAO.Connection;
import DAO.DAOFilament;
import DAO.DAOPoint;
import ENTITY.Filament;
import ENTITY.PointSkeleton;
import ENTITY.Star;
import feauture1.Bean.ComputeFilamentBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistanceMinStarFilSpine {
    private Filament filament;

    public DistanceMinStarFilSpine(int idFil,String nameStr) throws SQLException {
        this.filament = DAOFilament.searchFilamentById(idFil,nameStr);
    }
    public DistanceMinStarFilSpine(String nameFil) throws SQLException {
        this.filament = DAOFilament.searchFilamentByName(nameFil);
    }

    private double computeMinDistanceStar2Spine(Star star) throws SQLException {
        List<PointSkeleton> mainSpine= DAOPoint.takeFilamentSpine(filament.getId(),filament.getInstrument().getName());
            //compute min distance star<-> mainSpine just taken
        List<PointSkeleton> out = new ArrayList<>();
        Connection connection = Connection.getIstance();
        String sql = connection.getSqlString("skeletonMainSpineMinDistance");
        PreparedStatement stmt = connection.getConn().prepareStatement(sql);
        stmt.setDouble(1, star.getGlat());
        stmt.setDouble(2, star.getGlon());

        ResultSet resultSet = stmt.executeQuery();
        if(!resultSet.next()){
            System.err.println("invalid filament or invalid point in placeholder\n EMPTY RESULT");
            throw new RuntimeException("invalid filament or invalid point in placeholder in query");
        }


        return resultSet.getDouble(1); // minDistanceComputed with query

    }

    public List<List<String>> allStarMinDist() throws SQLException {
        //for all star evalutate min distance to filament spine and return infos
        List<List<String>> out= new ArrayList<>();  //TODO ROW:= NAME,ID,FLUX,minDIST;
        StarFilament getStarContainedController= new StarFilament(
                new ComputeFilamentBean(filament.getId(),filament.getInstrument().getName()));
        BeanRF9 beanRF9= getStarContainedController.starsInFilament();
        List<Star> starsInFilament = beanRF9.getStarsInFilament();

        for (int j=0;j<starsInFilament.size();j++){
            Star star= starsInFilament.get(j);
            double dist= this.computeMinDistanceStar2Spine(star); //TODO in boudary catch runtime exception for invalid filament|| star
            List<String> starInfos= new ArrayList<>();
            starInfos.add(star.getName());
            starInfos.add(String.valueOf(star.getId()));
            starInfos.add(String.valueOf(star.getFlux()));
            starInfos.add(String.valueOf(dist));
            out.add(starInfos);
        }
        return  out;

    }

}
