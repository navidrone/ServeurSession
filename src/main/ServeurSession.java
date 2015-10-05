package main;
import java.awt.BorderLayout;
import java.rmi.Naming;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import rmi.FabriqueMissionInt;
import swing.FenetreBase;
import threads.ConnexionDrone;


public class ServeurSession {
	private static FenetreBase ui;
	private static ConnexionDrone connexionDrone;
	private static FabriqueMissionInt fabriqueMission;
	
	public static void main(String[] args)
	{
		try {
			fabriqueMission =  (FabriqueMissionInt) Naming.lookup("rmi://localhost:1099/FabriqueMission");
		} catch (Exception e) {
		    //e.printStackTrace();
		}
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

	public static FabriqueMissionInt getFabriqueMission() {
		return fabriqueMission;
	}

	public static void setFabriqueMission(FabriqueMissionInt fabriqueMission) {
		ServeurSession.fabriqueMission = fabriqueMission;
	}	
}
