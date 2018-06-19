package feauture1.Controller;

import DAO.InstrumentDao;
import DAO.SatelliteDao;
import DAO.UserDao;
import feauture1.Bean.InstrumentBean;
import feauture1.Bean.SatelliteBean;
import feauture1.Bean.UserBean;

public class UserManager {  //relativo al requisito 3, può farlo solo l'amministartore


    public String addUser(UserBean user) {
        String msx = null;
        if((msx =dataControl(user.getUsername(), user.getPassword())) != null){
            return msx;
        }
        msx = UserDao.addUser(user);
        return msx;
    }

    /*
    questa funzione controlla i vincoli sull'username e password
     */
    private String dataControl(String username, String password) {
        if(password.length() < 6 && username.length() < 6) {
            return "password and username not valid, they must have at least 6 chars";
        }
        else if(password.length() < 6 ) {
            return "password  not valid, it must have at least 6 chars";
        }
        else if (username.length() < 6) {
            return "username not valid, it must have at least 6 chars";
        }
        else {
            return null;
        }
    }

    /*
    Funzione per inserire un satellite
     */

    public String addSatellite(SatelliteBean satelliteBean) {
        String msx = SatelliteDao.addSatellite(satelliteBean);
        return msx;

    }

    public static void main(String args[]) {
        UserManager um = new UserManager();
        InstrumentBean ib = new InstrumentBean();
        ib.setName("dfc");
        ib.setSatellite("Spitzer");
        double d[] = {1.2,1.3,1.4};
        ib.setBand(d);
        System.out.println(um.addInstrument(ib));
    }

    /*
    funzione per inserire un nuovo strumento nel DB
     */
    private String addInstrument(InstrumentBean ib) {
        String msx = InstrumentDao.addInstrument(ib);
        return msx;
    }


}
