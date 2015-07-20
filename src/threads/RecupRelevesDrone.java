package threads;
import java.net.ServerSocket;
import java.net.Socket;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;


public class RecupRelevesDrone extends RealtimeThread{
	/**
	 * 
	{
		tant que socket ouvert
			je lis dans la socket
			j’identifie la nature du message (block try catch)
			Si relevé
				j’affecte le relevé à l’objet RMI Mission (List<Relevés>)
				je sauvegarde
			fi
	}
	 */
	
	
	
	
	
	public RecupRelevesDrone(PriorityParameters priorityParameters,PeriodicParameters periodicParameters){
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
