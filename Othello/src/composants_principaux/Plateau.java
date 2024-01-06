
package composants_principaux;
import java.awt.Point;
import java.util.ArrayList;

/**
 * La classe Plateau représente le plateau de jeu.
 * Elle contient un tableau de disques, les joueurs, le tour du joueur, le gagnant et le nombre de tours.
 */
public class Plateau {

	public final int lignes;
	public final int colonnes;
	public Disque[][] othellier;
	

	public Joueur joueurNoir;
	public Joueur joueurBlanc;
	

	public Couleur tourJoueur;
	public boolean jeuTermine;
	public Couleur gagnant;
	public int tour;


	/**
	 * Constructeur de la classe Plateau.
	 * Initialise les lignes, les colonnes, le tableau de disques, les joueurs, le tour du joueur, le jeu terminé, le gagnant et le tour.
	 *
	 * @param lignes le nombre de lignes du plateau
	 * @param colonnes le nombre de colonnes du plateau
	 */
	public Plateau(int lignes, int colonnes){

		this.lignes = lignes;
		this.colonnes = colonnes;
		othellier = new Disque[lignes][colonnes];
		
		initialisationPlateau();
		

		joueurNoir = new Joueur(this, Couleur.NOIR, false);
		joueurBlanc = new Joueur(this, Couleur.BLANC, false);
		

		tourJoueur = Couleur.NOIR;
		jeuTermine = false;
		gagnant = null;
		tour = 1;
	}


	/**
	 * Constructeur de copie de la classe Plateau.
	 * Crée un nouveau plateau avec les mêmes attributs que le plateau donné.
	 *
	 * @param autrePlateau le plateau à copier
	 */
	public Plateau(Plateau autrePlateau){

		lignes = autrePlateau.lignes;
		colonnes = autrePlateau.colonnes;
		othellier = new Disque[lignes][colonnes];
		for (int lig = 0; lig < lignes; lig++){
			for (int col = 0; col < colonnes; col++){
				othellier[lig][col] = new Disque(autrePlateau.othellier[lig][col]);
			}
		}

		joueurNoir = new Joueur(autrePlateau.joueurNoir);
		joueurBlanc = new Joueur(autrePlateau.joueurBlanc);
		

		tourJoueur = autrePlateau.tourJoueur;
		jeuTermine = autrePlateau.jeuTermine;
		gagnant = autrePlateau.gagnant;
		tour = autrePlateau.tour;
	}


	/**
	 * Initialise le plateau de jeu.
	 */
	public void initialisationPlateau(){
		for (int lig = 0; lig < lignes; lig++){
			for (int col = 0; col < colonnes; col++){
				othellier[lig][col] = new Disque(Couleur.VIDE);
			}
		}
		
		int centreRondBas = lignes / 2 - 1;
		int centreRondHaut = lignes / 2;

		othellier[centreRondBas][centreRondBas].changerCouleur(Couleur.NOIR);
		othellier[centreRondHaut][centreRondHaut].changerCouleur(Couleur.NOIR);
		

		othellier[centreRondBas][centreRondHaut].changerCouleur(Couleur.BLANC);
		othellier[centreRondHaut][centreRondBas].changerCouleur(Couleur.BLANC);
	}


