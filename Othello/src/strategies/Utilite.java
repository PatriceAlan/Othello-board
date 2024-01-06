package strategies;

import composants_principaux.Plateau;
import composants_principaux.Couleur;

/**
 * La classe Utilite calcule l'utilité d'un plateau de jeu pour l'algorithme Minimax.
 * Elle contient des méthodes pour calculer l'utilité en fonction de différents critères.
 */
public class Utilite {

	// Le plateau de jeu
	public Plateau plateau;
	// La valeur d'utilité du plateau
	public int value;

	/**
	 * Constructeur de la classe Utilite.
	 * Initialise l'utilité avec le plateau donné.
	 *
	 * @param plateau le plateau de jeu
	 */
	public Utilite(Plateau plateau){
		this.plateau = plateau;
		this.value = 0;
	}

	/**
	 * Calcule l'utilité en fonction de la différence de score entre les joueurs.
	 */
	public void utiliteDiffScore(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.joueurNoir.score - plateau.joueurBlanc.score;
	}

	/**
	 * Calcule l'utilité en fonction du nombre de coins occupés par chaque joueur.
	 */
	public void utiliteCoins(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.coinsOccupes(Couleur.NOIR) - plateau.coinsOccupes(Couleur.BLANC);
	}

	/**
	 * Calcule l'utilité en fonction du nombre de mouvements valides pour chaque joueur.
	 */
	public void utiliteMouvementsValides(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.joueurNoir.mouvementsValides.size() - plateau.joueurBlanc.mouvementsValides.size();
	}

	/**
	 * Calcule l'utilité finale en fonction de plusieurs critères, y compris le nombre de mouvements valides, le nombre de mauvaises cases occupées et le nombre de coins occupés.
	 */
	public void utiliteFinale(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else{
			int utiliteNoir = plateau.joueurNoir.mouvementsValides.size()
					           + (-63 + plateau.tour) * plateau.mauvaisesCCasesOccupees(Couleur.NOIR)
					           + (-60 + plateau.tour) * plateau.mauvaisesXCasesOccupees(Couleur.NOIR)
				               + ( 66 - plateau.tour) * plateau.coinsOccupes(Couleur.NOIR);
			int utiliteBlanc = plateau.joueurBlanc.mouvementsValides.size()
			           			+ (-63 + plateau.tour) * plateau.mauvaisesCCasesOccupees(Couleur.BLANC)
			           			+ (-60 + plateau.tour) * plateau.mauvaisesXCasesOccupees(Couleur.BLANC)
			           			+ ( 66 - plateau.tour) * plateau.coinsOccupes(Couleur.BLANC);
			if (plateau.tour >= 44){
				utiliteNoir += plateau.joueurNoir.score;
				utiliteBlanc += plateau.joueurBlanc.score;
			}
			value = utiliteNoir - utiliteBlanc;
		}
	}


	/**
	 * Calcule l'utilité si le jeu est terminé.
	 * Si le joueur noir a gagné, l'utilité est de 1000 (qui ici est un nombre choisi arbitrairement).
	 * Si le joueur blanc a gagné, l'utilité est de -1000 (qui ici est un nombre choisi arbitrairement).
	 * Si le jeu est un match nul, l'utilité est de 0.
	 */
	public void utiliteJeuTermine(){
		if (plateau.jeuTermine){
			if (plateau.gagnant == Couleur.NOIR)
				value = 1000;
			else if (plateau.gagnant == Couleur.BLANC)
				value = -1000;
			else
				value = 0;
		}
	}
}
