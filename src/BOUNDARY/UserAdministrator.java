package BOUNDARY;

import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import BEAN.InstrumentBean;
import BEAN.SatelliteBean;
import BEAN.UserBean;
import CONTROLLER.UserManager;

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
    public String importExternCSV(String path,String tableDest,String nameStr)  {
        //import extern csv file into db,return err string (or null)
        String result="";
        Import2DB importer= null;
        String nameSat = new String();


        try {
            importer = new Parser();
        } catch (Exception e) {
            result = e.getMessage();
        }
        try {
            result=importer.parseExternFile(path,tableDest,nameStr);
            result="OK";        //parse block return null for no problems
        } catch (Exception e) {
            result= e.getMessage();

        }
        finally {
            return result;
        }
    }
       public String readSetOfCSV (String groupCSV) throws IllegalArgumentException,Exception {
        /*
        read set of csv demo files (hershel or spitzer) and write into db by multiple parseBlock calls
        groupCSV must match HERSHEL or SPITZER costant string in Import2db interface
         */
            String errMsg="OK";
           Import2DB reader = null;
           try {
               reader = new Parser();
               reader.readCSV(groupCSV,null);  //second field for instrument, null=>default instrument of demo files

           } catch (Exception e) {
               e.printStackTrace();
               errMsg=e.getMessage();
           }
           finally {
               return errMsg;
           }
    };

}
//    public void parseSatelliteInfo(String path) throws Exception;
