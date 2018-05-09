package CONTROLLER.parse;

public interface Parser2DB {
    // DB table name for DAO callings
    //usage in parser for eatch line
    public static final String FILAMENT = "FILAMENT";
    public static final String OUTLINE = "OUTLINE";
    public static final String SKELETONPOINT = "SKELETON";
    public static final String STAR = "STAR";
    public static final String[] CSVKINDSTODB = {FILAMENT, OUTLINE, SKELETONPOINT, STAR};
    public static final int[] filamentStrIndx = {1, 7, 8};
    public static final int[] starStrIndx = {1}; //index of string in records in CSVs(only star&Filam)..
    public static final int[] filamentDelColIndx = {7};
    //TODO NOT MAPPED TO FILE BECAUSE SO FEW..
    //comunication string used between boundary-> parser ... standard labelization of sets
    // to import set of CSVs
    public static final String HERSCHEL = "herschel";
    public static final String SPITZER = "spitzer";

    //reused star as communication string...
    public void readCSV(String name, String nameInstrument) throws Exception;

    public void parseStandard(String path, String kindCSV) throws Exception;

    public void parseSatelliteInfo(String path) throws Exception;

    public void parseExternFile(String path, String kindCSV, String nameStr) throws Exception;
}