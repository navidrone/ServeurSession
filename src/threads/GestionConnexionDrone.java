package threads;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;

import rmi.FabriqueMissionInt;
import rmi.ReleveInt;
import main.ServeurSession;
import bean.Drone;
import bean.MessageDrone;
import bean.Mission;

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
	/*
	 * type de message Mission (Serveur --> Drone) 
	 * "CODE_MISSION!coord_dep_lat:coord_dep_lon!releve_i_id:releve_i_lat:releve_i_lon;...;...;...!coord_ar_lat:coord_ar_lon\n"
	 * 
	 * type de message Commande (Serveur --> Drone)
	 * "CODE_COMMANDE!CODE_VALEUR_COMMANDE"
	 * 
	 * type de message Releve (Drone --> Serveur) 
	 * "CODE_RELEVE!releve_id:releve_date:releve_profondeur\n"
	 * 
	 */
	private Drone drone;
	private OutputStream os;
	private InputStream is;
	private FabriqueMissionInt fabriqueMissionInt;

	public GestionConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters, 
			Drone drone, ConnexionDrone connexionDrone){
		super(priorityParameters,periodicParameters);
		this.drone=drone;
		this.drone.setGestionConnexionDrone(this);
	}
	
	public void run(){
		//Processus temps réel + priorité
		try {
			System.out.println("Connexion d'un nouveau Drone");
			ServeurSession serveurSession = new ServeurSession();
			synchronized (serveurSession) {
				//fabriqueMissionInt = serveurSession.getFabriqueMission();
			}
			InputStream is = drone.getSocket().getInputStream();
			os = drone.getSocket().getOutputStream();
			is = drone.getSocket().getInputStream();
			//On commence par lire le message d'identification du drone
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String messageDrone = br.readLine();
            //Enregistrement du client
            drone.setId(Integer.parseInt(messageDrone));
            //Récupération de la mission
            drone.setMission(new Mission(fabriqueMissionInt.getMission(drone.getId())));
            envoyerMissionAuDrone();
            serveurSession.getUi().addDroneUI(drone);
            while (true) {
                if (is.available() != 0) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(is));
                    traiterMessageDrone(input.readLine());
                }
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void envoyerMissionAuDrone() throws RemoteException { 
		StringBuffer message = new StringBuffer();
		message.append(MessageDrone.MISSION + "!");
		message.append(drone.getMission().getCoord_dep().getLattitude() + ":");
		message.append(drone.getMission().getCoord_dep().getLongitude() + "!");
		for(ReleveInt releve : drone.getMission().getReleve()){
			message.append(releve.getId() + ":");
			message.append(releve.getLattitude() + ":");
			message.append(releve.getLongitude() + ";");
		}
		message.append("!" + drone.getMission().getCoord_ar().getLattitude() + ":");
		message.append(drone.getMission().getCoord_ar().getLongitude());
		envoyerMessage(message.toString());
	}

	public void envoyerCommande(int valeurCommande){
    	StringBuffer message = new StringBuffer();
    	message.append(MessageDrone.COMMANDE + "!");
    	message.append(valeurCommande);
    	envoyerMessage(message.toString());
	}
	
	public void envoyerMessage(String message){
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
	    try {
			writer.write(message + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void passerEnPiloteManuel(){
		StringBuffer message = new StringBuffer();
    	message.append(MessageDrone.PILOTE_MANUEL);
	}
	
	private void traiterMessageDrone(String message){
		String blocs[] = message.split("!");
		if(blocs != null && blocs.length > 0){
			int action = Integer.parseInt(blocs[0]);
			if(action == MessageDrone.RELEVE){
				//on récupère le releve
				String releveStr[] = blocs[1].split(":");
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss SSS");
					for(ReleveInt releve : drone.getMission().getReleve()){
						//Pour le relevé correspondant
						if(releve.getId() == Integer.parseInt(releveStr[0])){
							//on mets à jour la date
							releve.setDateReleve(formatter.parse(releveStr[1]));
							//et la profondeur
							releve.setProfondeur(Double.parseDouble(releveStr[2]));
							//puis on sauve la mission
							fabriqueMissionInt.saveMission(drone.getMission());
							break;
						}
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(action == MessageDrone.TERMINE){
				System.out.println("on affiche dans l'UI que le drone a terminé");
			}
		}
	}

	public void demarrer() {
    	StringBuffer message = new StringBuffer();
    	message.append(MessageDrone.START + "!");
    	envoyerMessage(message.toString());
	}
}