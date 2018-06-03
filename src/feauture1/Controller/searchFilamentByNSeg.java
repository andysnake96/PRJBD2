package feauture1.Controller;

import ENTITY.Filament;
import feauture1.Bean.BeanFilament;
import feauture1.Bean.rangeNSeg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class searchFilamentByNSeg {

    private List<Filament> filaments;

    public searchFilamentByNSeg() {
        this.filaments = new ArrayList<>();
    }

    public List<BeanFilament> executeSearch(rangeNSeg range) {
        List<BeanFilament> beans = new ArrayList<>();
        try {
            this.filaments =  DAO.DAOFilament.searchByRangeNSeg(range.getnSegMin(), range.getnSegMax());
            for(Filament filament : this.filaments) {
                BeanFilament bean = new BeanFilament();
                bean.setId(filament.getId());
                bean.setContrast(filament.getContrast());
                bean.setDensAvg(filament.getDensAvg());
                bean.setEllipticity(filament.getEllipticity());
                bean.setFluxTot(filament.getFluxTot());
                bean.setName(filament.getName());
                bean.setNameStr(filament.getInstrument().getName());
                bean.setnSeg(filament.getnSeg());
                bean.setTempAvg(filament.getTempAvg());
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BeanFilament bean = new BeanFilament();
            bean.setErrorMessage("databse fault");
            beans.add(bean);
        }
        finally {
            return beans;
        }

    }

    public static void main(String args[]) {
       rangeNSeg bean = new rangeNSeg(22, 35);
       searchFilamentByNSeg s = new searchFilamentByNSeg();
       List<BeanFilament> beans;
       beans = s.executeSearch(bean);
       System.out.println(beans.size());
       for(BeanFilament beanFilament : beans) {
           System.out.println(beanFilament);
       }


    }
}
