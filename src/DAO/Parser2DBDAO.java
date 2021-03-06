package DAO;



import CONTROLLER.parse.Import2DB;
import ENTITY.Outline;
import org.postgresql.PGConnection;
import org.postgresql.jdbc.PgStatement;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.*;
import java.util.List;
import java.util.Properties;
public class Parser2DBDAO {
    /*
    insert strings loaded from confis/insert.properties and completed with records passed
    initFromCSV public method witch wrap write calls
    DB write line per line... called from parser class, not passed all records to no wast too mem
     */
    private java.sql.Connection conn;
    private String insertPropertiesPath="configs/insert_prepared.properties";
    //private String insertPropertiesPath="configs/insert_prepared.properties";
    private Properties insertProp;


    public Parser2DBDAO(Connection conn) throws Exception {
        this.conn= conn;            //rb connection taken from parser
        FileInputStream inserts = new FileInputStream(this.insertPropertiesPath);
        insertProp = new Properties();
        insertProp.load(inserts);    }

    /*
    DAO write in right order fields of records in CSVs, added somewhere namestr column...
    nameStr column setted in attribute of class Parser
     */
    private String sqlString4Line(List<String> record,String nameStr){
      /*    ONTHEFLY SQL STRING VALUES GENERATOR
      create string sql for records inserted... nameStr MUST be null for Filament or Star CSV.. the only ones not need it
       string has to composed with insert base string in inserts.properties file...
       !!!!NB NOT ADDED TERMINATOR !!!
       write (value1,value2,...)
       */
        String outputFields=new String();
        outputFields+="(";
        for (int x=0;x<record.size();x++){
            String field=record.get(x);
            if(field==null)
                continue;
            outputFields+=field;
            if (x!=record.size()-1)
                outputFields+=",";

        }
        if(nameStr!=null)
            outputFields+=","+nameStr;
        outputFields+=")";
        return outputFields;
    }

