package strategies;

import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;

/**
 * La classe Minimax étend la classe Strategie.
 * Elle implémente l'algorithme Minimax pour le jeu.
 */
public class Minimax extends Strategie {

 // La profondeur de recherche de l'algorithme
 public int profondeur = 5;

 /**
  * Constructeur de la classe Minimax.
  * Initialise la stratégie avec le contrôleur donné.
  *
  * @param controleur le contrôleur du jeu
  */
 public Minimax(Controleur controleur){
  super(controleur);
 }

 /**
  * Effectue un mouvement sur le plateau en utilisant l'algorithme Minimax.
  *
  * @param plateau le plateau de jeu
  * @return le plateau après le mouvement
  */
 public Plateau mouvement(Plateau plateau){
  long tempsDebut = System.currentTimeMillis();
  Plateau plateauSuccesseur = minimax(plateau, 0, profondeur);
  long tempsFin = System.currentTimeMillis();

  double duree = (tempsFin - tempsDebut) / 1000.0;
  System.out.println("Tour " + plateau.tour + "\n------\nMinimax Temps mouvement = " + duree + " secondes");

  Commande commande = getCommande(plateau, plateauSuccesseur);
  manageur.executionCommande(commande);

  vue.miseAjourVue();

  Utilite utilite = new Utilite(plateauSuccesseur);
  utilite.utiliteCoins();
  System.out.println("Complexité utilité = " + utilite.value + "\n");

  return plateau;
 }

 /**
  * Implémente l'algorithme Minimax.
  *
  * @param plateau le plateau de jeu
  * @param niveauActuel le niveau actuel de l'arbre de recherche
  * @param profondeurMax la profondeur maximale de l'arbre de recherche
  * @return le meilleur plateau après la recherche
  */
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
