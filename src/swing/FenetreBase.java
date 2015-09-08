package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bean.Drone;
import threads.ConnexionDrone;
import threads.ServeurSession;

public class FenetreBase extends JFrame{
	private JTable table;
	TabDrones modele;
	private ServeurSession serveurSession;
	
	public FenetreBase(){
		super();
		this.serveurSession = new ServeurSession();
		build();//On initialise notre fenêtre
	}
 
	private void build(){
		setTitle("Ma première fenêtre"); //On donne un titre à l'application
		setSize(800,600); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	
	private JTabbedPane buildContentPane(){
		JTabbedPane container = new JTabbedPane();
		
		JPanel panelDrone = new JPanel();
		panelDrone.setBackground(Color.white);
		panelDrone.setLayout(new BoxLayout(panelDrone, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Drônes disponibles");
		//TabDrones modele = new TabDrones(drones);
		modele = new TabDrones(serveurSession.getConnexionDrone().getDrones());
		/*String[] entetes = { "Id", "Mission", "Position", "Contrôle"};
		Object[][] data = {
				{1, "charger mission", "Afficher", "contrôler"},
				{2, "charger mission", "Afficher", "contrôler"}
		};*/
		//DefaultTableModel modele = new DefaultTableModel(data, entetes);
		table = new JTable(modele);
		Action delete = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
				System.out.println("Coucou");
				//JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
		        /*JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);*/
		    }
		};
		 
		ButtonColumn btnMission = new ButtonColumn(table, delete, 1);
		btnMission.setMnemonic(KeyEvent.VK_D);
		/*ButtonColumn boutonPos = new ButtonColumn(table, delete, 2);
		ButtonColumn btnCtrl = new ButtonColumn(table, delete, 3);*/

		panelDrone.add(label);
		panelDrone.add(table);
		container.addTab("Drones", panelDrone);
		
		JPanel panelMaps = new JPanel();
		panelMaps.setBackground(Color.white);		
		container.addTab("Plans",panelMaps);
		
		return container;
	}
	
	public void rafraichir(){
		modele.fireTableDataChanged();
	}
}
