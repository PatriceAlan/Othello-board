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
 * La classe abstraite Strategie représente une stratégie de jeu pour notre programme Othello.
 * Elle contient des méthodes pour effectuer un mouvement, obtenir les plateaux adjacents et obtenir une commande.
 */
public abstract class Strategie {
	public Controleur controleur;
	public Vue vue;
	public Manageur manageur;


	/**
	 * Constructeur de la classe Strategie.
	 * Initialise la stratégie avec le contrôleur donné.
	 *
	 * @param controleur le contrôleur du jeu
	 */
	public Strategie(Controleur controleur){
		this.controleur = controleur;
		this.vue = controleur.vue;
		this.manageur = controleur.manageur;
	}


	/**
	 * Effectue un mouvement sur le plateau.
	 * Cette méthode est abstraite et doit être implémentée par les sous-classes.
	 *
	 * @param plateau le plateau de jeu
	 * @return le plateau après le mouvement
	 */
	public abstract Plateau mouvement(Plateau plateau);


	/**
	 * Obtient les plateaux adjacents à partir du plateau donné.
	 *
	 * @param plateau le plateau de jeu
	 * @return une liste des plateaux adjacents
	 */
	public ArrayList<Plateau> getPlateauxAdjacents(Plateau plateau){
		ArrayList<Plateau> plateaux = new ArrayList<>();
		ArrayList<Point> mouvementsValides;
		if (plateau.tourJoueur == Couleur.NOIR)
			mouvementsValides = plateau.joueurNoir.mouvementsValides;
		else
			mouvementsValides = plateau.joueurBlanc.mouvementsValides;
		for (Point mouvement : mouvementsValides){
			Plateau plateauResultant = new Plateau(plateau);
			Commande commande = new Commande(plateauResultant, plateauResultant.tourJoueur, mouvement);
			commande.execution();
			plateaux.add(plateauResultant);
		}
		return plateaux;
	}

	/**
	 * Obtient une commande à partir des plateaux donnés.
	 *
	 * @param plateau le plateau de jeu actuel
	 * @param plateauSuccesseur le plateau après le mouvement
	 * @return une commande représentant le mouvement entre les deux plateaux
	 */
	public Commande getCommande(Plateau plateau, Plateau plateauSuccesseur){
		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				if (plateau.othellier[lig][col].couleur == Couleur.VIDE && plateauSuccesseur.othellier[lig][col].couleur != Couleur.VIDE)
					return new Commande(plateau, plateauSuccesseur.othellier[lig][col].couleur, new Point(col, lig));
			}
		}
		return null;
	}
}
