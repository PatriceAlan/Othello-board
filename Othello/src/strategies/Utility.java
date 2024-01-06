package strategies;

import composants_principaux.Plateau;
import composants_principaux.Couleur;

/**
 * \brief
 * Calculates the "utility" of a Board based on different "evaluation functions"
 * @author Rodney Shaghoulian
 */
public class Utility {
	
	public Plateau plateau;	///< The Board that we are calculating the "utility" for.
	public int value;	///< The utility value of the Board
	
	/**
	 * Constructor - Initializes variables
	 * @param plateau		The Board we are calculating the utility for.
	 */
	public Utility(Plateau plateau){
		this.plateau = plateau;
		this.value = 0;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Calculates utility based on the difference of player scores.
	 */
	public void utilityScoreDiff(){
		if (plateau.gameEnded)
			gameEndedUtility();
		else
			value = plateau.blackJoueur.score - plateau.whiteJoueur.score;
	}
	
	/**
	 * Calculates utility based on the number of corners owned by each Player.
	 */
	public void utilityCorners(){
		if (plateau.gameEnded)
			gameEndedUtility();
		else
			value = plateau.cornersOwned(Couleur.BLACK) - plateau.cornersOwned(Couleur.WHITE);
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility
	 */
	public void utilityValidMoves(){
		if (plateau.gameEnded)
			gameEndedUtility();
		else
			value = plateau.blackJoueur.validMoves.size() - plateau.whiteJoueur.validMoves.size();
	}
	
	/**
	 * Calculates utility based on valid moves remaining for each player. More moves equals greater utility. \n
	 * Also takes into account X squares, C squares, and corners.
	 */
	public void utilityFinal(){
		if (plateau.gameEnded)
			gameEndedUtility();
		else{
			int utilityBlack = plateau.blackJoueur.validMoves.size()
					           + (-63 + plateau.turn) * plateau.badCSquaresOwned(Couleur.BLACK)
					           + (-60 + plateau.turn) * plateau.badXSquaresOwned(Couleur.BLACK)
				               + ( 66 - plateau.turn) * plateau.cornersOwned(Couleur.BLACK);
			int utilityWhite = plateau.whiteJoueur.validMoves.size()
			           			+ (-63 + plateau.turn) * plateau.badCSquaresOwned(Couleur.WHITE)
			           			+ (-60 + plateau.turn) * plateau.badXSquaresOwned(Couleur.WHITE)
			           			+ ( 66 - plateau.turn) * plateau.cornersOwned(Couleur.WHITE);
			if (plateau.turn >= 44){
				utilityBlack += plateau.blackJoueur.score;
				utilityWhite += plateau.whiteJoueur.score;
			}
			value = utilityBlack - utilityWhite;
		}
	}
	
	/**
	 * Assigns a utility of 1000 for BLACK win and -1000 for WHITE win.
	 */
	public void gameEndedUtility(){
		if (plateau.gameEnded){
			if (plateau.winner == Couleur.BLACK)
				value = 10000; //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else if (plateau.winner == Couleur.WHITE)
				value = -10000;  //pretty arbitrary. Just has to be bigger than Absolute Value of inner nodes
			else
				value = 0;
		}
	}
}
