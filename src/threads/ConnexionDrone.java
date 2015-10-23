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
		/**
		 * POUR DEMO DEBUT
		 */

		try {
			//Ajout Drone 1
			RealtimeThread.sleep(2000);
			Drone drone1 = new Drone(new Socket());
			drone1.setId(1);
	    	//System.out.println("Drones.size()(ConnDrone) = " + drones.size());
	    	System.out.println("DroneId = " + drone1.getId());
			synchronized (serveurSession) {
				fabriqueMissionInt = serveurSession.getFabriqueMission();
			}
			drone1.setMission(new Mission(fabriqueMissionInt.getMission(drone1.getId())));
			System.out.println("Récupération de la mission pour Drone id 1");
			int i = 0;
			for(ReleveInt r:drone1.getMission().getReleve()){
				i++;
				//System.out.println("Releve : "+r.getLattitude()+" "+r.getLongitude());
			}
			System.out.println("Envoi Mission " + drone1.getMission().getId() + " (nom: " + drone1.getMission().getName() + ", points de relevés: " + i + ")");
			fabriqueMissionInt.saveMission(drone1.getMission());
			drones.add(drone1);
			serveurSession.getUi().addDroneUI(drones.get(0));
			//Ajout Drone 2
			RealtimeThread.sleep(2000);
			Drone drone2 = new Drone(new Socket());
			drone2.setId(2);
	    	//System.out.println("Drones.size()(ConnDrone) = " + drones.size());
	    	System.out.println("DroneId = " + drone2.getId());
			drone2.setMission(new Mission(fabriqueMissionInt.getMission(drone2.getId())));
			i = 0;
			for(ReleveInt r:drone2.getMission().getReleve()){
				i++;
				//System.out.println("Releve : "+r.getLattitude()+" "+r.getLongitude());
			}
			System.out.println("Envoi Mission " + drone2.getMission().getId() + " (nom: " + drone2.getMission().getName() + ", points de relevés: " + i + ")");
			fabriqueMissionInt.saveMission(drone2.getMission());
			drones.add(drone2);
			serveurSession.getUi().addDroneUI(drones.get(1));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		/**
		 * POUR DEMO FIN
		 */
		/* priority for new thread: mininum+10 */
		int priority = PriorityScheduler.instance().getMinPriority()+5;
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
