package strategies;

import composants_principaux.Plateau;
import composants_principaux.Couleur;


public class Utilite {
	
	public Plateau plateau;
	public int value;
	

	public Utilite(Plateau plateau){
		this.plateau = plateau;
		this.value = 0;
	}
	
	

	public void utiliteDiffScore(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.joueurNoir.score - plateau.joueurBlanc.score;
	}

	public void utiliteCoins(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.coinsOccupes(Couleur.NOIR) - plateau.coinsOccupes(Couleur.BLANC);
	}
	

	public void utiliteMouvementsValides(){
		if (plateau.jeuTermine)
			utiliteJeuTermine();
		else
			value = plateau.joueurNoir.mouvementsValides.size() - plateau.joueurBlanc.mouvementsValides.size();
	}

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
	

	public void utiliteJeuTermine(){
		if (plateau.jeuTermine){
			if (plateau.gagnant == Couleur.NOIR)
				value = 10000;
			else if (plateau.gagnant == Couleur.BLANC)
				value = -10000;
			else
				value = 0;
		}
	}
}
