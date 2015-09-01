package threads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import bean.MessageDrone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GestionConnexionDrone extends RealtimeThread{
	/**
	 * 
	{
		Je récupère les streams IN et OUT depuis le socket (OK)
		on lit dans la socket IN pour recevoir message d’identification du drone (OK)
		on lance dans un nouveau Thread (3) pour signaler la connection du drône au serveur données (param outPutStream) (OK)
		Crée Thread pour écouter données du drones (Thread 4)
		fini
	}
	 */
	
	
	
	private Socket client;
	private OutputStream os;
	private ConnexionDrone connexionDrone;

	public GestionConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters, 
			Socket client, ConnexionDrone connexionDrone){
		super(priorityParameters,periodicParameters);
		this.client=client;
		this.connexionDrone=connexionDrone;
	}
	
	public void run(){
		try {
			/* priority for new thread: mininum+10 */
			int priority = PriorityScheduler.instance().getMinPriority()+10;
			PriorityParameters priorityParameters = new PriorityParameters(priority);
			/* period: 200ms */
			RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
			PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
			InputStream is = client.getInputStream();
			os = client.getOutputStream();
			//On commence par lire le message d'identification du drone
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String messageDrone = br.readLine();
			Gson gson = new GsonBuilder().create();
            MessageDrone message = gson.fromJson(messageDrone, MessageDrone.class);
            //Enregistrement du client
            connexionDrone.getClients().put(message.getValeur(), client);
            //Récupération 
            NotifConnexionDrone notifConnexionDrone = new NotifConnexionDrone(priorityParameters, periodicParameters);
            notifConnexionDrone.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isTermine() {
		return termine;
	}

	public void setTermine(boolean termine) {
		this.termine = termine;
	}
}
