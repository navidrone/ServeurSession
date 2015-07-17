import java.net.ServerSocket;
import java.net.Socket;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;


public class GestionConnexionDrone extends RealtimeThread{
	/**
	 * 
	{
		Je récupère les streams IN et OUT depuis le socket
		on lit dans la socket IN pour recevoir message d’identification du drone
		on lance dans un nouveau Thread (3) pour signaler la connection du drône au serveur données (param outPutStream)
		Crée Thread pour écouter données du drones (Thread 4)
		fini
	}
	 */
	
	
	
	
	
	public GestionConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters){
		super(priorityParameters,periodicParameters);
	}
	
	public void run()
      {
		ServerSocket server = null;
		server = new ServerSocket(5432);
	    for (;;) {
	    	Socket client = server.accept();
	    	new Thread(new DoSomethingWithInput(clientSocket)).start();
	    }
		
        /*for (int n=1;n<10;n++)
        {
          waitForNextPeriod();
          System.out.println("Coucou " + n);
        }*/
      }
}
