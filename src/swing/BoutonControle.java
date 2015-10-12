package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import bean.Drone;

public class BoutonControle extends JToggleButton{
	private FenetreBase fenetre;
	private int idDrone;
	
	private Action controler = new AbstractAction()
	{
		private static final long serialVersionUID = -5669139537076132674L;

		public void actionPerformed(ActionEvent e)
	    {
	        System.out.println("Id drone :" + idDrone);
	        //Récupération du drone
	        for(Drone drone : fenetre.getServeurSession().getConnexionDrone().getDrones()){
	        	if(drone.getId().equals(idDrone)){
	        		//drone.getGestionConnexionDrone().demarrer();
			        System.out.println("Envoi de la commande Controle!" + "Id drone :" + idDrone);
			        fenetre.setIdDroneActif(idDrone);
			        fenetre.setCommandeActive(true);
	        		break;
	        	}
	        }
	    }
	};
   private ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        if(selected){
		        System.out.println("Id drone :" + idDrone);
		        setText("Rerendre le mission");
		        //Récupération du drone
		        for(Drone drone : fenetre.getServeurSession().getConnexionDrone().getDrones()){
		        	if(drone.getId().equals(idDrone)){
		        		//drone.getGestionConnexionDrone().demarrer();
				        System.out.println("Envoi de la commande Controle!" + "Id drone :" + idDrone);
				        fenetre.setIdDroneActif(idDrone);
				        fenetre.setCommandeActive(true);
		        		break;
		        	}
		        }
	      }else{
	    	  fenetre.setCommandeActive(false);
	    	  setText("Prendre le contrôle");
	      }
	      }
    };
	public BoutonControle(int idDrone, FenetreBase fenetre) {
		super();
		this.fenetre = fenetre;
		this.idDrone = idDrone;
		addActionListener(actionListener);
		//setAction(controler);
	}
	

}