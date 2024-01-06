package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;


public class Joueur {
	public Couleur couleur;
	public boolean estOrdi;
	public int score;
	public ArrayList<Point> mouvementsValides;
	

	public Joueur(Plateau plateau, Couleur couleur, boolean ordi){
		this.couleur = couleur;
		this.estOrdi = ordi;
		score = 2;
		mouvementsValides = new ArrayList<Point>();
		miseAJourMouvements(plateau);
	}
	

	public Joueur(Joueur autreJoueur){
		couleur = autreJoueur.couleur;
		estOrdi = autreJoueur.estOrdi;
		score = autreJoueur.score;
		mouvementsValides = new ArrayList<Point>(autreJoueur.mouvementsValides);
	}
	

	public void miseAJourMouvements(Plateau plateau){
		mouvementsValides.clear();
		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				Point mouvementPoint = new Point(col, lig);
				if (plateau.mouvementValide(mouvementPoint, couleur))
					mouvementsValides.add(mouvementPoint);
			}
		}
	}
}