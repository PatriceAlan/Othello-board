package strategies;

import java.awt.Point;
import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;

/**
 * \brief
 * Decides a computer move by selecting a valid move at random
 * @author Rodney Shaghoulian
 */
public class RandomStrategy extends Strategy {

	/**
	 * Constructor - Calls subclasses constructor
	 * @param controleur	The Othello simulation that we should run Minimax on.
	 */
	public RandomStrategy(Controleur controleur){
		super(controleur);
	}
	
	/**
	 * Does a "move" on a Board, which is decided randomly.
	 * @param plateau		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Plateau move(Plateau plateau){
		ArrayList<Point> validMoves;
		if (plateau.playerTurn == Couleur.BLACK)
			validMoves = plateau.blackJoueur.validMoves;
		else
			validMoves = plateau.whiteJoueur.validMoves;
		if (validMoves.isEmpty())
			return null;
		Point move = chooseRandomMove(validMoves);
		Commande commande = new Commande(plateau, controleur.plateau.playerTurn, move);
		manageur.executeCommand(commande);
		vue.updateView();
		return plateau;
	}
	
	/**
	 * Returns one of the validMoves at random. A "move" corresponds to a "Point".
	 * @param validMoves	The list of valid moves
	 * @return				One of the moves, chosen randomly.
	 */
	public Point chooseRandomMove(ArrayList<Point> validMoves){
		int numMoves = validMoves.size();
		int num = (int) (Math.random() * numMoves);
		return validMoves.get(num);
	}
}
