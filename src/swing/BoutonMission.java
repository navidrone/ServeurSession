package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import bean.Drone;

public class BoutonMission extends JToggleButton{
	private static final long serialVersionUID = 992194088300423965L;
	private FenetreBase fenetre;
	private int idDrone;
	
   private ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
		        if(selected){
		        	setText("Arrêter Mission");
			        System.out.println("Id drone :" + idDrone);
			        //Récupération du drone
			        for(Drone drone : fenetre.getServeurSession().getConnexionDrone().getDrones()){
			        	if(drone.getId().equals(idDrone)){
			        		//drone.getGestionConnexionDrone().controler();
					        System.out.println("Envoi de la commande start!" + "Id drone :" + idDrone);
			        		break;
			        	}
			        }
		      }else{
		    	  setText("Relancer Mission");
		      }
	      }
    };
	public BoutonMission(int idDrone, FenetreBase fenetre) {
		super();
		this.fenetre = fenetre;
		this.idDrone = idDrone;
		addActionListener(actionListener);
		setBackground(new Color(26, 188, 156));
		setForeground(Color.WHITE);
		setFocusPainted(false);
		setBorder(BorderFactory.createLineBorder(new Color(26, 188, 156),5));
	}
	

}