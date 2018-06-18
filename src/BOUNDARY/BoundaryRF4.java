package BOUNDARY;

import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import DAO.MyException;

import java.io.IOException;

public class BoundaryRF4 {

    public String importExternCSV(String path,String tableDest,String nameStr) {
        String result="OK";
        Import2DB importer= null;
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
