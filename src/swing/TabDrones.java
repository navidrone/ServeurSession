package swing;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import bean.Drone;

public class TabDrones extends AbstractTableModel{
	private final String[] entetes = { "Id", "Mission", "Position", "Contrôle"};
	private List<Drone> drones;
	
	
	public TabDrones(List<Drone> drones) {
		super();
		this.drones = drones;
	}

	@Override
	public int getColumnCount() {
		return entetes.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	@Override
	public int getRowCount() {
		return drones.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {

		case 0:
			// Id
			return drones.get(rowIndex).getId();
		case 1:
			// Mission
			if(drones.get(rowIndex).getMission() != null){
				try {
					return drones.get(rowIndex).getMission().getName();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				return "Charger mission";
			}
		case 2:
			// Position
			return "Afficher";
		case 3:
			// Contrôle
			return "Contrôler";
		default:
			throw new IllegalArgumentException();
		}
	}
}
