package strategies;

import java.util.Comparator;
import composants_principaux.Plateau;

/**
 * La classe ComparateurScoreBlanc implémente l'interface Comparator pour comparer deux plateaux de jeu en fonction du score du joueur blanc.
 */
public class ComparateurScoreBlanc implements Comparator<Plateau>{

 /**
  * Compare deux plateaux de jeu en fonction du score du joueur blanc.
  *
  * @param plateau1 le premier plateau à comparer
  * @param plateau2 le deuxième plateau à comparer
  * @return un entier négatif, zéro ou un entier positif si le score du joueur blanc sur le premier plateau est respectivement inférieur, égal à ou supérieur au score du joueur blanc sur le deuxième plateau.
  */
 @Override
 public int compare(Plateau plateau1, Plateau plateau2){
  Utilite utilite1 = new Utilite(plateau1);
  utilite1.utiliteDiffScore();

  Utilite utilite2 = new Utilite(plateau2);
  utilite2.utiliteDiffScore();

  return utilite1.value - utilite2.value;
 }
}