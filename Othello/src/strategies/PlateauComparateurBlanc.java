package strategies;

import java.util.Comparator;
import composants_principaux.Plateau;

/**
 * La classe PlateauComparateurBlanc implémente l'interface Comparator pour comparer deux plateaux de jeu en fonction de l'utilité finale pour le joueur blanc.
 */
public class PlateauComparateurBlanc implements Comparator<Plateau>{

 /**
  * Compare deux plateaux de jeu en fonction de l'utilité finale pour le joueur blanc.
  *
  * @param plateau1 le premier plateau à comparer
  * @param plateau2 le deuxième plateau à comparer
  * @return un entier négatif, zéro ou un entier positif si l'utilité finale pour le joueur blanc sur le premier plateau est respectivement inférieure, égale à ou supérieure à l'utilité finale pour le joueur blanc sur le deuxième plateau.
  */
 @Override
 public int compare(Plateau plateau1, Plateau plateau2){
  Utilite utilite1 = new Utilite(plateau1);
  utilite1.utiliteFinale();

  Utilite utilite2 = new Utilite(plateau2);
  utilite2.utiliteFinale();

  return utilite1.value - utilite2.value;
 }
}