package CONTROLLER.parse;

public interface Import2DB {
    /*
    interface witch  wrap RF4 (= Import CSV) in Parser Class (engineered to be efficient and low memory consumer )
     */
    // DB table name for DAO callings
    //usage in parser for eatch line
    public static final String FILAMENT = "filament";
    public static final String OUTLINE = "outline";
    public static final String SKELETONPOINT = "skeletonpoint";
    public static final String STAR = "star";
    public static final String[] CSVKINDSTODB = {FILAMENT, OUTLINE, SKELETONPOINT, STAR};
    public static final int[] filamentStrIndx = {1, 7, 8};
    public static final int[] starStrIndx = {1}; //index of string in records in CSVs(only star&Filam)..
    public static final int[] filamentDelColIndx = {7};

    //comunication string used between boundary-> parser ... standard labelization of sets
    // to import set of CSVs
    public static final String HERSCHEL = "herschel";
    public static final String SPITZER = "spitzer";

    //read a set of file (demo db CSV )associated to name
    public void readCSV(String name, String nameInstrument) throws Exception;

    public void parseSatelliteInfo(String path) throws Exception;
    //RF4
    public String parseExternFile(String path, String kindCSV, String nameStr) throws Exception;
}