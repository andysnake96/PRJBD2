package CONTROLLER.parse;

import DAO.Connection;
import DAO.InstrumentDao;
import DAO.MyException;
import DAO.Parser2DBDAO;
import ENTITY.Instrument;
import TEST.*;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        !compulsory namestr (Instrument in CSV) for every kind except star
        for identifiability in db
        */

        this.satelliteInUse= InstrumentDao.takeSatelliteFromInstrument(nameStr);

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

    public String parseBlock(String path, String kindCSV) throws Exception {

        /*

            PARSE a csv in path, of kindCsv(witch indicate table destination for db)
            and WRITE block of lines in db
             string returned is error msg obtained in writing in db (null if no errors)
            BUISNESS RULE checked and handled at the end
            skeleton buisness rule corrected by eliminating uncorrect data of filament on cascade
            roll back of trasaction associated import of path if violated buisness rule of outline(reported to boundary
            with an errorstring)
            NOT WASTED TOO MEMORY , THAT ACHIVED BY iterating among block of lines
            HP CSV file in path has  at least header line
            ==>parsed records will be written in DB with
            flipped line handled(removed)
            removed in csv spitzer skeleton point referenced to a not existing filament
         */

        String result=null;
        System.out.println("parsing...:\t"+path);
        Connection connClint = Connection.getIstance();
        java.sql.Connection connection = connClint.getConn();


        connection.setAutoCommit(false);  // handle rollback for outline buisness rule violation
        Parser2DBDAO parser2DBDAO = new Parser2DBDAO(connection);
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        line = bufferedReader.readLine(); // HEADER IN FIRST LINE...
        String[] CSVColumns = line.split(this.SPLIT_CHAR);   //header csv
        List<List<String>> blockOfRecords = new ArrayList<>();
        List<String> fields = null;
        int i = 0;
        do {

            line = bufferedReader.readLine();
            if (line != null) {                  //MAYBE CSV FILE WITH NO RECORDS...
                String[] fieldsParsed = line.split(this.SPLIT_CHAR);
                fields = Arrays.asList(fieldsParsed);
                if (fieldsParsed.length != CSVColumns.length) {
                    //  FLIPPED LINE ==> SKIPPED
                    //flipped line check= wrong # of items..

                    System.err.println("error in file\t:" + path +
                            "\nwrong Number of fields in line :" + i +
                            "fields...\t" +
                            Arrays.toString(fieldsParsed) +
                            "but header size is: " + CSVColumns.length +
                            "with columns:\t" + Arrays.toString(CSVColumns));   //WRONG LINE NUM
                    continue;
                } else {          // QUOTING NEEDED ONLY FOR SQL ON THE FLY

                    if (kindCSV.equals(FILAMENT)) {
                        int delCol = filamentDelColIndx[0];
                        fields.set(delCol, null);  //filament table in db does not need column satellite in filament CSV
                    }
                    blockOfRecords.add(fields);    //adding a record (as fixed list) in a block of reco


                }
            }
            i++;    //write in blocks...
            if (i % BLOCKRECORDS == 0) {
                parser2DBDAO.initDBFromCSVBlock(kindCSV, blockOfRecords, this.instrumentInUse,this.satelliteInUse);
                //write block of lines to DB
                blockOfRecords = new ArrayList<>();        // NEEDED TO BE EMPTYED 4 NEXT BLOCK
            }


        }
        while (line != null);
        if (blockOfRecords.size() > 0)
            parser2DBDAO.initDBFromCSVBlock(kindCSV, blockOfRecords, this.instrumentInUse,this.satelliteInUse);
        //empty the block( case CSV #LINES%BLOCKSIZE!=0
        try {
            parser2DBDAO.checkConstraints(instrumentInUse,kindCSV); //BUISNESS RULE CHECK
            if( kindCSV.equals(SKELETONPOINT))                      //for extra col in table filament
                parser2DBDAO.updatenSeg();
            connection.commit();
            connection.setAutoCommit(true);
            connClint.closeConn(connection);
            fileReader.close();
        } catch (MyException e) {
            result="BUISNESS RULE VIOLATION! CORRECTED ELIMINATING FILAMENTS UNCOMPLETE ";
            //in spitzer demo csv there's a buisness rule violation, corrected by deletting corresponding filament on cascade
            if (e.getMessage().equals(OUTLINE)) {
                connection.rollback();
                result="OUTLINE BUISNESS RULE VIOLATION! ROLLING BACK ";
            }
            e.printStackTrace();
            connection.setAutoCommit(true);
            connClint.closeConn(connection);

        }
        return result;  //error string or null
    }


    public void readCSV(String name, String nameInstrument) throws Exception {
        /*
        read a set of file associated with name ( it must be same of public final string (FK ENM)
        in the same order of initPaths.txt
        hershel ==> filament, outline, skeleton,star CSVs loaded...
        spitzer ==> filament, outline, skeleton,star CSVs loaded...
        star    ==> star csv loaded (  1
         */
        List<List<String[]>> output= new ArrayList<>();
        boolean deflt=false;
        if(nameInstrument==null)
            deflt=true;             //if instrument not passed used default interpretation of hershel&Spitzer in CSVs



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

        //return output;
    }

    public static void main(String[] args) throws Exception {
    //NB RIGA 144 0,4,B,272.07536,-20.36854,21,0.53455965 NON HA UN FILAMNETO CORRISPONDENTE,
        //E' CONFORMATA A ID=380 COME RIGHE PRECEDENTI
        Parser parser = new Parser();
        ParserTest.cleanDBWrap();
        long inizio = System.currentTimeMillis();

        parser.readCSV(HERSCHEL,null);
        long fine = System.currentTimeMillis();
        System.out.println((fine-inizio)/60000.0);


    }
}
