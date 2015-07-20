package threads;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

/**
 * 
{
	je crée mon serverSocket sur un port donnée
	tant que infini
		J’écoute dans l’attente de connexions
		à la connexion d’un drone je crée un Thread 2 dédiée
	fin
}
 */
public class ConnexionDrone extends RealtimeThread{

	private Map<Integer,Socket> clients;
	
	public ConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters){
		super(priorityParameters,periodicParameters);
	}
	
	public void run()
    {
		/* priority for new thread: mininum+10 */
		int priority = PriorityScheduler.instance().getMinPriority()+10;
		PriorityParameters priorityParameters = new PriorityParameters(priority);
		/* period: 200ms */
		RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
		PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
		ServerSocket server = null;
		clients = new HashMap<Integer, Socket>();
		try {
			server = new ServerSocket(5432);
		    for (;;) {//On écoute indéfiniment la connexion de drones
		    	System.out.println("Serveur Session demarré, en attente de connexions");
		    	Socket client = server.accept();
		    	//Un fois que le drone s'est connecté, on crée un Thread propre pour le gérer
		    	GestionConnexionDrone gestionConnexionDrone = 
		    			new GestionConnexionDrone(priorityParameters, periodicParameters, client, this);
		    	gestionConnexionDrone.start();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public Map<Integer, Socket> getClients() {
		return clients;
	}

	public void setClients(Map<Integer, Socket> clients) {
		this.clients = clients;
	}
	
	
}
