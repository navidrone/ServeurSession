package threads;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import rmi.CoordGpsInt;
import rmi.FabriqueMissionInt;
import rmi.ReleveInt;
import main.ServeurSession;
import bean.Drone;
import bean.Mission;

/**
 * 
{
	je crée mon serverSocket sur un port donnée
	tant que infini
		J’écoute dans l’attente de connexions
		à la connexion d’un drone je crée un Thread 2 dédiée
		je mets à jour la fênetre swing
	fin
}
 */
public class ConnexionDrone extends RealtimeThread{

	private List<Drone> drones;
	private boolean termine;
	private ServeurSession serveurSession;
	public static final int MAX_DRONES = 100;
	private FabriqueMissionInt fabriqueMissionInt;

	public ConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters){
		super(priorityParameters,periodicParameters);
		drones = new ArrayList<Drone>();
		this.serveurSession=new ServeurSession();
	}
	
	public void run()
    {
		try {
			RealtimeThread.sleep(2000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		Drone monDrone = new Drone(new Socket());
		monDrone.setId(1);
    	//System.out.println("Drones.size()(ConnDrone) = " + drones.size());
    	System.out.println("DroneId = " + monDrone.getId());
		synchronized (serveurSession) {
			fabriqueMissionInt = serveurSession.getFabriqueMission();
		}
		try {
			monDrone.setMission(new Mission(fabriqueMissionInt.getMission(monDrone.getId())));
			System.out.println("DroneName : " + monDrone.getName());
			System.out.println("densite : " + monDrone.getMission().getDensite());
			System.out.println("période : " + monDrone.getMission().getPeriode());
			for(ReleveInt r:monDrone.getMission().getReleve()){
				CoordGpsInt c = r.getCoordGps();
				r.setProfondeur(12.0);
				System.out.println("Releve : "+c.getLattitude()+" "+c.getLongitude());
			}
			fabriqueMissionInt.saveMission(monDrone.getMission());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		drones.add(monDrone);
		serveurSession.getUi().addDroneUI(drones.get(0));
		/* priority for new thread: mininum+10 */
		int priority = PriorityScheduler.instance().getMinPriority()+10;
		PriorityParameters priorityParameters = new PriorityParameters(priority);
		/* period: 200ms */
		RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
		PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
		ServerSocket server = null;
		try {
			server = new ServerSocket(5432);
		    for (;;) {//On écoute indéfiniment la connexion de drones
		    	if(termine){//Si application terminé, on sort de la boucle
		    		break;
		    	}
		    	System.out.println("Serveur Session demarré, en attente de connexions");
		    	Socket client = server.accept();
		    	//Un drone se connecte
		    	Drone drone = new Drone(client);
		    	//Un fois que le drone s'est connecté, on crée un Thread propre pour le gérer
		    	GestionConnexionDrone gestionConnexionDrone = 
		    			new GestionConnexionDrone(priorityParameters, periodicParameters, drone, this);
		    	//enregistrement
		    	drones.add(drone);
		    	gestionConnexionDrone.start();
		    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public List<Drone> getDrones() {
		return drones;
	}

	public void setDrones(List<Drone> drones) {
		this.drones = drones;
	}

	public boolean isTermine() {
		return termine;
	}

	public void setTermine(boolean termine) {
		this.termine = termine;
	}
}
