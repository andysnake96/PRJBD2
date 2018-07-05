package BOUNDARY;

import feauture1.Bean.InstrumentBean;
import feauture1.Bean.ComputeFilamentBean;
import feauture1.Controller.LoginManager;

public class UserLogin {
    private String username;
    private String paswoord;
    private String type;
    private boolean isLog;
    private static String TYPEONE = "Administrator";
    private static String TYPETWO = "User";

    public UserLogin(String username, String paswoord) {
        this.username = username;
        this.paswoord = paswoord;
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
        if(!this.isLog || !this.type.equals(TYPEONE))
            throw new Exception();
        else
            return new UserAdministrator();

    }

    public UserRecorded getUserRecorded() throws Exception {
        if(!this.isLog || !this.type.equals(TYPETWO))
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
