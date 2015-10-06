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

	public Releve() throws RemoteException {
		super();
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

	@Override
	public CoordGpsInt getCoordGps() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCoordGps(CoordGpsInt coordGps) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getLattitude() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLattitude(Double lattitude) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double getLongitude() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLongitude(Double longitude) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
