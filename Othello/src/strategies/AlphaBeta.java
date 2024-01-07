package strategies;

import java.util.ArrayList;
import java.util.Collections;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;



/**
 * La classe AlphaBeta implémente la stratégie de recherche alpha-bêta pour le jeu.
 * Elle étend la classe Strategie.
 */
public class AlphaBeta extends Strategie {

	// La profondeur de recherche de l'algorithme
	public int profondeur = 7;

	// Le nombre de nœuds explorés par l'algorithme
	public int noeudsExplores = 0;


	/**
	 * Constructeur de la classe AlphaBeta.
	 * Initialise la stratégie avec le contrôleur donné.
	 *
	 * @param controleur le contrôleur du jeu
	 */
	public AlphaBeta(Controleur controleur){
		super(controleur);
	}


	/**
	 * Effectue un mouvement sur le plateau en utilisant l'algorithme alpha-bêta.
	 *
	 * @param plateau le plateau de jeu
	 * @return le plateau après le mouvement
	 */
	public Plateau mouvement(Plateau plateau){
		if (plateau.tour >= 48)
			profondeur = 60;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		long tempsDebut = System.currentTimeMillis();
		Plateau plateauSuccesseur = alphaBeta(plateau, 0, profondeur, alpha, beta);
		long tempsFin = System.currentTimeMillis();
		
		double duree = (tempsFin - tempsDebut) / 1000.0;
		System.out.println("Tour " + plateau.tour + "\n------\nA.I. Temps mouvement = " + duree + " secondes");
		System.out.println("Plateaux étendus = " + noeudsExplores);
		

		Commande commande = getCommande(plateau, plateauSuccesseur);
		manageur.executionCommande(commande);
		
		vue.miseAjourVue();
		

		Utilite utilite = new Utilite(plateauSuccesseur);
		utilite.utiliteFinale();
		System.out.println("Complexité utilité = " + utilite.value + "\n");
		
		return plateau;
	}


	/**
	 * Implémente l'algorithme de recherche alpha-bêta.
	 *
	 * @param plateau le plateau de jeu
	 * @param niveauActuel le niveau actuel de l'arbre de recherche
	 * @param profondeurMax la profondeur maximale de l'arbre de recherche
	 * @param alpha la valeur alpha pour l'algorithme alpha-bêta
	 * @param beta la valeur bêta pour l'algorithme alpha-bêta
	 * @return le meilleur plateau après la recherche
	 */
	public Plateau alphaBeta(Plateau plateau, int niveauActuel, int profondeurMax, int alpha, int beta){
		if (plateau.jeuTermine || (niveauActuel == profondeurMax))
			return plateau;
		Utilite utilite;
		
		ArrayList<Plateau> plateauxSuccesseurs = getPlateauxAdjacents(plateau);
		Plateau meilleurPlateau = null;
		
		if (plateau.tourJoueur == Couleur.NOIR){

			Collections.sort(plateauxSuccesseurs, Collections.reverseOrder(new PlateauComparateurBlanc()));
			
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < plateauxSuccesseurs.size(); i++){
				noeudsExplores++;
				Plateau plateauSuccesseur = plateauxSuccesseurs.get(i);
				Plateau plateauSuccesseur2 = alphaBeta(plateauSuccesseur, niveauActuel + 1, profondeurMax, alpha, beta);
				utilite = new Utilite(plateauSuccesseur2);
				utilite.utiliteFinale();
				if (utilite.value > max){
					max = utilite.value;
					meilleurPlateau = plateauSuccesseur;
				}
				alpha = Math.max(alpha, utilite.value);
				if (utilite.value >= beta){
					return plateauSuccesseur2;
				}
			}
		}
		else{

			Collections.sort(plateauxSuccesseurs, new PlateauComparateurBlanc());
			
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < plateauxSuccesseurs.size(); i++){
				noeudsExplores++;
				Plateau plateauSuccesseur = plateauxSuccesseurs.get(i);
				Plateau plateauSuccesseur2 = alphaBeta(plateauSuccesseur, niveauActuel + 1, profondeurMax, alpha, beta);
				utilite = new Utilite(plateauSuccesseur2);
				utilite.utiliteFinale();
				if (utilite.value < min){
					min = utilite.value;
					meilleurPlateau = plateauSuccesseur;
				}
				beta = Math.min(beta, utilite.value);
				if (utilite.value <= alpha){
					return plateauSuccesseur2;
				}
			}
		}
		return meilleurPlateau;
	}
}

