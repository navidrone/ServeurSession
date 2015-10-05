package threads;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import main.ServeurSession;
import bean.Drone;

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
		drones.add(new Drone(new Socket()));
		drones.get(0).setId(1);
		serveurSession.getUi().addDroneUI(drones.get(0));
		/* priority for new thread: mininum+10 */
		int priority = PriorityScheduler.instance().getMinPriority()+10;
		PriorityParameters priorityParameters = new PriorityParameters(priority);
		/* period: 200ms */
		RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
		PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
		ServerSocket server = null;
		drones = new ArrayList<Drone>();
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
		    	drone.setGestionConnexionDrone(gestionConnexionDrone);
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
