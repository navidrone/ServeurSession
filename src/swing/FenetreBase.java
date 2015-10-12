package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.ServeurSession;
import bean.Drone;

public class FenetreBase extends JFrame{
	/**
	 * FOND rgb(44, 62, 80)
	 */
	private static final long serialVersionUID = 1L;
	private static final int NB_DRONES = 10;
	private ServeurSession serveurSession;
	private boolean isCommandeActive;
	private int idDroneActif;
	private JPanel[][] panelHolder;
	private int[] corresDronePanel = new int[NB_DRONES];

	
	public FenetreBase(){
		super();
		this.serveurSession = new ServeurSession();
		build();//On initialise notre fenêtre
		isCommandeActive = false;
	}
 
	private void build(){
		setTitle("Serveur Session (RT) - Drones connectés"); //On donne un titre à l'application
		setSize(800,600); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		panelHolder = new JPanel[NB_DRONES][3];   
		setLayout(new GridLayout(NB_DRONES,3));
		String[] entetes = { "Id Drône", "Mission", "Contrôle"};
		//Ajout des panels
		for(int m = 0; m < NB_DRONES; m++) {
			for(int n = 0; n < 3; n++) {
				panelHolder[m][n] = new JPanel();
				panelHolder[m][n].setOpaque(true);
				panelHolder[m][n].setAlignmentY(JPanel.CENTER_ALIGNMENT);
				if(m == 0){
					JLabel entete = new JLabel(entetes[n]);
					entete.setForeground(Color.WHITE);
					panelHolder[m][n].add(entete);
					panelHolder[m][n].setBackground( new Color(93, 93, 93) );
				}
				if(m % 2 == 0 && m > 0){
					panelHolder[m][n].setBackground( new Color(219, 219, 219) );
				}else if(m > 0){
					panelHolder[m][n].setBackground( new Color(237, 237, 237) );
				}
				add(panelHolder[m][n]);
			}
		}
	}
	
	public void addDroneUI(Drone drone){
		System.out.println("Ajout drone");
		BoutonMission boutonMission = new BoutonMission(drone.getId(), this);
		BoutonControle boutonControle = new BoutonControle(drone.getId(), this);
		boutonControle.addKeyListener(new MyKeyListener(this));
		boutonMission.setText("Lancer Mission");
		boutonControle.setText("Prendre le contrôle");
		for(int i = 1; i<corresDronePanel.length; i++){
			//ligne libre
			if(corresDronePanel[i] == 0){
				System.out.println("i = " + i);
				panelHolder[i][0].add(new JLabel(drone.getId() + ""));
				panelHolder[i][0].revalidate();
				panelHolder[i][1].add(boutonMission);	
				panelHolder[i][1].revalidate();
				panelHolder[i][2].add(boutonControle);
				panelHolder[i][2].revalidate();
				corresDronePanel[i] = drone.getId();
				break;
			}
		}
	}

	public void envoyerCommande(int valeur) {
        System.out.println("Envoi de la commande au Thread: " + valeur);
		//envoiCommande
	}

	public boolean isCommandeActive() {
		return isCommandeActive;
	}

	public void setCommandeActive(boolean isCommandeActive) {
		this.isCommandeActive = isCommandeActive;
	}

	public ServeurSession getServeurSession() {
		return serveurSession;
	}

	public void setServeurSession(ServeurSession serveurSession) {
		this.serveurSession = serveurSession;
	}

	public int getIdDroneActif() {
		return idDroneActif;
	}

	public void setIdDroneActif(int inDroneActif) {
		this.idDroneActif = inDroneActif;
	}	
	
	
}