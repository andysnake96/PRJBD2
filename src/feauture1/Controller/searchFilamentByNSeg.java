package feauture1.Controller;

import ENTITY.Filament;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.rangeNSeg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class searchFilamentByNSeg {

    private List<Filament> filaments;

    public searchFilamentByNSeg() {
        this.filaments = new ArrayList<>();
    }

    public List<InfoFilament> executeSearch(rangeNSeg range) {
        List<InfoFilament> beans = new ArrayList<>();
        try {
            this.filaments =  DAO.DAOFilament.searchByRangeNSeg(range.getnSegMin(), range.getnSegMax());
            for(Filament filament : this.filaments) {
                InfoFilament bean = new InfoFilament();
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
            InfoFilament bean = new InfoFilament();
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
       List<InfoFilament> beans;
       beans = s.executeSearch(bean);
       System.out.println(beans.size());
       for(InfoFilament infoFilament : beans) {
           System.out.println(infoFilament);
       }


    }
}
