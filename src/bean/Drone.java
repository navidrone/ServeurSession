/**
 * 
 */
package bean;

import java.io.Serializable;
import java.net.Socket;

import rmi.DroneInt;
import rmi.MissionInt;
import threads.GestionConnexionDrone;

/**
 * @author Jullien
 *
 */

public class Drone implements Serializable, DroneInt {
	
	private static final long serialVersionUID = 1L;
	private Socket socket;
	private Integer id;
	private String name;
	private Double lattitude;
	private Double longitude;
	private Mission mission;
	private GestionConnexionDrone gestionConnexionDrone;

	
	
	public Drone(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public Drone(Socket socket, GestionConnexionDrone gestionConnexionDrone) {
		super();
		this.socket = socket;
		this.gestionConnexionDrone = gestionConnexionDrone;
	}

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

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public GestionConnexionDrone getGestionConnexionDrone() {
		return gestionConnexionDrone;
	}

	public void setGestionConnexionDrone(GestionConnexionDrone gestionConnexionDrone) {
		this.gestionConnexionDrone = gestionConnexionDrone;
	}

	public void traiterMessage(MessageDrone message) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}