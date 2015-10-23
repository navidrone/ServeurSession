package swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Random;

import javax.realtime.RealtimeThread;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

import main.ServeurSession;
import rmi.ReleveInt;
import bean.Drone;

public class BoutonMission extends JToggleButton{
	private static final long serialVersionUID = 992194088300423965L;
	private Drone drone;
	private Thread t;
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			Random random = new Random();
			double rangeMin = 12;
			double rangeMax = 15;
	        for(ReleveInt r:drone.getMission().getReleve()){
	        	
	        	try {
	        		if(r.getProfondeur() == null){
						RealtimeThread.sleep(300);
			        	
			        	double profondeur = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
						r.setProfondeur(profondeur);
			        	System.out.println("Releve (id=" + r.getId()+"): "+r.getLattitude()+" "+r.getLongitude() + ", Profondeur: " + r.getProfondeur());
	        		}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        }
	        System.out.println("mission terminee, sauvegarde en base");
	        ServeurSession serveurSession = new ServeurSession();
	        try {
				serveurSession.getFabriqueMission().saveMission(drone.getMission());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
   private ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
		        if(selected){
		        	setText("Arrêter Mission");
	        		//drone.getGestionConnexionDrone().demarrer();
			        System.out.println("Envoi de la commande start!" + "Id drone :" + drone.getId());
			        t = new Thread(runnable);
			        t.start();
				}else{
		    	//drone.getGestionConnexionDrone().arreter();
				  t.stop();
		    	  setText("Relancer Mission");
		      }
	      }
    };
    
	public BoutonMission(int idDrone, FenetreBase fenetre) {
		super();
        //Récupération du drone
        for(Drone drone : fenetre.getServeurSession().getConnexionDrone().getDrones()){
        	if(drone.getId().equals(idDrone)){
        		System.out.println("BoutonControle Drone trouvé!Id drone :" + drone.getId());
        		this.drone = drone;
        	}
        }
		addActionListener(actionListener);
		setBackground(new Color(26, 188, 156));
		setForeground(Color.WHITE);
		setFocusPainted(false);
		setBorder(BorderFactory.createLineBorder(new Color(26, 188, 156),5));
	}
	

}