/** \brief The strategies for our Othello Program */
package strategies;

import java.awt.Point;
import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Manageur;
import composants_principaux.Controleur;
import composants_principaux.Vue;

/**
 * \brief
 * An abstract class. Each Strategy should decide a "move" for a computer A.I.
 * @author Rodney Shaghoulian
 */
public abstract class Strategy {
	public Controleur controleur;				///< The Controller in the MVC structure
	public Vue vue;							///< The View in the MVC structure
	public Manageur manageur;		///< Keeps track of Commands for "undos" and "redos"
	
	/**
	 * Constructor - Initializes Variables
	 * @param controleur	Corresponds to the current Othello simulation
	 */
	public Strategy(Controleur controleur){
		this.controleur = controleur;
		this.vue = controleur.vue;
		this.manageur = controleur.manageur;
	}
	
	/**
	 * Does a "move" on a Board. Which "move" is done depends on implemented strategy
	 * @param plateau		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public abstract Plateau move(Plateau plateau);
	
	/**
	 * Get all Boards that can result from all possible moves on this turn.
	 * @param plateau		The current Board before the "move" is performed
	 * @return			The resulting Board after the "move" is performed.
	 */
	public ArrayList<Plateau> getAdjacentBoards(Plateau plateau){
		ArrayList<Plateau> plateaus = new ArrayList<>();
		ArrayList<Point> validMoves;
		if (plateau.playerTurn == Couleur.BLACK)
			validMoves = plateau.blackJoueur.validMoves;
		else
			validMoves = plateau.whiteJoueur.validMoves;
		for (Point move : validMoves){
			Plateau resultingPlateau = new Plateau(plateau);
			Commande commande = new Commande(resultingPlateau, resultingPlateau.playerTurn, move);
			commande.execute();
			plateaus.add(resultingPlateau);
		}
		return plateaus;
	}
	
	/**
	 * Gets Command that converts board into successorBoard
	 * @param plateau				The original Board that we will execute a command on.
	 * @param successorPlateau	The resulting Board after executing a move.
	 * @return					The Command to execute.
	 */
	public Commande getCommand(Plateau plateau, Plateau successorPlateau){
		for (int row = 0; row < plateau.rows; row++){
			for (int col = 0; col < plateau.columns; col++){
				if (plateau.tile[row][col].couleur == Couleur.NONE && successorPlateau.tile[row][col].couleur != Couleur.NONE)
					return new Commande(plateau, successorPlateau.tile[row][col].couleur, new Point(col, row));
			}
		}
		return null; // should never execute
	}
}
