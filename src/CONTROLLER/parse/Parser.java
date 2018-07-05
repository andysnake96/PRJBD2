package CONTROLLER.parse;

import DAO.Connection;
import DAO.MyException;
import DAO.Parser2DBDAO;
import TEST.*;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO STATIC CONSTANT FIELDS MOVED TO INTERFACE IMPLEMENTED... ACCESIBLE FROM OUT AND INNERITED AS ATTRIBUTES
public class Parser implements Import2DB {
    /*
    parse set of CSVs files...
    parsed records will be written to db with methods in Parser2DBDAO
    parse a set of files related to ..==> see comunication strings (final static) in interface...
    in constructor called private metod to init PATHs... from initPaths.txt
     needed to load CSVs only by set name...
    :)
     */
    private final int BLOCKRECORDS=5000;
    private final String SPLIT_CHAR=",";
    private List<String[]> hershelPaths;
    private List<String[]> spitzerPaths;
    /*
    file will be parserd and sent to db in line or block of line
    in the same order of initPaths file...
     */

    private String[] starPath;
    private final String initPath="configs/initPaths.txt";
    private String instrumentInUse;
    private String satelliteInUse;

    public Parser() throws IOException {
        hershelPaths=new ArrayList<>();
        spitzerPaths=new ArrayList<>();
        this.setUpFilesPath();
    }
    public  void parseSatelliteInfo(String path) throws Exception {
        /*
        static because do
         */
        List<String> file = Files.readAllLines(Paths.get(path));

        String[] line = null;
        List<List<String>> satelliteRecords = new ArrayList<>();
        for (int l = 1; l < file.size(); l++) { //FIRST LINE IS HEADER
            line = file.get(l).split(",");
//            line[0]=quoteField(line[0]);
//            line[1]=LocalDate.parse(line[1]).toString();
//            line[2]=quoteField(line[2]);
//            line[3]=LocalDate.parse(line[3]).toString();
            satelliteRecords.add(Arrays.asList(line));

        }
        Connection conn=Connection.getIstance();
        java.sql.Connection connection=conn.getConn();
        Parser2DBDAO parser2DBDAO= new Parser2DBDAO(connection);
        parser2DBDAO.insertSatellite(satelliteRecords);
    }

    public String parseExternFile(String path,String kindCSV,String nameStr) throws Exception {
        /*
        RF4 parse an extern file located in path; of kind specified in kindCSV (compatible with db tables
                                                                                (filament,star,outline,skeleton)
        compulsory namestr (Instrument in CSV) for every kind except star
        */

        //this.satelliteInUse= this.takeSatelliteFromInstrument(nameStr);

        if (kindCSV.equals(FILAMENT)) {
            return parseBlock(path, kindCSV);

        } else if (kindCSV.equals(STAR)) {
            return parseBlock(path, kindCSV);

        } else if (kindCSV.equals(OUTLINE)) {
            this.instrumentInUse = nameStr;   //setted instrument in use for the file
            return parseBlock(path, kindCSV);
        }

        else if ((kindCSV.equals(SKELETONPOINT))){
            this.instrumentInUse = nameStr;   //setted instrument in use for the file
            return parseBlock(path,kindCSV);

        }

        else
            return "INVALID Dest Table";

    }
    private void setUpFilesPath() throws IOException {
        /*
        init default set of CSV set from file
        paths will be written in hershelpaths and in spizerpaths KIND,PATHCSV
         */
        String line;
        List<String[]> destList = this.spitzerPaths;
        List<String> fileList= Files.readAllLines(Paths.get(initPath));
        //System.out.println(Arrays.toString(fileList.toArray())); //debug lines...
        //first line star path
        line=fileList.get(0);
        starPath=line.split(SPLIT_CHAR);
        //TODO LIVIO CHECK PASSED INSTRUMENT , if exist..> set nameSat

        String nameSat=null;
        for (int i=1;i< fileList.size();i++){ //first line already passed
            line=fileList.get(i);
            if (line.equals("#H"))      //START OF HESHERL PATHS
                destList=this.hershelPaths;

            else if (line.equals("#S")) //STAR OF SPITZER PATHS
                destList=this.spitzerPaths;
            else{
                destList.add(line.split(SPLIT_CHAR));

            }

        }


    }

