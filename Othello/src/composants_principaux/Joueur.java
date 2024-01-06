package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * A representation of a Player. May be Human or Computer.
 * @author Rodney Shaghoulian
 *
 */
public class Joueur {
	public Couleur couleur;					///< An enumerated type. "WHITE, BLACK, or NONE"
	public boolean isComputer;			///< false for Human Player. True for Computer Player
	public int score;					///< Equal to the number of Disks corresponding to color
	public ArrayList<Point> validMoves;	///< Potential Moves a Player can do
	
	/** 
	 * Constructor. Simply initializes variables.
	 * @param plateau		the Othello Board the Player is playing on.
	 * @param couleur		"WHITE" or "BLACK". "NONE" will not used.
	 * @param computer	boolean to determine if computer or human Player.
	 */
	public Joueur(Plateau plateau, Couleur couleur, boolean computer){
		this.couleur = couleur;
		this.isComputer = computer;
		score = 2; // since we start off with 2 Disks in center
		validMoves = new ArrayList<Point>();
		updateValidMoves(plateau);
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherJoueur	the other Player to create a deep copy of
	 */
	public Joueur(Joueur otherJoueur){
		couleur = otherJoueur.couleur;
		isComputer = otherJoueur.isComputer;
		score = otherJoueur.score;
		validMoves = new ArrayList<Point>(otherJoueur.validMoves);
	}
	
	/**
	 * Updates valid (potential) moves for a Player. Player could possibly have 0 valid moves.
	 * @param plateau	The Board that the Player is playing Othello on
	 */
	public void updateValidMoves(Plateau plateau){ //can possibly make this more efficient.
		validMoves.clear();
		for (int row = 0; row < plateau.rows; row++){
			for (int col = 0; col < plateau.columns; col++){
				Point movePoint = new Point(col, row);
				if (plateau.validMove(movePoint, couleur))
					validMoves.add(movePoint);
			}
		}
	}
}