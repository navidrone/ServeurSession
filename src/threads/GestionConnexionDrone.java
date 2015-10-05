package threads;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import rmi.FabriqueMissionInt;
import main.ServeurSession;
import bean.Drone;
import bean.MessageDrone;
import bean.Releve;

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
	
	private Drone drone;
	private OutputStream os;
	private InputStream is;
	private ConnexionDrone connexionDrone;
	private Gson gson;
	private FabriqueMissionInt fabriqueMissionInt;

	public GestionConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters, 
			Drone drone, ConnexionDrone connexionDrone){
		super(priorityParameters,periodicParameters);
		this.drone=drone;
		this.connexionDrone=connexionDrone;
		gson = new GsonBuilder().create();
	}
	
	public void run(){
		//Processus temps réel + priorité
		try {
			ServeurSession serveurSession = new ServeurSession();
			synchronized (serveurSession) {
				fabriqueMissionInt = serveurSession.getFabriqueMission();
			}
			/* priority for new thread: mininum+10 */
			int priority = PriorityScheduler.instance().getMinPriority()+10;
			PriorityParameters priorityParameters = new PriorityParameters(priority);
			/* period: 200ms */
			RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
			PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
			InputStream is = drone.getSocket().getInputStream();
			os = drone.getSocket().getOutputStream();
			is = drone.getSocket().getInputStream();
			//On commence par lire le message d'identification du drone
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String messageDrone = br.readLine();
            MessageDrone message = gson.fromJson(messageDrone, MessageDrone.class);
            //Enregistrement du client
            drone.setId(message.getValeur());
            //Récupération de la mission
            drone.setMission(fabriqueMissionInt.getMission(message.getValeur()));
            envoyerMissionAuDrone();
            while (true) {
                if (is.available() != 0) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(is));
                    message = gson.fromJson(input.readLine(), MessageDrone.class);
                    drone.traiterMessage(message);
                }
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void envoyerMissionAuDrone() {
		Releve
		
	}

	public void envoyerCommande(MessageDrone messageDrone){
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
	    try {
			writer.write(gson.toJson(messageDrone));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void traiterMessageDrone(MessageDrone message){
		if(message.getType() == MessageDrone.RELEVE){
			System.out.println("on enregistre le relevé");
			drone.getMission().getReleve().add(gson.fromJson(message.getReleve(), Releve.class));
		}else if(message.getType() == MessageDrone.COMMANDE){
			System.out.println("on enregistre le relevé");
		}
	}
}