    public static String quoteField(String strUnquoted){
        //add single quote to a string witch will be passed to dao witch will write in a simpler way
        return "'"+strUnquoted+"'";
    }
    public String parseBlock(String path, String kindCSV) throws Exception {

        /* to NO WAST TOO MEMORY ACHIVED BY iterating among lines
            lines ( or block of lines will be written to DB with a call named //TODO CALL
            HP CSV file in path has  at least header line
            ==>parsed records will be written in DB with
         */

        //KIND IS CORRECT...
        String result="OK";
        System.out.println("parsing...:\t"+path);
        Connection connClint = Connection.getIstance();
        java.sql.Connection connection = connClint.getConn();


        connection.setAutoCommit(false);  //se nn va bene un vincolo annuliamo tutto
        //getted connection to send write call for eatch line to DB...
        Parser2DBDAO parser2DBDAO = new Parser2DBDAO(connection);
        //retrived DAOWRITE istance
        //List<String[]> lines= new ArrayList<>(); // DEBUG TODO REMOVE
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        line = bufferedReader.readLine(); // HEADER IN FIRST LINE...
        String[] CSVColumns = line.split(this.SPLIT_CHAR);   //header csv
        List<List<String>> blockOfRecords = new ArrayList<>();
        List<String> fields = null;
        int i = 0;

        //RETRIVING STRING INDEX IN RECORDS IN CSV, matching kindOfCsv to indexes in interface.
            /*int[] strIndx;
            //filament has to ignore 1 column...
            if (kindCSV.equals(FILAMENT)) {
                strIndx = filamentStrIndx;
            } else if (kindCSV.equals(STAR))
                strIndx = starStrIndx;
            else
                strIndx = null;   //other CSV not contains strings...*/
        do {

            line = bufferedReader.readLine();
            if (line != null) {                  //MAYBE CSV FILE WITH NO RECORDS...
                String[] fieldsParsed = line.split(this.SPLIT_CHAR);
                fields = Arrays.asList(fieldsParsed);
                if (fieldsParsed.length != CSVColumns.length) {
                    //TODO FLIPPED LINE ==> SKIPPED
                    //flipped line check= wrong # of items..

                    System.err.println("error in file\t:" + path +
                            "\nwrong Number of fields in line :" + i +
                            "fields...\t" +
                            Arrays.toString(fieldsParsed) +
                            "but header size is: " + CSVColumns.length +
                            "with columns:\t" + Arrays.toString(CSVColumns));   //WRONG LINE NUM
                    continue;
                } else {          //NOT FLIPPED LINE...
//                    if (strIndx != null) { //(only some)CSV in parsing has strings to be quoted
//                        for (int x = 0; x < strIndx.length; x++) {
//                            String quotedField = this.quoteField(fields.get(strIndx[x]));
//                            fields.set(strIndx[x], quotedField);
//                            //quoting strings fields..
//                            //TODO QUOTING NEEDED ONLY FOR SQL ON THE FLY
//                        }

                    if (kindCSV.equals(FILAMENT)) {
                        int delCol = filamentDelColIndx[0];
                        fields.set(delCol, null);  //db does not need a column in filament CSV
                    }
                    blockOfRecords.add(fields);    //adding a record (as fixed list) in a block of reco


                }
            }
            i++;    //write in blocks...
            if (i % BLOCKRECORDS == 0) {
                parser2DBDAO.initDBFromCSVBlock(kindCSV, blockOfRecords, this.instrumentInUse,this.satelliteInUse); //write block of lines to DB !!!!!!!!!!!!
                blockOfRecords = new ArrayList<>();        // NEEDED TO BE EMPTYED 4 NEXT BLOCK
            }


        }
        while (line != null);
        if (blockOfRecords.size() > 0)
            parser2DBDAO.initDBFromCSVBlock(kindCSV, blockOfRecords, this.instrumentInUse,this.satelliteInUse);    //empty the block( case CSV #LINES%BLOCKSIZE!=0
        try {
            parser2DBDAO.checkConstraints(instrumentInUse,kindCSV);
            if( kindCSV.equals(SKELETONPOINT))
                parser2DBDAO.updatenSeg();
            connection.commit();
            connection.setAutoCommit(true);
            connClint.closeConn(connection);
            fileReader.close();
        } catch (MyException e) {
            result="BUISNESS RULE VIOLATION! CORRECTED ELIMINATING FILAMENTS UNCOMPLETE ";
            if (e.getMessage().equals(OUTLINE))
                connection.rollback();
            e.printStackTrace();
            connection.setAutoCommit(true);
            connClint.closeConn(connection);

        }
        return result;
    }