    private String sqlString4Block(List<List<String>> records,String nameStr) {
        // same of line version up wrapped for block of line version
        //TERMINATOR NOT ADDED!!!
        String outputRecords=new String();
        for (int x=0;x<records.size();x++){
            outputRecords+=this.sqlString4Line(records.get(x),nameStr);
            if(x!=records.size()-1)
                outputRecords+=",";
        }

        return outputRecords;
    }



//    private void writeFilamentOnTheFly(List<String>  records){
//        Statement statement=null;
//        try {
//            statement = conn.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        String sql =this.insertProp.getProperty("insertFilament");
//        sql+="(";
//        for (int x=0;x<records.size();x++){
//            String field=records.get(x);
//            if(field==null)     //cause java not effiticiently remove a element in list retrived from array
//                continue;       //skip a null element setted in list.
//            sql+=records.get(x);        //adding values to string
//            if (x<records.size()-1)
//                sql+=",";
//        }
//        sql+=") ";
//        sql+=this.insertProp.getProperty("conflictFilament")+";";
//        try {
//            statement.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Deprecated
    public void writeFilamentBlockOnTheFly(List<List<String>> records){
        /* NB ::>write same block of records but using sql string generated on the fly...

        same writeFilament, but optimizing writing a block of records
        records:=list of records(list of string)
        sql string created on the fly iterating among fields in list records...
        this calling sqlBlock-->sqlLine private methods
        */
        Statement stmtt=null;
        try {
            stmtt = conn.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql =this.insertProp.getProperty("insertFilament");
        List<String> record;
        String recordsToInsert = sqlString4Block(records,null); // create on the fly sql string...
        //rb nameStr has to be null only for filament and star
        sql+=recordsToInsert;
        sql+=this.insertProp.getProperty("conflictFilament")+";";
        try {
            stmtt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void writeOutline(List<List<String>> records,String nameStr) throws SQLException, MyException {

        //this.checkOutline(records, nameStr);
        String sql = insertProp.getProperty("insertOutlineP");
        PreparedStatement prepS = this.conn.prepareStatement(sql);

        List<String> record=null;
        for (int x = 0; x < records.size(); x++) {
            //(idfil,glon,glat,namestr)
            record=records.get(x);
            prepS.setInt(1,Integer.parseInt(record.get(0)) );       //id
            prepS.setDouble(2,Double.parseDouble(record.get(1)));   //glon
            prepS.setDouble(3,Double.parseDouble(record.get(2)));   //glat
            prepS.setString(4,nameStr);                             //nameStr
            prepS.addBatch();
            prepS.clearParameters();        //ADDED TO BATCH AND CLEARED INSERTED PARAMETERS...
        }
        int[] out=prepS.executeBatch();
        for(int i=0;i<out.length;i++){
            if(out[i]<0)
                System.err.println("errore nell esecuzione del batch posizione "+i);
        }
        //prepS.executeLargeBatch()
    }




    private void writeFilament(List<List<String>> records) throws SQLException {
        String sql = insertProp.getProperty("insertFilament");
        PreparedStatement prepS = this.conn.prepareStatement(sql);
        List<String> record=null;
        for (int x = 0; x < records.size(); x++) {      //iterating among filaments
            record=records.get(x);                      //parsed line in CSV
            prepS.setInt(1,Integer.parseInt(record.get(0)) );       //idFil
            prepS.setString(2,record.get(1) );                      //name
            prepS.setDouble(3,Double.parseDouble(record.get(2)));   //fluxtot
            prepS.setDouble(4,Double.parseDouble(record.get(3)));   //densavg
            prepS.setDouble(5,Double.parseDouble(record.get(4)));   //tempavg
            prepS.setDouble(6,Double.parseDouble(record.get(5)));   //ellipticity
            prepS.setDouble(7,Double.parseDouble(record.get(6)));   //contrast
            prepS.setString(8,record.get(8));                       //nameStr
            prepS.addBatch();
            prepS.clearParameters();        //ADDED TO BATCH AND CLEARED INSERTED PARAMETERS for next iteration
        }
        int[] out=prepS.executeBatch();
        for(int i=0;i<out.length;i++){      //check possible errors in batch exec
            if(out[i]<0)
                System.err.println("errore nell esecuzione del batch posizione "+i);
        }

    }
    private void writeSkeleton(List<List<String>> records,String nameStr) throws SQLException {


        String sql = insertProp.getProperty("insertSkeletonP");
        PreparedStatement prepS = this.conn.prepareStatement(sql);
        List<String> record=null;
        for (int x = 0; x < records.size(); x++) {
            record=records.get(x);
            prepS.setInt(1,Integer.parseInt(record.get(0)) );       //idfil
            prepS.setDouble(2,Integer.parseInt(record.get(1)));     //idseg
            prepS.setString(3,record.get(2) );                      //type (Char)
            prepS.setDouble(4,Double.parseDouble(record.get(3)));   //glon
            prepS.setDouble(5,Double.parseDouble(record.get(4)));   //glat
            prepS.setInt(6,Integer.parseInt(record.get(5)));        //n
            prepS.setDouble(7,Double.parseDouble(record.get(6)));   //flux
            prepS.setString(8,nameStr);                       //nameStr
            prepS.addBatch();
            prepS.clearParameters();        //ADDED TO BATCH AND CLEARED INSERTED PARAMETERS...
        }
        int[] out=prepS.executeBatch();
        for(int i=0;i<out.length;i++){ //execute doc said out vect has return value of batches...
            if(out[i]<0)
                System.err.println("errore nell esecuzione del batch posizione "+i);
        }
        //prepS.executeLargeBatch()
    }
    private void writeStar(List<List<String>> records,String satellite) throws SQLException {
        String sql = insertProp.getProperty("insertStar");
        PreparedStatement prepS = this.conn.prepareStatement(sql);
        List<String> record=null;
        for (int x = 0; x < records.size(); x++) {
            record=records.get(x);
            prepS.setInt(1,Integer.parseInt(record.get(0)) );       //id
            prepS.setString(2,record.get(1) );                      //name (Char)
            prepS.setDouble(3,Double.parseDouble(record.get(2)));   //glon
            prepS.setDouble(4,Double.parseDouble(record.get(3)));   //glat
            prepS.setDouble(5,Double.parseDouble(record.get(4)));   //flux
            prepS.setString(6,record.get(5));                       //type
            prepS.setString(7,satellite);                       //satellite

            prepS.addBatch();
            prepS.clearParameters();        //ADDED TO BATCH AND CLEARED INSERTED PARAMETERS...
        }
        int[] out=prepS.executeBatch();
        for(int i=0;i<out.length;i++){ //execute doc said out vect has return value of batches...
            if(out[i]<0)
                System.err.println("errore nell esecuzione del batch posizione "+i);
        }
        //prepS.executeLargeBatch()
    }


    public void initDBFromCSVBlock(String name,List<List<String>>records,String nameStr,String nameSat) throws SQLException, MyException {
        // wrap write in db based on the kind of the CSV parsed in records...
        //nameStr for the added column... only filament &Star accept nameStr==null
        if (name.equals(Import2DB.OUTLINE) && nameStr!=null) {
            this.writeOutline(records,nameStr);

        }
        else if (name.equals(Import2DB.FILAMENT)){
            //this.writeFilamentBlockOnTheFly(records);        //to check diff of performace...
            this.writeFilament(records);
        }
        else if (name.equals(Import2DB.SKELETONPOINT) && nameStr!=null){
            this.writeSkeleton(records,nameStr);

        }
        else if (name.equals(Import2DB.STAR)){
            this.writeStar(records,nameSat);
        }
        else
            throw new IllegalArgumentException("invalid inputs");
        //caller may catch this exeption and retry with other name of set of csv files to load...


    }

    public void updatenSeg() throws SQLException {
        DAO.Connection connection = DAO.Connection.getIstance();
        String sql = connection.getSqlString("querynseg");
        PreparedStatement stmt =  this.conn.prepareStatement(sql);
        stmt.executeUpdate();
    }

    public void checkConstraints(String nameStr,String kindContraint) throws MyException, SQLException {
        /*
        to check buissnes rules:
            if outline overlap costraint violated => rollback
            if skeleton overlap costraint violated => delete cascade filament related to overlapped sk. points
            (NB in file founded skeleton point overlapped
         roll back will affect only wrong csv table ...
         */
        if(kindContraint.equals(Import2DB.OUTLINE))
            this.checkOutline(nameStr);
        else if(kindContraint.equals(Import2DB.SKELETONPOINT))
            this.checkSkeleton();


    }


    private void checkSkeleton() throws SQLException, MyException {

        DAO.Connection connection = DAO.Connection.getIstance();
        String sql2 = connection.getSqlString("queryconstraintskeleton");
        Statement stmt2 = this.conn.createStatement();
        ResultSet rs2 = stmt2.executeQuery(sql2);
        rs2.next();
        if(rs2.getInt("tot")>0)  {
            System.err.println("VIOLATION OF BUISNESS RULE ! skeletons overlaps");
            String sqlSolveConflict= connection.getSqlString("solveConflictsSkeleton");
            stmt2 = this.conn.createStatement();
            stmt2.executeUpdate(sqlSolveConflict);        //deletted corrupted filaments all data
            //throw new MyException(Import2DB.SKELETONPOINT);

        }
    }
    private void checkOutline( String nameStr) throws SQLException, MyException {

        DAO.Connection connection = DAO.Connection.getIstance();
        String sql = connection.getSqlString("queryconstraintoutlinebis");
        PreparedStatement stmt =  this.conn.prepareStatement(sql);
        stmt.setString(1, nameStr);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        if(rs.getInt("tot")>0)  {
            System.err.println("VIOLATION OF BUISNESS RULE outline overlap skeleton");
            throw new MyException(Import2DB.OUTLINE);
        }
    }


    public void insertSatellite(List<List<String>> records) throws Exception {

        String sql=insertProp.getProperty("insertSatellitePrp");
        PreparedStatement preparedStatement=conn.prepareStatement(sql);
        List<String> record=null;
        for(int x=0;x<records.size();x++){
            record=records.get(x);
            preparedStatement.setString(1,record.get(0));               //name
            preparedStatement.setDate(2,Date.valueOf(record.get(1)));   //dateStart
            preparedStatement.setString(3,record.get(2));               //type
            preparedStatement.setDate(4,Date.valueOf(record.get(3)));   //dateEnd
            preparedStatement.addBatch();
            preparedStatement.clearParameters();

        }
        int[] out= preparedStatement.executeBatch();
        for(int i=0;i<out.length;i++){
            if(out[i]<0)
                throw new MyException("import error dbms");
        }


    }



    public static void main(String[] args) throws Exception {
    }}