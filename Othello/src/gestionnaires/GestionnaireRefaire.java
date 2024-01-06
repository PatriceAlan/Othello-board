package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Manageur;
import composants_principaux.Controleur;
import composants_principaux.Vue;

/**
 * \brief
 * Enables a Player to "redo" a move
 * @author Rodney Shaghoulian
 */
public class GestionnaireRefaire extends MouseAdapter {
	public Manageur manageur;	///< Keeps track of undos and redos
	public Plateau plateau;						///< The Board we are playing Othello on.
	public Vue vue;						///< The View of the Board
	
	/**
	 * Constructor that saves CommandManager information
	 * @param controleur	The Controller that has access to CommandManager
	 */
	public GestionnaireRefaire(Controleur controleur){
		this.manageur = controleur.manageur;
		this.plateau = controleur.plateau;
		this.vue = controleur.vue;
	}
	
	/**
	 * Redos a move if a redo is available. Beeps otherwise
	 */
	public void mouseClicked(MouseEvent event){
		if (manageur.redoAvailable()){
			do{
				manageur.redo();
				vue.updateView();
			}while(plateau.playerTurn == Couleur.WHITE);
		}
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}