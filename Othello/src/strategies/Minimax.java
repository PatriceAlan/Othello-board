package strategies;

import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;


public class Minimax extends Strategie {

	public int depth = 5;
	

	public Minimax(Controleur controleur){
		super(controleur);
	}


	public Plateau mouvement(Plateau plateau){
		Plateau plateauSuccesseur = minimax(plateau, 0, depth);
		

		Commande commande = getCommande(plateau, plateauSuccesseur);
		manageur.executionCommande(commande);
		
		vue.miseAjourVue();
		return plateau;
	}
	

	public Plateau minimax(Plateau plateau, int niveauActuel, int profondeurMax){
		if (plateau.jeuTermine || (niveauActuel == profondeurMax))
			return plateau;
		Utilite utilite;
		
		ArrayList<Plateau> plateauxSuccesseurs = getPlateauxAdjacents(plateau);
		Plateau bestPlateau = null;
		
		if (plateau.tourJoueur == Couleur.NOIR){
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < plateauxSuccesseurs.size(); i++){
				Plateau plateauSuccesseur = plateauxSuccesseurs.get(i);
				Plateau plateauSuccesseur2 = minimax(plateauSuccesseur, niveauActuel + 1, profondeurMax);
				utilite = new Utilite(plateauSuccesseur2);
				utilite.utiliteCoins();
				if (utilite.value > max){
					max = utilite.value;
					bestPlateau = plateauSuccesseur;
				}
			}
		}
		else{
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < plateauxSuccesseurs.size(); i++){
				Plateau plateauSuccesseur = plateauxSuccesseurs.get(i);
				Plateau plateauSuccesseur2 = minimax(plateauSuccesseur, niveauActuel + 1, profondeurMax);
				utilite = new Utilite(plateauSuccesseur2);
				utilite.utiliteCoins();
				if (utilite.value < min){
					min = utilite.value;
					bestPlateau = plateauSuccesseur;
				}
			}
		}
		return bestPlateau;
	}
}