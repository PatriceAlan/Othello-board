package strategies;

import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;

/**
 * \brief
 * Decides a computer move based on "Minimax" strategy.
 * @author Rodney Shaghoulian
 */
public class MinimaxStrategy extends Strategy{

	public int depth = 5;			///< The depth to search the game tree
	
	/**
	 * Constructor - Calls subclasses constructor
	 * @param controleur	The Othello simulation that we should run Minimax on.
	 */
	public MinimaxStrategy(Controleur controleur){
		super(controleur);
	}

	/**
	 * Does a "move" on a Board. Which "move" is done depends on Minimax strategy
	 * @param plateau		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Plateau move(Plateau plateau){
		Plateau successorPlateau = minimax(plateau, 0, depth);
		
		/* Execute the command */
		Commande commande = getCommand(plateau, successorPlateau);
		manageur.executeCommand(commande);
		
		vue.updateView();
		return plateau;
	}
	
	/**
	 * Recursive function. Uses Minimax strategy to determine the next move
	 * @param plateau			The Board to do a "move" on.
	 * @param currLevel		The current depth we've searched to in game tree (where 0 corresponds to current state of Board)
	 * @param maxDepth		The number of levels deep we should search the game tree
	 * @return				The Board after the "move" is performed.
	 */
	public Plateau minimax(Plateau plateau, int currLevel, int maxDepth){
		if (plateau.gameEnded || (currLevel == maxDepth))
			return plateau;
		Utility utility;
		
		ArrayList<Plateau> successorPlateaus = getAdjacentBoards(plateau);
		Plateau bestPlateau = null;
		
		if (plateau.playerTurn == Couleur.BLACK){
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < successorPlateaus.size(); i++){
				Plateau successor = successorPlateaus.get(i);
				Plateau lookaheadPlateau = minimax(successor, currLevel + 1, maxDepth);
				utility = new Utility(lookaheadPlateau);
				utility.utilityCorners();
				if (utility.value > max){
					max = utility.value;
					bestPlateau = successor;
				}
			}
		}
		else{
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < successorPlateaus.size(); i++){
				Plateau successor = successorPlateaus.get(i);
				Plateau lookaheadPlateau = minimax(successor, currLevel + 1, maxDepth);
				utility = new Utility(lookaheadPlateau);
				utility.utilityCorners();
				if (utility.value < min){
					min = utility.value;
					bestPlateau = successor;
				}
			}
		}
		return bestPlateau;
	}
}