package feauture1.Boundary;

import feauture1.Bean.InstrumentBean;
import feauture1.Bean.SatelliteBean;
import feauture1.Bean.UserBean;
import feauture1.Controller.UserManager;

public class UserAdministrator extends UserRecorded {

    private UserManager userManager;

    public UserAdministrator() {
        super();
        this.userManager = new UserManager();
    }

    protected String recordUser(UserBean bean) {
        String msx = this.userManager.addUser(bean);
        return msx;
    }

    protected String addSatellite(SatelliteBean bean) {
        String msx = this.userManager.addSatellite(bean);
        return msx;
    }

    public String addInstrument(InstrumentBean bean) {
        String msx = this.userManager.addInstrument(bean);
        return msx;
    }
}
