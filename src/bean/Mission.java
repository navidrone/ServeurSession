package bean;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import rmi.DroneInt;
import rmi.MissionInt;
import rmi.ReleveInt;


public class Mission extends UnicastRemoteObject implements Serializable,MissionInt{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Mission() throws RemoteException {
		super();
	}

	private Integer id;
	
	private String name;
    
    /** Attributs renseign�s "� la main" dans la factory */
    private ArrayList<Releve> releve;
    private ArrayList<Drone> flotte;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public List<? extends ReleveInt> getReleve() {
		return releve;
	}

	public void setReleve(List<? extends ReleveInt> releve) {
		this.releve = (ArrayList<Releve>)releve;
	}

	public List<? extends DroneInt> getFlotte() {
		return flotte;
	}

	public void setFlotte(List<? extends DroneInt> flotte) {
		this.flotte = (ArrayList<Drone>)flotte;
	}
	
}
