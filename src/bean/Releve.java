/**
 * 
 */
package bean;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import rmi.CoordGpsInt;
import rmi.ReleveInt;

/**
 * @author Jullien
 *
 */


public class Releve extends UnicastRemoteObject implements Serializable, ReleveInt {

	private CoordGpsInt coordGps;
	
	public Releve() throws RemoteException {
		super();
	}
	public Releve(CoordGpsInt coordGps) throws RemoteException {
		super();
		this.coordGps = coordGps;
	}
	private static final long serialVersionUID = 1L;	


	private Double profondeur;

	private Date dateReleve;
	

	public Double getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(Double profondeur) {
		this.profondeur = profondeur;
	}

	public Date getDateReleve() {
		return dateReleve;
	}

	public void setDateReleve(Date dateReleve) {
		this.dateReleve = dateReleve;
	}



	public CoordGpsInt getCoordGps() {
		return coordGps;
	}
	public void setCoordGps(CoordGpsInt coordGps) {
		this.coordGps = coordGps;
	}
	@Override
	public Integer getId() throws RemoteException {
		return this.coordGps.getId();
	}

	@Override
	public void setId(Integer id) throws RemoteException {
		this.coordGps.setId(id);
	}

	@Override
	public Double getLattitude() throws RemoteException {
		return this.coordGps.getLattitude();
	}

	@Override
	public void setLattitude(Double lattitude) throws RemoteException {
		this.coordGps.setLattitude(lattitude);
	}

	@Override
	public Double getLongitude() throws RemoteException {
		// TODO Auto-generated method stub
		return this.coordGps.getLongitude();
	}

	@Override
	public void setLongitude(Double longitude) throws RemoteException {
		this.coordGps.setLongitude(longitude);
	}
	
}
