import java.net.ServerSocket;
import java.net.Socket;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;


public class NotifConnexionDrone extends RealtimeThread{
	/**
	 * 
		{
			on crée un objet Drone en RMI
			sauvegarde du drone
			puis boucle pour récupérer mission du drône
				sleep
			On envoi la mission au drône
			fin
		}
	 */
	
	
	
	
	
	public NotifConnexionDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters){
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
