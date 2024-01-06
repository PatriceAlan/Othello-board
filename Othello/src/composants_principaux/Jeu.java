package composants_principaux;

import strategies.Difficulte;

/**
 * La classe Jeu contient la méthode principale pour démarrer le jeu.
 */
public class Jeu {

 /**
  * La méthode principale pour démarrer le jeu.
  * Crée un nouveau contrôleur avec une grille de 8x8 et une difficulté DIFFICILE.
  *
  * @param args les arguments de la ligne de commande
  */
 public static void main(String[] args) {
  new Controleur(8, 8, Difficulte.DIFFICILE);
 }
}