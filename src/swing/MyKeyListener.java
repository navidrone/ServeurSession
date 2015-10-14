package swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import bean.MessageDrone;

public class MyKeyListener implements KeyListener {
	private FenetreBase fenetreBase;
	
	
	public MyKeyListener(FenetreBase fenetreBase) {
		super();
		this.fenetreBase = fenetreBase;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Si l'envoi de commandes est activé
		if(fenetreBase.isCommandeActive()){
			switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					fenetreBase.envoyerCommande(MessageDrone.CMD_DROITE);
					break;
				case KeyEvent.VK_LEFT:
					fenetreBase.envoyerCommande(MessageDrone.CMD_GAUCHE);
					break;
				case KeyEvent.VK_UP:
					fenetreBase.envoyerCommande(MessageDrone.CMD_HAUT);		        
					break;
				case KeyEvent.VK_DOWN:
					fenetreBase.envoyerCommande(MessageDrone.CMD_BAS);
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
