import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import swing.FenetreBase;
import threads.ConnexionDrone;


public class ServeurSession {
	
	public static void main(String[] args)
	{
		int priority = PriorityScheduler.instance().getMinPriority()+10;
		PriorityParameters priorityParameters = new PriorityParameters(priority);
		RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
		PeriodicParameters periodicParameters = new PeriodicParameters(null,period, null,null,null,null);
		RealtimeThread realtimeThread = new RealtimeThread(priorityParameters,periodicParameters)
		{
			public void run()
			{
				for (int n=1;n<50;n++)
				{
					waitForNextPeriod();
					System.out.println("Hello " + n);
				}
			}
		};
		FenetreBase fenetre = new FenetreBase();
		fenetre.setVisible(true);//On la rend visible
		ConnexionDrone connexionDrone = new ConnexionDrone(priorityParameters,periodicParameters);
		connexionDrone.start();
	}	
}
