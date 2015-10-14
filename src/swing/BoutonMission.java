package swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

import bean.Drone;

public class BoutonMission extends JToggleButton{
	private static final long serialVersionUID = 992194088300423965L;
	private Drone drone;
	
   private ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
		        if(selected){
		        	setText("Arrêter Mission");
	        		//drone.getGestionConnexionDrone().demarrer();
			        System.out.println("Envoi de la commande start!" + "Id drone :" + drone.getId());
		      }else{
		    	//drone.getGestionConnexionDrone().arreter();
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