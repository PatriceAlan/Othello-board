package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;

/**
 * \brief
 * A representation of a move on a Board
 * @author Rodney Shaghoulian
 */
public class Commande {
	public Plateau plateau;						///< the current Board the game is being played on
	public Couleur couleur;						///< the Color of the new Disk
	public Point destination;				///< the destination of the new Disk
	public ArrayList<Point> capturedPoints; ///< the Points that were captured. We save it in case a user "undoes" a move
	
	/**
	 * A representation of a "Move" command.
	 * @param plateau			The Board we are placing a Disk on.
	 * @param position		The position we are placing the Disk.
	 */
	public Commande(Plateau plateau, Couleur couleur, Point position){
		this.plateau = plateau;
		this.couleur = couleur;
		this.destination = new Point(position);
		
		capturedPoints = new ArrayList<Point>();
	}
	
	/**
	 * Execute the current Command on the given Board
	 */
	public void execute() {
		/* Update Model */
		plateau.flipDisk(destination, couleur);
		capturedPoints = plateau.flipCaptures(destination, couleur);
		plateau.updateBoard();
	}
	
	/**
	 * Undo the current Command
	 */
	public void undo(){
		/* Update Model */
		Couleur oppositeCouleur = plateau.getOppositeColor(couleur);
		plateau.removeDisk(destination);
		for (Point point : capturedPoints){
			plateau.flipDisk(point, oppositeCouleur);
		}
		plateau.updateBoard();
	}
}
