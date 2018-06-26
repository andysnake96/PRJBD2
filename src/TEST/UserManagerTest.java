package TEST;

/* test per i rquesiti 1, 2 e 3

 */

import DAO.UserDao;
import ENTITY.Instrument;
import ENTITY.Satellite;
import ENTITY.User;
import feauture1.Bean.InstrumentBean;
import feauture1.Bean.SatelliteBean;
import feauture1.Bean.UserBean;
import feauture1.Controller.LoginManager;
import feauture1.Controller.UserManager;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {

    private static final String TYPE = "Administrator";
    private static final String DELETEUSER = "delete from usersistem where username = ?";
    private static final String DELETSATELLITE = "delete from satellite where name = ?";
    private static final String DELETEINSTRUMENT = "delete from instrument where name = ?";
    private final String NAME = "name";
    private final String SURNAME = "surname";
    private final String SEARCHUSER = "select * from usersistem where username = ?";
    private final String SEARCHSATELLITE = "select * from satellite left join partecipation on (name = satellite)  where name = ?  ";
    private final String SEARCHINSTRUMENT = "select * from instrument where name = ? ";
    @Test
    void inserUserCorrecteLogin() {
        UserBean dataUser = null;
        User user = null;
        String msx = null;
        try {
            final String USERID = "userTestOne"; //lunghezza maggiore di 6
            final String PASSWORD = "password"; //lunghezza maggiore di 6
             dataUser = new UserBean(NAME, SURNAME, USERID, PASSWORD, null, "Administrator");
            System.out.println("dati utente da inserire: " + dataUser);
            System.out.println("ricerca utente prima dell'inserimento :");
            user = findUser(dataUser);
            System.out.println(user);
            assert user == null;
            UserManager userManager = new UserManager();
             msx = userManager.addUser(dataUser);
            System.out.println("ricerca utente dopo dell'inserimento :");
            user = findUser(dataUser);
            System.out.println(user + " " + msx);
            assert user != null;
            System.out.println("Prova di login");
            LoginManager login = LoginManager.getIstance();
            String type = login.login(USERID, PASSWORD);
            assert TYPE.equals(type); // controllo se il login restituisce il tipo giusto.
            System.out.println("inserimento dello stesso utente quindi con stesso username: ");
            msx = userManager.addUser(dataUser);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user = findUser(dataUser);
        }
        catch (Exception e) {
            System.err.println("doppia voce nel database, errore!!!");
        }
        System.out.println(msx);
        deleteUser(dataUser.getUsername());



    }

    @Test
    void insertUserIncorrect() {
        User user = null;
        String password = "pass";
        String username = "user";
        UserBean dataUser = new UserBean(NAME, SURNAME, username, password, null, TYPE);
        System.out.println("inserimento con questi dati: "+ dataUser);
        UserManager userManager = new UserManager();
        String msx = userManager.addUser(dataUser);
        System.out.println("dopo tentativo di inserimento: ");
        try {
             user = findUser(dataUser);
             assert user == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(msx);

        username = "userTestTwo";
        password = "password";
        String typeIncorrect = "typeIncorrect";
        dataUser.setUsername(username);
        dataUser.setPassword(password);
        dataUser.setType(typeIncorrect);
        System.out.println("tentativo inserimento con questi dati: "+ dataUser);
        msx = userManager.addUser(dataUser);
        try {
            user = findUser(dataUser);
            assert user == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(msx + " user: "+user);
        System.out.println("tentativo di login: ");
        LoginManager loginManager = LoginManager.getIstance();
        String type = loginManager.login(username, password);
        assert type == null; //login non riuscito.


    }

    @Test
    void insertSatelliteAllData() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String nameSatellite = "testSatellite";
        SatelliteBean satelliteBean = new SatelliteBean(nameSatellite, startDate, endDate);
        System.out.println("dati da inserire: "+ satelliteBean);
        Satellite satellite = searchSatellite(nameSatellite);
        assert satellite == null;
        UserManager userManager = new UserManager();
        String msx = userManager.addSatellite(satelliteBean);
        satellite = searchSatellite(nameSatellite);
        assert satellite != null;
        System.out.println("dopo l'inserimento: "+satellite+" "+ msx);
        deleteSatellite(nameSatellite);


    }

   @Test
   void insertInstrument() {
        String nameSatellite = "Herschel";
        double band[] = {1,2,3};
        String nameStr = "StrTest";
        InstrumentBean instrumentBean = new InstrumentBean(nameStr,band, nameSatellite );
        System.out.println("dati da inserire: " + instrumentBean);
        assert !searchInstrument(nameStr);

        UserManager userManager = new UserManager();
        String msx = userManager.addInstrument(instrumentBean);
        System.out.println(msx);
        assert searchInstrument(nameStr);
        userManager.addInstrument(instrumentBean);
        assert searchInstrument(nameStr);
        deleteInstrument(nameStr);

        //satellite non esistente:
       nameSatellite = "";
       instrumentBean.setSatellite(nameSatellite);
       System.out.println("dati da inserire: " + instrumentBean);
       msx = userManager.addInstrument(instrumentBean);
       System.out.println(msx);
       assert  !searchInstrument(nameStr);
   }

    private void deleteInstrument(String nameStr) {
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(DELETEINSTRUMENT);
            stmt.setString(1, nameStr);
            stmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.closeConn(conn);
        }
    }


    private boolean searchInstrument(String nameStr) {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();

        try {
            String sql = SEARCHINSTRUMENT;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameStr);



            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) { // rs is empty

                return false;
            }
            assert !rs.next();


            rs.close();
            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        return true;

    }


    private void deleteSatellite(String nameSatellite) {
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(DELETSATELLITE);
            stmt.setString(1, nameSatellite);
            stmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.closeConn(conn);
        }
    }


    private Satellite searchSatellite(String nameSatellite) {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();
        Satellite satellite = new Satellite();
        try {
            String sql = SEARCHSATELLITE;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameSatellite);


            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) { // rs is empty

                return null;
            }

            satellite.setName(rs.getString("name"));
            satellite.setStartDate(rs.getDate("startdate").toLocalDate());
            satellite.setEndDate(rs.getDate("enddate").toLocalDate());
            satellite.setType(rs.getString("type"));
            List<String> agencies = new ArrayList<>();

            do {

                if(rs.getString("agency")!= null)
                    agencies.add(rs.getString("agency"));


            } while(rs.next());
           satellite.setAgencies(agencies);



            System.out.println(satellite);
            rs.close();
            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        return satellite;

    }




    private void deleteUser(String username) {
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(DELETEUSER);
            stmt.setString(1, username);
            stmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.closeConn(conn);
        }
    }

    private User findUser(UserBean dataUser) throws Exception {
        PreparedStatement stmt = null;
        DAO.Connection connection = DAO.Connection.getIstance();
        Connection conn = connection.getConn();
        User user = null;
        try {
            String sql = SEARCHUSER;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataUser.getUsername());


            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) { // rs is empty

                return null;
            }

            String name = rs.getString("name");
            String uname = rs.getString("username");
            String pword = rs.getString("password");
            String type = rs.getString("type");
            String email = rs.getString("email");
            String sname = rs.getString("surname");
            if(rs.next()) {
                throw new Exception();
            }

            user = new User(name, sname, uname, pword, email, type);

            rs.close();
            connection.closeConn(conn);
            stmt.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        return user;

    }
        }




