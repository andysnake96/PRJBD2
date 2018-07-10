package TEST;

import BEAN.InfoFilament;
import BEAN.ComputeFilamentBean;
import CONTROLLER.ComputeDistance;
import org.junit.jupiter.api.Test;

public class ComputeDistanceTest {


    @Test
    void computeDistanceTest() {
        InfoFilament info;
        ComputeDistance c = new ComputeDistance();
        ComputeFilamentBean bean = new ComputeFilamentBean(59, "SPIRE");
        info = c.distanceFromVertex(bean);
        //il filamento esiste
        System.out.println(info);
        assert info.getErrorMessage() == null;
        bean = new ComputeFilamentBean(-1, "SPIRE");
        info = c.distanceFromVertex(bean);
        System.out.println(info);
        assert info.getErrorMessage() != null;
    }
}
