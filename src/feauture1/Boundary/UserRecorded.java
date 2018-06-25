package feauture1.Boundary;

import feauture1.Bean.*;
import feauture1.Controller.*;

import java.util.ArrayList;
import java.util.List;

public class UserRecorded {

    private ComputeDistance computeDistance;
    private ComputeFilament computeFilament;
    private searchFilamentByNSeg searchFilamentByNSeg;
    private StarInFilamentAndInRectangle starInFilamentAndInRectangle;
    private final int DIMRANGE = 3;

    protected InfoFilament recoveryInfoFilament(computeFilamentBean bean) {
        this.computeFilament = new ComputeFilament();
        return this.computeFilament.computeFilament(bean);
    }

    protected List<InfoFilament> searchFilamentNSeg(RangeNSeg range) {
        List<InfoFilament> infos = new ArrayList<>();
        if(range.getnSegMax() < range.getnSegMin()) {
            InfoFilament infoError = new InfoFilament();
            infoError.setErrorMessage("incorrect range");
            infos.add(infoError);
            return infos;
        }
        if(range.getnSegMax()-range.getnSegMin() < DIMRANGE) {
            InfoFilament infoError = new InfoFilament();
            infoError.setErrorMessage("The interval must be at least "+ DIMRANGE+" in size" );
            infos.add(infoError);
            return infos;
        }
        this.searchFilamentByNSeg = new searchFilamentByNSeg();
        infos = this.searchFilamentByNSeg.executeSearch(range);
        return infos;
    }

    protected InfoStarInFilamentAndRectangle starInFormationInRectangle(Rectangle rectangle) {
        InfoStarInFilamentAndRectangle info;
        this.starInFilamentAndInRectangle = new StarInFilamentAndInRectangle();
        info = this.starInFilamentAndInRectangle.execute(rectangle);
        return info;
    }

    protected InfoFilament distanceVertexOutline(computeFilamentBean bean) {
        InfoFilament info;
        this.computeDistance = new ComputeDistance();
        info = this.computeDistance.distanceFromVertex(bean);
        return info;
    }
}
