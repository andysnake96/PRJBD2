package DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Connection {
    private static Connection istance;
    private static String PASS;
    private static String USER;
    private static String DB_URL;
    private static String DRIVER;

    private final String fileConfig = "configs/config.properties";
    private final String sqlFile = "configs/sqlFile.properties";

    public static synchronized Connection getIstance()  {
        if(istance == null) {
            istance = new Connection();
            return istance;
        }
        else {
            return istance;
        }
    }

    private Connection()  {

        this.getConfig();
    }

    private void getConfig()  {  // take a configuration info of database from file
        Properties properties = null;
        FileInputStream fis = null;
        try {
             fis = new FileInputStream(this.fileConfig);
            properties = new Properties();
            properties.load(fis);
            PASS = properties.getProperty("PASS");
            USER = properties.getProperty("USER");
            DB_URL = properties.getProperty("URL");
            DRIVER = properties.getProperty("DRIVER");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public java.sql.Connection getConn() {
        java.sql.Connection conn = null;
        // aggiungere controllo se la connessione è gia aperta o metterlo come valore di ritorno(coon) ?
        try {
            System.out.println(DRIVER);
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


    } catch (SQLException se) {
        // Errore durante l'apertura della connessione

        se.printStackTrace();
    } catch (Exception e) {
        // Errore nel loading del driver

        e.printStackTrace();
    }
    return conn;
    }


    public void closeConn(java.sql.Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }


    }

    public String getSqlString(String idQuery)  {
        Properties properties = null;
        FileInputStream fis = null;
        String query = null;
        try {
            fis = new FileInputStream(this.sqlFile);
            properties = new Properties();
            properties.load(fis);
            query = properties.getProperty(idQuery);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return query;
    }




    }

