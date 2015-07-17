import javax.realtime.Clock;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;


public class ServeurSession {
	
	public static void main(String[] args)
	{
		Clock clock = Clock.getRealtimeClock();
		long ms = clock.getTime().getMilliseconds();
		int ns = clock.getTime().getNanoseconds();
		if (ms < 0 || ns < 0 || ns > 999999) {
			System.out.println("Absolute time values are incorrect. Please set the system clock!");
			System.out.println("Got 'ms: " + ms + "' - 'ns: " + ns + "'");
			System.exit(1);
		}
	
		/* priority for new thread: mininum+10 */
		int priority = PriorityScheduler.instance().getMinPriority()+10;
		PriorityParameters priorityParameters = new PriorityParameters(priority);
		
		/* period: 200ms */
		RelativeTime period = new RelativeTime(200 /* ms */, 0 /* ns */);
		
		/* release parameters for periodic thread: */
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
		ConnexionDrone connexionDrone = new ConnexionDrone(priorityParameters,periodicParameters);
		/* start periodic thread: */
		connexionDrone.start();
		realtimeThread.start();
	}
}
