package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Manageur;
import composants_principaux.Controleur;
import composants_principaux.Vue;

/**
 *
 * Permet au joueur d'annuler son coup
 * @author Peck Patrice Alan
 */
public class GestionnaireAnnuler extends MouseAdapter {
	public Manageur manageur;
	public Plateau plateau;
	public Vue vue;
	
	/**
	 * Constructeur
	 */
	public GestionnaireAnnuler(Controleur controleur){
		this.manageur = controleur.manageur;
		this.plateau = controleur.plateau;
		this.vue = controleur.vue;
	}

	public void mouseClicked(MouseEvent event){
		if (manageur.undoAvailable()){
			do{
				manageur.undo();
				vue.updateView();
			}while(plateau.playerTurn == Couleur.WHITE);
		}
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}
