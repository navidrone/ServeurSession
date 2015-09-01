package swing;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreBase extends JFrame{
	
	public FenetreBase(){
		super();
 
		build();//On initialise notre fenêtre
	}
 
	private void build(){
		setTitle("Ma première fenêtre"); //On donne un titre à l'application
		setSize(420,340); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
 
		JLabel label = new JLabel("Panneau de contrôle");
 
		panel.add(label);
		JButton bouton = new JButton("Cliquez ici !");
		panel.add(bouton);
 
		JButton bouton2 = new JButton("Ou là !");
		panel.add(bouton2);
 
		return panel;
	}
}
