/** \brief The mouse listeners for our Othello Program */
package gestionnaires;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Controleur;
import strategies.Difficulty;

/**
 * \ Permet d'abandonner la partie
 * @author Peck Patrice Alan
 */
public class GestionnaireAbandon extends MouseAdapter {
	public Controleur controleur;
	public Plateau plateau;
	
	/**
	 * Constructeur
	 */
	public GestionnaireAbandon(Controleur controleur){
		this.controleur = controleur;
		this.plateau = controleur.plateau;
	}

	public void mouseClicked(MouseEvent event){
		if (plateau.playerTurn == Couleur.BLACK){
			javax.swing.JOptionPane.showMessageDialog(null, "Abandon du joueur noir. Blanc gagne!");
			controleur.whiteGamesWon++;
		}
		else{
			javax.swing.JOptionPane.showMessageDialog(null, "Abandon du joueur blanc. Noir gagne!");
			controleur.blackGamesWon++;
		}
		controleur.reset(Difficulty.EASY);
	}
}
