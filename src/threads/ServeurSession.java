package threads;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import swing.FenetreBase;


public class ServeurSession {
	private static FenetreBase ui;
	private static ConnexionDrone connexionDrone;
	
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
		connexionDrone = new ConnexionDrone(priorityParameters,periodicParameters);
		ui = new FenetreBase();
		connexionDrone.start();
		ui.setVisible(true);
	}

	public FenetreBase getUi() {
		return ui;
	}

	public void setUi(FenetreBase ui) {
		ServeurSession.ui = ui;
	}

	public ConnexionDrone getConnexionDrone() {
		return connexionDrone;
	}

	public void setConnexionDrone(ConnexionDrone connexionDrone) {
		ServeurSession.connexionDrone = connexionDrone;
	}
	
}
