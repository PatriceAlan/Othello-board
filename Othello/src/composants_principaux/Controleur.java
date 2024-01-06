package composants_principaux;

import gestionnaires.GestionnaireAbandon;
import gestionnaires.GestionnaireNouvellePartie;
import gestionnaires.GestionnaireDisque;
import gestionnaires.GestionnaireRefaire;
import gestionnaires.GestionnaireAnnuler;
import strategies.Minimax;
import strategies.AlphaBeta;
import strategies.Difficulte;
import strategies.StrategieAleatoire;


public class Controleur {
	public Plateau plateau;
	public Manageur manageur;

	public Vue vue;
	public int victoiresBlanc = 0;
	public int victoiresNoir = 0;
	public int lignes;
	public int colonnes;

	public Difficulte difficulte;
	

	public Controleur(int lignes, int colonnes, Difficulte difficulte) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.difficulte = difficulte;
		initialisationVariables();
		vue = new Vue(plateau, this);
		gestionnaireSouris();
	}
	
	public void reinitialiser(){
		initialisationVariables();
		vue.initialisation(plateau, this);
		gestionnaireSouris();
	}
	

	public void reinitialiser(Difficulte difficulte){
		this.difficulte = difficulte;
		initialisationVariables();
		vue.initialisation(plateau, this);
		gestionnaireSouris();
	}
	

	public void initialisationVariables(){
		plateau = new Plateau(lignes, colonnes);
		manageur = new Manageur();
	}
	

	public void gestionnaireSouris(){
		for (int i = 0; i < plateau.lignes; i++){
			for (int j = 0; j < plateau.colonnes; j++){
				vue.gestionnaireSouris(new GestionnaireDisque(this), vue.boutton[i][j]);
			}
		}
		vue.gestionnaireSouris(new GestionnaireAnnuler(this), vue.bouttonAnnuler);
		vue.gestionnaireSouris(new GestionnaireRefaire(this), vue.bouttonRefaire);
		vue.gestionnaireSouris(new GestionnaireAbandon(this), vue.abandonBlanc);
		vue.gestionnaireSouris(new GestionnaireAbandon(this), vue.abandonNoir);
		vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.FACILE),   vue.nouvellePartieFacile);
		vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.MOYEN), vue.nouvellePartieMoyenne);
		vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.DIFFICILE),   vue.nouvellePartieDifficile);

	}
	

	public void tourOrdi(){
		if (difficulte == Difficulte.FACILE)
			ordiFacile();
		else if (difficulte == Difficulte.MOYEN)
			ordiMoyen();
		else if (difficulte == Difficulte.DIFFICILE)
			ordiDifficile();
	}
	

	public void ordiFacile(){
		while (plateau.tourJoueur == Couleur.BLANC && !plateau.jeuTermine){
			StrategieAleatoire randomStrategy = new StrategieAleatoire(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Erreur");
			}
			plateau = randomStrategy.mouvement(plateau);
		}
	}


	public void ordiMoyen(){
		while (plateau.tourJoueur == Couleur.BLANC && !plateau.jeuTermine){
			Minimax minimax = new Minimax(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Erreur");
			}
			plateau = minimax.mouvement(plateau);
		}
	}
	

	public void ordiDifficile(){
		while (plateau.tourJoueur == Couleur.BLANC && !plateau.jeuTermine){
			AlphaBeta alphaBeta = new AlphaBeta(this);
			plateau = alphaBeta.mouvement(plateau);
		}
	}
}
