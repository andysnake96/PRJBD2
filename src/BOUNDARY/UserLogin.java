package BOUNDARY;

import BEAN.InstrumentBean;
import BEAN.ComputeFilamentBean;
import CONTROLLER.LoginManager;

public class UserLogin {
    private String username;
    private String paswoord;
    private String type;
    private boolean isLog;
    public static String TYPEADMIN = "Administrator";
    public static String TYPEUSER = "User";

    public UserLogin(String username, String paswoord) {
        this.username = username;
        this.paswoord = paswoord;
    }

    public String getType() {
        return type;
    }

    public boolean loggin() {
        if ((this.type = LoginManager.getIstance().login(this.username, this.paswoord)) != null) {
            this.isLog = true;

            return true;
        } else
            return false;
    }

    public void loggout() {
        this.isLog = false;
        this.username = null;
        this.paswoord = null;
        this.type=null;
    }

    public UserAdministrator getUserAdministrator() throws Exception {
        if(!this.isLog || !this.type.equals(TYPEADMIN))
            throw new Exception();
        else
            return new UserAdministrator();

    }

    public UserRecorded getUserRecorded() throws Exception {
        if(!this.isLog )
            throw new Exception();
        else
            return new UserRecorded();
    }



    public static void main(String args[]) {
        UserLogin u = new UserLogin("tira", "tira");
        System.out.println(u.loggin());
        InstrumentBean ib = new InstrumentBean();
        ib.setName("dfcx");
        ib.setSatellite("Spitzer");
        double d[] = {1.2,1.3,1.4};
        ib.setBand(d);
        System.out.println();
        UserAdministrator ur = null;
        try {
            ur = u.getUserAdministrator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ComputeFilamentBean bean = new ComputeFilamentBean(59, "SPIRE");
        System.out.println(ur.recoveryInfoFilament(bean));
    }
}
