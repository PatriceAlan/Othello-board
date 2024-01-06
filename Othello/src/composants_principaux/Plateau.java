
package composants_principaux;
import java.awt.Point;
import java.util.ArrayList;


public class Plateau {
	/* Essential Info */
	public final int lignes;
	public final int colonnes;
	public Disque[][] othellier;
	

	public Joueur joueurNoir;
	public Joueur joueurBlanc;
	

	public Couleur tourJoueur;
	public boolean jeuTermine;
	public Couleur gagnant;
	public int tour;
	

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
	

	public boolean positionValide(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < colonnes && pos.y < lignes);
	}
	

	public Couleur couleurDisque(Point pos){
		return othellier[pos.y][pos.x].couleur;
	}
	

	public Couleur getCouleurAdverse(Couleur couleur){
		if (couleur == Couleur.NOIR)
			return Couleur.BLANC;
		else if (couleur == Couleur.BLANC)
			return Couleur.NOIR;
		return null;
	}
	

	public void MiseAJourTour(){
		if (tourJoueur == Couleur.BLANC && !joueurNoir.mouvementsValides.isEmpty())
			tourJoueur = Couleur.NOIR;
		else if (tourJoueur == Couleur.NOIR && !joueurBlanc.mouvementsValides.isEmpty())
			tourJoueur = Couleur.BLANC;
		tour = joueurNoir.score + joueurBlanc.score - 3;
	}
	

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
	

	public void miseAJourTableau(){
		joueurNoir.miseAJourMouvements(this);
		joueurBlanc.miseAJourMouvements(this);
		MiseAJourTour();
		miseAJourStatutJeu();
	}
	

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
	

	public void retirerDisque(Point pos){
		Couleur oldCouleur = othellier[pos.y][pos.x].couleur;
		othellier[pos.y][pos.x].changerCouleur(Couleur.VIDE);
		
		/* Subtract from scores */
		if (oldCouleur == Couleur.NOIR)
			joueurNoir.score--;
		else if (oldCouleur == Couleur.BLANC)
			joueurBlanc.score--;
	}
	

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
	

	public void placerDisque(Point pos, Couleur couleur){
		retournerDisque(pos, couleur);
		retournerCaptures(pos, couleur);
		miseAJourTableau();
	}
	

	public Joueur getJoueurActuel(){
		if (tourJoueur == Couleur.NOIR)
			return joueurNoir;
		else
			return joueurBlanc;
	}
	

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
