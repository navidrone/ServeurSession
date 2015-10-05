package swing;

import java.awt.BorderLayout;
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

import main.ServeurSession;
import bean.Drone;
import threads.ConnexionDrone;

public class FenetreBase extends JFrame{
	public static final int MAX_DRONES = 100;
	private JTable table;
	private DefaultTableModel modele;
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
		container.addTab("Drones", getPanelDrone());
		return container;
	}
	
	public void addDroneUI(Drone drone){
		modele.addRow(new Object[]{
			drone.getId(), "Charger Mission", "Contrôle"
		});;
	}
	
	private JPanel getPanelDrone(){
		JPanel panelDrone = new JPanel();
		panelDrone.setBackground(Color.white);
		panelDrone.setLayout(new BoxLayout(panelDrone, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Drônes disponibles");
		//TabDrones modele = new TabDrones(drones);
		//modele = new TabDrones(serveurSession.getConnexionDrone().getDrones());
		String[] entetes = { "Id", "Mission", "Contrôle"};
		modele = new DefaultTableModel(null, entetes);
		table = new JTable(modele);
		Action chargerMission = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        System.out.println("Id drone :" + ((DefaultTableModel)table.getModel()).getValueAt(modelRow, 0));
		    }
		};
		
		ButtonColumn btnMission = new ButtonColumn(table, chargerMission, 1);
		btnMission.setMnemonic(KeyEvent.VK_D);
		/*ButtonColumn boutonPos = new ButtonColumn(table, delete, 2);
		ButtonColumn btnCtrl = new ButtonColumn(table, delete, 3);*/

		panelDrone.add(label);
		panelDrone.add(table);
		return panelDrone;
	}
	
	public Object[][] getTableModel(List<Drone> drones){
		String[][] data = new String[drones.size()][4];
		System.out.println("Drones size = " + drones.size());
		System.out.println("Data size = " + data.length);
		for(int i= 0; i<drones.size(); i++){
			data[i][0] = drones.get(i).getId().toString();
			data[i][1] = "Charger mission";
			data[i][2] = "contrôler";
		}
		return data;
	}
}