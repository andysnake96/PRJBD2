package feauture1.Controller;

import DAO.UserDao;
import ENTITY.User;

public class LoginManager {


    private static LoginManager istance;

    public static synchronized LoginManager getIstance() {
        if (istance == null) {
            istance = new LoginManager();
        }
        return istance;
    }


    public String login(String username, String password) {

        User user = UserDao.findUser(username, password);
        if (user == null) {
            return null;
        } else {
            return user.getType();
        }


    }





}
