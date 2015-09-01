package bean;

import java.io.Serializable;

import rmi.CoordGpsInt;


public class CoordGps implements Serializable, CoordGpsInt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Double lattitude;
	private Double longitude;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLattitude() {
		return lattitude;
	}
	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}