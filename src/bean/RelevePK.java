/**
 * 
 */
package bean;

import java.io.Serializable;


/**
 * @author Jullien
 *
 */
public class RelevePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	

    private Integer missionID;
    
    private Integer coordGpsID;

    public RelevePK() {}

    public RelevePK(Integer missionID, Integer coordGpsID) {
        this.missionID = missionID;
        this.coordGpsID = coordGpsID;
    }

	public Integer getMissionID() {
		return missionID;
	}

	public void setMissionID(Integer missionID) {
		this.missionID = missionID;
	}

	public Integer getCoordGpsID() {
		return coordGpsID;
	}

	public void setCoordGpsID(Integer coordGpsID) {
		this.coordGpsID = coordGpsID;
	}
    
    
	
}
