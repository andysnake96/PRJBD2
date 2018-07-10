package CONTROLLER;

import ENTITY.Filament;
import BEAN.InfoFilament;
import BEAN.RangeNSeg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
questa classe controller esegue il requisito 7.
 */

public class searchFilamentByNSeg {

    private List<Filament> filaments; //filamenti che rispettano la specifica sul numero di segmenti

   /*
   questa funzione dopo aver torvato i filamenti richiesti ritornerà una lista di bean che conterranno tutte le
   informazioni utili per l'utente.
    */


    public List<InfoFilament> executeSearch(RangeNSeg range) {
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
            bean.setErrorMessage("database fault");
            beans.add(bean);
        }
        finally {
            return beans;
        }

    }

    public static void main(String args[]) {
       RangeNSeg bean = new RangeNSeg(2, 2);
       searchFilamentByNSeg s = new searchFilamentByNSeg();
       List<InfoFilament> beans;
       beans = s.executeSearch(bean);
       System.out.println(beans.size());
       for(InfoFilament infoFilament : beans) {
           System.out.println(infoFilament);
       }


    }
}
