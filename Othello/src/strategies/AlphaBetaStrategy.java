package strategies;

import java.util.ArrayList;
import java.util.Collections;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;

/**
 * \brief
 * Decides a computer move based on "AlphaBeta" strategy.
 * @author Rodney Shaghoulian
 */
public class AlphaBetaStrategy extends Strategy{

	public int depth = 7;			///< The depth to search the game tree. Values 1 to 8 are practical.
	
	public int nodesExpanded = 0;	///< Number of nodes expanded during search.
	
	/**
	 * Constructor - Calls subclasses constructor
	 * @param controleur	The Othello simulation that we should run AlphaBeta on.
	 */
	public AlphaBetaStrategy(Controleur controleur){
		super(controleur);
	}

	/**
	 * Does a "move" on a Board. Which "move" is done depends on AlphaBeta strategy
	 * @param plateau		The Board that the computer A.I. will do a move on.
	 * @return			The updated Board after the "move" is performed.
	 */
	public Plateau move(Plateau plateau){
		if (plateau.turn >= 48)
			depth = 60;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		long startTime = System.currentTimeMillis();
		Plateau successorPlateau = alphaBeta(plateau, 0, depth, alpha, beta);
		long endTime = System.currentTimeMillis();
		
		double elapsedTime = (endTime - startTime) / 1000.0;
		System.out.println("Turn " + plateau.turn + "\n------\nA.I. Move Time = " + elapsedTime + " seconds");
		System.out.println("Game Boards expanded = " + nodesExpanded);
		
		/* Execute the command */
		Commande commande = getCommand(plateau, successorPlateau);
		manageur.executeCommand(commande);
		
		vue.updateView();
		
		/* Display utility to console */
		Utility utility = new Utility(successorPlateau);
		utility.utilityFinal();
		System.out.println("utility complex = " + utility.value + "\n");
		
		return plateau;
	}
	
	/**
	 * Recursive function. Uses AlphaBeta strategy to determine the next move
	 * @param plateau			The Board to do a "move" on.
	 * @param currLevel		The current depth we've searched to in game tree (where 0 corresponds to current state of Board)
	 * @param maxDepth		The number of levels deep we should search the game tree
	 * @param alpha			"value of the best choice of the max player" - CS 440 lecture slides
	 * @param beta			"lowest utility choice found so far for the min player" - CS 440 lecture slides
	 * @return				The Board after the "move" is performed.
	 */
	public Plateau alphaBeta(Plateau plateau, int currLevel, int maxDepth, int alpha, int beta){
		if (plateau.gameEnded || (currLevel == maxDepth))
			return plateau;
		Utility utility;
		
		ArrayList<Plateau> successorPlateaus = getAdjacentBoards(plateau);
		Plateau bestPlateau = null;
		
		if (plateau.playerTurn == Couleur.BLACK){
			/* Sort Boards corresponding to stronger moves first */
			Collections.sort(successorPlateaus, Collections.reverseOrder(new BoardComparatorWhite()));
			
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < successorPlateaus.size(); i++){
				nodesExpanded++;
				Plateau successor = successorPlateaus.get(i);
				Plateau lookaheadPlateau = alphaBeta(successor, currLevel + 1, maxDepth, alpha, beta);
				utility = new Utility(lookaheadPlateau);
				utility.utilityFinal();
				if (utility.value > max){
					max = utility.value;
					bestPlateau = successor;
				}
				alpha = Math.max(alpha, utility.value);
				if (utility.value >= beta){
					return lookaheadPlateau; // this is where we "prune", since MIN player will not allow this branch
				}
			}
		}
		else{
			/* Sort Boards corresponding to stronger moves first */
			Collections.sort(successorPlateaus, new BoardComparatorWhite());
			
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < successorPlateaus.size(); i++){
				nodesExpanded++;
				Plateau successor = successorPlateaus.get(i);
				Plateau lookaheadPlateau = alphaBeta(successor, currLevel + 1, maxDepth, alpha, beta);
				utility = new Utility(lookaheadPlateau);
				utility.utilityFinal();
				if (utility.value < min){
					min = utility.value;
					bestPlateau = successor;
				}
				beta = Math.min(beta, utility.value);
				if (utility.value <= alpha){
					return lookaheadPlateau; // this is where we "prune", since MAX player will not allow this branch
				}
			}
		}
		return bestPlateau;
	}
}