	/**
	 * Vérifie si la position est valide sur le plateau.
	 *
	 * @param pos la position à vérifier
	 * @return vrai si la position est valide, faux sinon
	 */
	public boolean positionValide(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < colonnes && pos.y < lignes);
	}


	/**
	 * Retourne la couleur du disque à la position donnée.
	 *
	 * @param pos la position du disque
	 * @return la couleur du disque à la position donnée
	 */
	public Couleur couleurDisque(Point pos){
		return othellier[pos.y][pos.x].couleur;
	}


	/**
	 * Retourne la couleur de l'adversaire de la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return la couleur de l'adversaire
	 */
	public Couleur getCouleurAdverse(Couleur couleur){
		if (couleur == Couleur.NOIR)
			return Couleur.BLANC;
		else if (couleur == Couleur.BLANC)
			return Couleur.NOIR;
		return null;
	}


	/**
	 * Met à jour le tour du joueur.
	 */
	public void MiseAJourTour(){
		if (tourJoueur == Couleur.BLANC && !joueurNoir.mouvementsValides.isEmpty())
			tourJoueur = Couleur.NOIR;
		else if (tourJoueur == Couleur.NOIR && !joueurBlanc.mouvementsValides.isEmpty())
			tourJoueur = Couleur.BLANC;
		tour = joueurNoir.score + joueurBlanc.score - 3;
	}


	/**
	 * Met à jour le statut du jeu.
	 */
	public void miseAJourStatutJeu(){
		if (joueurNoir.mouvementsValides.isEmpty() && joueurBlanc.mouvementsValides.isEmpty()){
			jeuTermine = true;
			if (joueurNoir.score > joueurBlanc.score)
				gagnant = Couleur.NOIR;
			else if (joueurBlanc.score > joueurNoir.score)
				gagnant = Couleur.BLANC;
			else
				gagnant = Couleur.VIDE;
		}
	}


	/**
	 * Met à jour le tableau de jeu.
	 */
	public void miseAJourTableau(){
		joueurNoir.miseAJourMouvements(this);
		joueurBlanc.miseAJourMouvements(this);
		MiseAJourTour();
		miseAJourStatutJeu();
	}


	/**
	 * Vérifie si le mouvement est valide pour la couleur donnée à la position donnée.
	 *
	 * @param pos la position du mouvement
	 * @param couleur la couleur du joueur
	 * @return vrai si le mouvement est valide, faux sinon
	 */
	public boolean mouvementValide(Point pos, Couleur couleur){
		if (pos == null || couleur == null || !positionValide(pos) || couleur == Couleur.VIDE || othellier[pos.y][pos.x].couleur != Couleur.VIDE)
			return false;
		
		if (valideDansToutesLesDirections(pos, couleur, 1, 0))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, -1, 0))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, 0, 1))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, 0, -1))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, 1, 1))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, 1, -1))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, -1, 1))
			return true;
		else if (valideDansToutesLesDirections(pos, couleur, -1, -1))
			return true;
		return false;
	}


	/**
	 * Vérifie si le mouvement est valide dans toutes les directions pour la couleur donnée à la position donnée.
	 *
	 * @param position la position du mouvement
	 * @param couleur la couleur du joueur
	 * @param xDirection la direction x
	 * @param yDirection la direction y
	 * @return vrai si le mouvement est valide dans toutes les directions, faux sinon
	 */
	public boolean valideDansToutesLesDirections(Point position, Couleur couleur, int xDirection, int yDirection){
		Point pos = new Point(position);
		Couleur opponentCouleur = getCouleurAdverse(couleur);
		

		pos.x += xDirection;
		pos.y += yDirection;
		if (!positionValide(pos) || couleurDisque(pos) != opponentCouleur)
			return false;
		

		pos.x += xDirection;
		pos.y += yDirection;
		while(positionValide(pos)){
			if (couleurDisque(pos) == couleur)
				return true;
			else if (couleurDisque(pos) == Couleur.VIDE)
				return false;
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return false;
	}


	/**
	 * Retire le disque à la position donnée.
	 *
	 * @param pos la position du disque à retirer
	 */
	public void retirerDisque(Point pos){
		Couleur oldCouleur = othellier[pos.y][pos.x].couleur;
		othellier[pos.y][pos.x].changerCouleur(Couleur.VIDE);
		
		/* Subtract from scores */
		if (oldCouleur == Couleur.NOIR)
			joueurNoir.score--;
		else if (oldCouleur == Couleur.BLANC)
			joueurBlanc.score--;
	}


	/**
	 * Retourne le disque à la position donnée avec la nouvelle couleur.
	 *
	 * @param pos la position du disque à retourner
	 * @param nouvelleCouleur la nouvelle couleur du disque
	 */
	public void retournerDisque(Point pos, Couleur nouvelleCouleur){
		Couleur ancienneCouleur = othellier[pos.y][pos.x].couleur;
		othellier[pos.y][pos.x].changerCouleur(nouvelleCouleur);
		

		if (ancienneCouleur == Couleur.NOIR)
			joueurNoir.score--;
		else if (ancienneCouleur == Couleur.BLANC)
			joueurBlanc.score--;
		

		if (nouvelleCouleur == Couleur.NOIR)
			joueurNoir.score++;
		else if (nouvelleCouleur == Couleur.BLANC)
			joueurBlanc.score++;
	}


	/**
	 * Retourne les disques capturés à la position donnée pour la couleur donnée.
	 *
	 * @param pos la position du disque
	 * @param couleur la couleur du joueur
	 * @return la liste des points des disques capturés
	 */
	public ArrayList<Point> retournerCaptures(Point pos, Couleur couleur){
		ArrayList<Point> disquesRetournes = new ArrayList<Point>();
		ArrayList<Point> disquesRetournesDansDirection = new ArrayList<Point>();
		
		if (!positionValide(pos))
			return disquesRetournes;
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur,  1,  0);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur, -1,  0);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur,  0,  1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur,  0, -1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur,  1,  1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur,  1, -1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur, -1,  1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		disquesRetournesDansDirection = retourneDansDirection(pos, couleur, -1, -1);
		disquesRetournes.addAll(disquesRetournesDansDirection);
		return disquesRetournes;
	}


	/**
	 * Retourne les disques capturés à la position donnée pour la couleur donnée.
	 *
	 * @param position la position du disque
	 * @param couleur la couleur du joueur
	 * @return la liste des points des disques capturés
	 */
	public ArrayList<Point> retourneDansDirection(Point position, Couleur couleur, int xDirection, int yDirection){
		ArrayList<Point> disquesRetournes = new ArrayList<Point>();
		Point pos = new Point(position);
		if (!valideDansToutesLesDirections(pos, couleur, xDirection, yDirection))
			return disquesRetournes;
		Couleur couleurAdverse = getCouleurAdverse(couleur);
		pos.x += xDirection;
		pos.y += yDirection;
		while (positionValide(pos) && couleurDisque(pos) == couleurAdverse){
			retournerDisque(pos, couleur);
			disquesRetournes.add(new Point(pos));
			pos.x += xDirection;
			pos.y += yDirection;
		}
		return disquesRetournes;
	}



	/**
	 * Place un disque à la position donnée pour la couleur donnée.
	 *
	 * @param pos la position du disque à placer
	 * @param couleur la couleur du disque à placer
	 */
	public void placerDisque(Point pos, Couleur couleur){
		retournerDisque(pos, couleur);
		retournerCaptures(pos, couleur);
		miseAJourTableau();
	}


	/**
	 * Retourne le joueur actuel.
	 *
	 * @return le joueur actuel
	 */
	public Joueur getJoueurActuel(){
		if (tourJoueur == Couleur.NOIR)
			return joueurNoir;
		else
			return joueurBlanc;
	}


	/**
	 * Compte le nombre de cases X occupées par la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return le nombre de cases X occupées
	 */
	public int xCasesOccupees(Couleur couleur){
		int mauvaisesCases = 0;
		if (couleurDisque(new Point(1, 1)) == couleur)
			mauvaisesCases++;
		if (couleurDisque(new Point(1, lignes - 2)) == couleur)
			mauvaisesCases++;
		if (couleurDisque(new Point(colonnes - 2, 1)) == couleur)
			mauvaisesCases++;
		if (couleurDisque(new Point(colonnes - 2, lignes - 2)) == couleur)
			mauvaisesCases++;
		return mauvaisesCases;
	}


	/**
	 * Compte le nombre de mauvaises cases X occupées par la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return le nombre de mauvaises cases X occupées
	 */
	public int mauvaisesXCasesOccupees(Couleur couleur){
		int mauvaisesCases = 0;
		if ((couleurDisque(new Point(1, 1)) == couleur) && (couleurDisque(new Point(0, 0)) == Couleur.VIDE))
			mauvaisesCases++;
		if ((couleurDisque(new Point(1, lignes - 2)) == couleur) && (couleurDisque(new Point(0, lignes - 1)) == Couleur.VIDE))
			mauvaisesCases++;
		if ((couleurDisque(new Point(colonnes - 2, 1)) == couleur) && (couleurDisque(new Point(colonnes - 1, 0)) == Couleur.VIDE))
			mauvaisesCases++;
		if ((couleurDisque(new Point(colonnes - 2, lignes - 2)) == couleur) && (couleurDisque(new Point(colonnes - 1, lignes - 1)) == Couleur.VIDE))
			mauvaisesCases++;
		return mauvaisesCases;
	}


	/**
	 * Compte le nombre de cases C occupées par la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return le nombre de cases C occupées
	 */
	public int cCasesOccupees(Couleur couleur){
		int cCases = 0;
		
		if (couleurDisque(new Point(0, 1)) == couleur)
			cCases++;
		if (couleurDisque(new Point(1, 0)) == couleur)
			cCases++;
		
		if (couleurDisque(new Point(0, lignes - 2)) == couleur)
			cCases++;
		if (couleurDisque(new Point(1, lignes - 1)) == couleur)
			cCases++;
		
		if (couleurDisque(new Point(colonnes - 2, 0)) == couleur)
			cCases++;
		if (couleurDisque(new Point(colonnes - 1, 1)) == couleur)
			cCases++;
		
		if (couleurDisque(new Point(colonnes - 1, lignes - 2)) == couleur)
			cCases++;
		if (couleurDisque(new Point(colonnes - 2, lignes - 1)) == couleur)
			cCases++;
		
		return cCases;
	}


	/**
	 * Compte le nombre de mauvaises cases C occupées par la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return le nombre de mauvaises cases C occupées
	 */
	public int mauvaisesCCasesOccupees(Couleur couleur){
		int cCases = 0;
		
		if ((couleurDisque(new Point(0, 1)) == couleur) && (couleurDisque(new Point(0, 0)) == Couleur.VIDE))
			cCases++;
		if ((couleurDisque(new Point(1, 0)) == couleur) && (couleurDisque(new Point(0, 0)) == Couleur.VIDE))
			cCases++;
		
		if ((couleurDisque(new Point(0, lignes - 2)) == couleur) && (couleurDisque(new Point(0, lignes - 1)) == Couleur.VIDE))
			cCases++;
		if ((couleurDisque(new Point(1, lignes - 1)) == couleur) && (couleurDisque(new Point(0, lignes - 1)) == Couleur.VIDE))
			cCases++;
		
		if ((couleurDisque(new Point(colonnes - 2, 0)) == couleur) && (couleurDisque(new Point(colonnes - 1, 0)) == Couleur.VIDE))
			cCases++;
		if ((couleurDisque(new Point(colonnes - 1, 1)) == couleur) && (couleurDisque(new Point(colonnes - 1, 0)) == Couleur.VIDE))
			cCases++;
		
		if ((couleurDisque(new Point(colonnes - 1, lignes - 2)) == couleur) && (couleurDisque(new Point(colonnes - 1, lignes - 1)) == Couleur.VIDE))
			cCases++;
		if ((couleurDisque(new Point(colonnes - 2, lignes - 1)) == couleur) && (couleurDisque(new Point(colonnes - 1, lignes - 1)) == Couleur.VIDE))
			cCases++;
		
		return cCases;
	}


	/**
	 * Compte le nombre de coins occupés par la couleur donnée.
	 *
	 * @param couleur la couleur du joueur
	 * @return le nombre de coins occupés
	 */
	public int coinsOccupes(Couleur couleur){
		int coins = 0;
		if (couleurDisque(new Point(0, 0)) == couleur)
			coins++;
		if (couleurDisque(new Point(0, lignes - 1)) == couleur)
			coins++;
		if (couleurDisque(new Point(colonnes - 1, 0)) == couleur)
			coins++;
		if (couleurDisque(new Point(colonnes - 1, lignes - 1)) == couleur)
			coins++;
		return coins;
	}


	/**
	 * Retourne une représentation en chaîne de caractères du plateau.
	 *
	 * @return une représentation en chaîne de caractères du plateau
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		for (int lig = lignes - 1; lig >= 0; lig--){
			for (int col = 0; col < colonnes; col++){
				if (othellier[lig][col].couleur == Couleur.NOIR)
					sb.append("\\u25CF");
				else if (othellier[lig][col].couleur == Couleur.BLANC)
					sb.append("\\u25CB");
				else
					sb.append("-");
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
