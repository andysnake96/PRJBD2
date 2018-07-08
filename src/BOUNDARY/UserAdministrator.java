package BOUNDARY;

import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import feauture1.Bean.InstrumentBean;
import feauture1.Bean.SatelliteBean;
import feauture1.Bean.UserBean;
import feauture1.Controller.UserManager;

import java.io.IOException;

public class UserAdministrator extends UserRecorded {

    private UserManager userManager;

    public UserAdministrator() {
        super();
        this.userManager = new UserManager();
    }


    public String recordUser(UserBean bean) {
        String msx = this.userManager.addUser(bean);
        return msx;
    }

    public String addSatellite(SatelliteBean bean) {
        String msx = this.userManager.addSatellite(bean);
        return msx;
    }

    public String addInstrument(InstrumentBean bean) {
        String msx = this.userManager.addInstrument(bean);
        return msx;
    }

    // import calls
    public String importExternCSV(String path,String tableDest,String nameStr) {
        String result="OK";
        Import2DB importer= null;
        String nameSat = new String();
        //TODO nameSat <--- query satellite table in db...
        try {
            importer = new Parser();
        } catch (IOException e) {
            result = e.getMessage();
        }
        try {
            result=importer.parseExternFile(path,tableDest,nameStr);
        } catch (Exception e) {
            result= e.getMessage();
        }
        return result;
    }
    //    public String insertSatellite(){};
    //    public String readSetOfCSV(String groupCSV){};
}
