/**
 * 
 */
package bean;

import java.io.Serializable;
import java.util.Date;

import rmi.CoordGpsInt;
import rmi.ReleveInt;

/**
 * @author Jullien
 *
 */


public class Releve implements Serializable, ReleveInt {

	private static final long serialVersionUID = 1L;	

	private RelevePK relevePk;
	
	private CoordGps coordGps;

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

	public RelevePK getRelevePk() {
		return relevePk;
	}

	public void setRelevePk(RelevePK relevePk) {
		this.relevePk = relevePk;
	}

	public CoordGps getCoordGps() {
		return coordGps;
	}

	public void setCoordGps(CoordGpsInt coordGps) {
		this.coordGps = (CoordGps)coordGps;
	}
	
	
	
	
	
	
}
