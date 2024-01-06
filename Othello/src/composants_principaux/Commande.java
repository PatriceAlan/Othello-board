package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;


public class Commande {
	public Plateau plateau;
	public Couleur couleur;
	public Point destination;
	public ArrayList<Point> pointsCaptures;
	

	public Commande(Plateau plateau, Couleur couleur, Point position){
		this.plateau = plateau;
		this.couleur = couleur;
		this.destination = new Point(position);
		
		pointsCaptures = new ArrayList<Point>();
	}
	

	public void execution() {
		/* Update Model */
		plateau.retournerDisque(destination, couleur);
		pointsCaptures = plateau.retournerCaptures(destination, couleur);
		plateau.miseAJourTableau();
	}


	public void annuler(){

		Couleur couleurAdverse = plateau.getCouleurAdverse(couleur);
		plateau.retirerDisque(destination);
		for (Point point : pointsCaptures){
			plateau.retournerDisque(point, couleurAdverse);
		}
		plateau.miseAJourTableau();
	}
}
