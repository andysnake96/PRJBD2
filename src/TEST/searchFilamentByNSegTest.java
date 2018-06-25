package TEST;

import DAO.DAOFilament;
import ENTITY.Filament;
import feauture1.Bean.InfoFilament;
import feauture1.Bean.RangeNSeg;
import feauture1.Controller.searchFilamentByNSeg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class searchFilamentByNSegTest {
    Filament filament;

    @BeforeEach
    void takeFilament()  {
        try {
            this.filament = DAOFilament.searchFilamentById(45, "SPIRE");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /*
    questo test controlla che quel filamento stia in una ricerca che comprende il suo numero di segmenti e non sia invece
    in una che non lo comprende.
     */

    @Test
    void testFilamentinList() {
        int nSeg = this.filament.getnSeg();
        RangeNSeg bean = new RangeNSeg(nSeg-1, nSeg+1);
        searchFilamentByNSeg s = new searchFilamentByNSeg();
        List<InfoFilament> beans;
        beans = s.executeSearch(bean);
        System.out.println(beans.size());
        int c = 0;
        for(InfoFilament infoFilament : beans) {
            if(this.filament.getName().equals( infoFilament.getName())) {
                c++; //deve essere uno se le informazioni del filamento sono presenti, essendo il nome identificante.
            }
        }

        assert c ==1;
        bean = new RangeNSeg(nSeg+1, nSeg+3);
        beans = s.executeSearch(bean);
        System.out.println(beans.size());
         c = 0;
        for(InfoFilament infoFilament : beans) {
            if(this.filament.getName().equals( infoFilament.getName())) {
                c++; //deve essere zero se le informazioni del filamento non sono presenti
            }
        }
        assert c == 0;
    }
}