    public void readCSV(String name, String nameInstrument) throws Exception {
        //TODO return List<List<String[]>> only for debbuging...

        List<List<String[]>> output= new ArrayList<>();
        /*
        read a set of file associated with name ( it must be same of public final string (FK ENM)
        in the same order of initPaths.txt
        hershel ==> filament, outline, skeleton,star CSVs loaded...
        spitzer ==> filament, outline, skeleton,star CSVs loaded...
        star    ==> star csv loaded (  1
         */

        boolean deflt=false;
        if(nameInstrument==null)
            deflt=true;             //if instrument not passed used default interpretation of hershel&Spitzer in CSVs
        //TODO LIVIO check passed instrument is in DB.instruments
        if (name.equals(Import2DB.HERSCHEL)) {
            this.satelliteInUse="Herschel";
            this.parseBlock(starPath[1],starPath[0]);
            for (int j = 0; j < this.hershelPaths.size(); j++) {
                if (deflt)
                    this.instrumentInUse="SPIRE";
                else
                    this.instrumentInUse=nameInstrument;
                String[] pathTuple = this.hershelPaths.get(j);
                String path=pathTuple[1];
                String kindOfCSV = pathTuple[0];
                this.parseBlock(path,kindOfCSV);


            }
        } else if (name.equals(Import2DB.SPITZER)){
            this.satelliteInUse="Spitzer";
            this.parseBlock(starPath[1],starPath[0]);
            for (int j = 0; j < this.spitzerPaths.size(); j++) {
                if (deflt)
                    this.instrumentInUse="IRAC";
                else
                    this.instrumentInUse=nameInstrument;
                //set instrument used in CSV spitzer... ONLY MIPS USED
                String[] pathTuple = this.spitzerPaths.get(j);
                String path=pathTuple[1];
                String kindOfCSV = pathTuple[0];
                this.parseBlock(path,kindOfCSV);

            }
        } /*else if (name.equals(Import2DB.STAR)){
            this.satelliteInUse="Hershel";
            String[] pathTuple = this.starPath;
            String path=pathTuple[1];
            String kindOfCSV = pathTuple[0];
            this.parseBlock(path,kindOfCSV);

            //output.add (this.parseStandard(path,kindOfCSV));

        }*/
        else
            throw new IllegalArgumentException("invalid (set of) files to parse...\n called with" + name);
        //caller may catch this exeption and retry with other name of set of csv files to load...

        //return output;
    }

    public static void main(String[] args) throws Exception {
    //TODO RIGA 144 0,4,B,272.07536,-20.36854,21,0.53455965 ELIMINATA PERCHE NON C'È ID IN FILAMENT...
        Parser parser = new Parser();
//        parser.readCSV(Import2DB.HERSCHEL);

        //TODO remove these dubug lists... wast a lot of mem
//        List<List<String[]>> listHershel = parser.readCSV(Import2DB.HERSCHEL);
//        List<List<String[]>> listSpitzer = parser.readCSV(Import2DB.SPITZER);
//        List<List<String[]>> listStars = parser.readCSV(Import2DB.STAR);
//        System.out.println(listHershel.size()+listSpitzer.size()+listStars.size());

        // TODO end remove..
        //nb all files togeter size ~
        ParserTest.cleanDBWrap();
        long inizio = System.currentTimeMillis();
        parser.readCSV(SPITZER,null);
        System.out.println("doneSpitzer");
        parser.readCSV(HERSCHEL,null);
        long fine = System.currentTimeMillis();
        System.out.println((fine-inizio)/60000.0);
        //parser.parseBlock("CSV/scheletro_filamenti_Herschel.csv",Import2DB.SKELETONPOINT);
        //TODO IMPORT TEST CASE... LINE IN DB==LINE IN CSV... 11451-1( the header one);
        //parser.parseSatelliteInfo("configs/satellite.txt");
    }
}
