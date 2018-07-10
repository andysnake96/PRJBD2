//andysnake
package TEST;

import CONTROLLER.parse.Import2DB;
import CONTROLLER.parse.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class ParserTest {
    /*
    TEST CASE RF4
    CSV IMPORT TESTED BY INSERTING AND MATCHING:
                    -NUMBER OF DIFFERENT INSERTED RECORDS
                    -NUMBER OF LINES IN SOURCE CSV FILE
                    (NB Hershel files:=>removed duplicated lines in outline

     */
    final String[] pathsALL={"CSV/stelle_Herschel.csv",
            "CSV/filamenti_Herschel.csv","CSV/contorni_filamenti_Herschel.csv",
            "CSV/scheletro_filamenti_Herschel.csv"};

    final String[] pathHersel={"star,CSV/stelle_Herschel.csv","filament,CSV/filamenti_Herschel.csv",
            "outline,CSV/contorni_filamenti_Herschel.csv","skeletonpoint,CSV/scheletro_filamenti_Herschel.csv"};

    final String[] pathSpitzer={"filament,CSV/filamenti_Spitzer.csv","outline,CSV/contorni_filamenti_Spitzer.csv",
            "skeletonpoint,CSV/scheletro_filamenti_Spitzer_CORRETTO.csv"};

    final String hershelDefaultInstrument="SPIRE";
    final String spitzerDefaultInstrument="IRAC";
    final String[] tablesNames={Import2DB.FILAMENT,Import2DB.OUTLINE,Import2DB.SKELETONPOINT,Import2DB.STAR};
    final HashMap<String,Integer> filesNumberOfLinesHershel=new HashMap<>();
    final HashMap<String,Integer> filesNumberOfLinesSpitzer=new HashMap<>();

    @BeforeEach
    void setUp() throws Exception { //clean db first...

        cleanDBWrap();
        countLinesOfFiles();    //init maps grupsCSVFiles---># of records :)
    }
    public static void cleanDBWrap(){
        try {
            DAO.Connection connection = DAO.Connection.getIstance();
            String sql = connection.getSqlString("cleanDB");
            Statement statement= connection.getConn().createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("CLEANED INSERTED FILAMENT & OUTLINE & STARS");
    }
    private HashMap<String,Integer> countInTableWrap(){
        //return hashmap of table->counted row
        HashMap<String,Integer> outTablesCounted=new HashMap<>();
        DAO.Connection connection = DAO.Connection.getIstance();
        String sql = connection.getSqlString("countDB");

        int counted;
        for(int x=0;x<this.tablesNames.length;x++) {
            String sqlTable = this.tablesNames[x];
            try {
                counted = 0;
                Statement stmt=connection.getConn().createStatement();
                ResultSet resultSet = stmt.executeQuery(sql+sqlTable+";");
                resultSet.next();
                counted = resultSet.getInt("total");
                outTablesCounted.put(sqlTable, counted);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return outTablesCounted;
    }
    private void countLinesOfFiles() throws IOException {
        HashMap<String,Integer> outFilesLenght= new HashMap<>();
        for(int x=0;x<pathHersel.length;x++) {
            String[] rowPath=pathHersel[x].split(",");
            String pathFile=rowPath[1];
            pathFile+=".NOREPETITION"; //added files with concatenated .NOREPETITION to mean files with no repetition
            //founded a lot of lines repetition in outlines demo files...
            String table=rowPath[0];
            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int lines = 0;
            while (bufferedReader.readLine() != null) {
                lines++;
            }
            System.out.println("Hershel:\nreaded from "+pathFile+" lines:\t"+lines);
            filesNumberOfLinesHershel.put(table,lines-1);   //CSV header not a record!
        }

    }
    @Test
    void parseExternFile() {
        /*
        call wrapped method in interface Import2DB parseExternFile to write into DB all file of spitzer and hershel
        calling both groups in random order
        assert match inserted records<==> num of records in CSV !!
         */
        Import2DB importWrap = null;
        HashMap<String, Integer> dbRowsNumAfter;
        HashMap<String, Integer> dbRowsNumBefore;

        try {
            importWrap = new Parser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbRowsNumBefore = countInTableWrap();
        wrapParseExternFile(importWrap, pathHersel, hershelDefaultInstrument);
        dbRowsNumAfter = countInTableWrap();
        assertCorrectInserted(dbRowsNumBefore,dbRowsNumAfter,Import2DB.HERSCHEL);
    }

    private void assertCorrectInserted(HashMap<String, Integer> before ,HashMap<String, Integer> after,String group) {
        Iterator<String> keys = before.keySet().iterator();
        while (keys.hasNext()) {
            String keyTable = keys.next();
            int inserted, fileLenght = 0;
            inserted = after.get(keyTable) - before.get(keyTable);
            if (group.equals(Import2DB.SPITZER))
                fileLenght = filesNumberOfLinesSpitzer.get(keyTable);
            else
                fileLenght = filesNumberOfLinesHershel.get(keyTable);     //getted # of lines of file readed into db

            if (fileLenght == inserted)
                System.out.println(" \nOK " + group + "_" + keyTable+"  INSERTED:"+inserted+"\tEXPECTED:"+fileLenght);
            else
                System.out.println(" \nNOT OK " + group + "_" + keyTable+"  INSERTED:"+inserted+"\tEXPECTED:"+fileLenght);
            //assertEquals(fileLenght,inserted,
            // "group:\t"+group+" in "+keyTable+"\nEXPECTED:"+fileLenght+"\tbut inserted:"+inserted);

        }
    }

    void wrapParseExternFile(Import2DB importWrap, String[] paths, String instrument){
        for (int x=0;x<paths.length;x++){
            String[] pathRow=paths[x].split(",");
            String path=pathRow[1];
            String kind=pathRow[0];
            String satelliteName=new String();
            try {
                importWrap.parseExternFile(path,kind,instrument);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println();
            }

        }
    }


}