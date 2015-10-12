package swing;

import java.util.List;

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
				return drones.get(rowIndex).getMission().getName();
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
