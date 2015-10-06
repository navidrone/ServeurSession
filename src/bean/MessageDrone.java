package bean;

public class MessageDrone {
	
	public static int COMMANDE = 0;
	public static int START = 1;
	public static int STOP = 2;
	public static int MISSION = 3;
	public static int RELEVE = 4;
	public static int PILOTE_MANUEL = 5;
	public static int TERMINE = 6;
	public static int CMD_HAUT = 0;
	public static int CMD_BAS = 1;
	public static int CMD_DROITE = 2;
	public static int CMD_GAUCHE = 3;
	
	private int type;
	private int valeur;
	private String releve;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	public String getReleve() {
		return releve;
	}
	public void setReleve(String releve) {
		this.releve = releve;
	}
	
}
