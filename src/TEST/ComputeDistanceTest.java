package TEST;

import feauture1.Bean.InfoFilament;
import feauture1.Bean.computeFilamentBean;
import feauture1.Controller.ComputeDistance;
import org.junit.jupiter.api.Test;

public class ComputeDistanceTest {


    @Test
    void computeDistanceTest() {
        InfoFilament info;
        ComputeDistance c = new ComputeDistance();
        computeFilamentBean bean = new computeFilamentBean(59, "SPIRE");
        info = c.distanceFromVertex(bean);
        //il filamento esiste
        System.out.println(info);
        assert info.getErrorMessage() == null;
        bean = new computeFilamentBean(-1, "SPIRE");
        info = c.distanceFromVertex(bean);
        System.out.println(info);
        assert info.getErrorMessage() != null;
    }
}
