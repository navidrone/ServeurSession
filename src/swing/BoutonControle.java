package swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import bean.Drone;

public class BoutonControle extends JToggleButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FenetreBase fenetre;
	private Drone drone;
	
   private ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        //Passage en mode manuel
	        if(selected){
		        setText("Rerendre le mission");
        		//drone.getGestionConnexionDrone().passerEnPiloteManuel();
		        System.out.println("Envoi de la commande Controle!" + "Id drone :" + drone.getId());
		        fenetre.setIdDroneActif(drone.getId());
		        fenetre.setCommandeActive(true);
	      }else{//On redemarre la mission
	    	  fenetre.setCommandeActive(false);
	    	  //drone.getGestionConnexionDrone().demarrer();
	    	  setText("Prendre le contrôle");
	      }
	      }
    };
	public BoutonControle(int idDrone, FenetreBase fenetre) {
		super();
		this.fenetre = fenetre;
		//Récupération du drone
        for(Drone drone : fenetre.getServeurSession().getConnexionDrone().getDrones()){
        	if(drone.getId().equals(idDrone)){
        		this.drone = drone;
        		System.out.println("BoutonControle Drone trouvé!Id drone :" + drone.getId());
        		break;
        	}
        }
		setBackground(new Color(26, 188, 156));
		setForeground(Color.WHITE);
		setFocusPainted(false);
		setBorder(BorderFactory.createLineBorder(new Color(26, 188, 156),5));
		addActionListener(actionListener);
	}
	

}