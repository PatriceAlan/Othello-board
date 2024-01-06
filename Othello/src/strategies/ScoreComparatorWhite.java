package strategies;

import java.util.Comparator;

import composants_principaux.Plateau;

/**
 * \brief
 * Used in sorting an array of boards so that the moves that immediately give WHITE the largest point gain come first
 * @author Rodney Shaghoulian
 */
public class ScoreComparatorWhite implements Comparator<Plateau>{
	/**
	 * Compares 2 Boards to determine which one is better for whitePlayer.
	 * @param plateau1	Board 1. Will compare to Board 2.
	 * @param plateau2	Board 2. Will compare to Board 1.
	 * @return		A negative integer if Board 1 is better than Board 2 for whitePlayer. \n
	 * 				Zero if Board 1 is equal to Board 2 for whitePlayer. \n
	 *              A positive integer if Board 1 is better than Board 2 for whitePlayer.
	 */
	@Override
	public int compare(Plateau plateau1, Plateau plateau2){
		Utility utility1 = new Utility(plateau1);	// corresponds to BLACK's utility
		utility1.utilityScoreDiff();
		
		Utility utility2 = new Utility(plateau2); // corresponds to BLACK's utility
		utility2.utilityScoreDiff();
		
		return utility1.value - utility2.value; // Example: If utility1 > utility2, we want board1 to come after board2
	}
}