package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;

/**
 * La classe Joueur représente un joueur dans le jeu.
 * Elle contient une couleur, un indicateur si le joueur est un ordinateur, un score et une liste de mouvements valides.
 */
public class Joueur {
 // La couleur du joueur
 public Couleur couleur;
 // Indicateur si le joueur est un ordinateur
 public boolean estOrdi;
 // Le score du joueur
 public int score;
 // La liste des mouvements valides du joueur
 public ArrayList<Point> mouvementsValides;

 /**
  * Constructeur de la classe Joueur.
  * Initialise la couleur, l'indicateur si le joueur est un ordinateur, le score et la liste des mouvements valides.
  *
  * @param plateau le plateau de jeu
  * @param couleur la couleur du joueur
  * @param ordi l'indicateur si le joueur est un ordinateur
  */
 public Joueur(Plateau plateau, Couleur couleur, boolean ordi){
  this.couleur = couleur;
  this.estOrdi = ordi;
  score = 2;
  mouvementsValides = new ArrayList<Point>();
  miseAJourMouvements(plateau);
 }

 /**
  * Constructeur de copie de la classe Joueur.
  * Crée un nouveau joueur avec les mêmes attributs que le joueur donné.
  *
  * @param autreJoueur le joueur à copier
  */
 public Joueur(Joueur autreJoueur){
  couleur = autreJoueur.couleur;
  estOrdi = autreJoueur.estOrdi;
  score = autreJoueur.score;
  mouvementsValides = new ArrayList<Point>(autreJoueur.mouvementsValides);
 }

 /**
  * Met à jour la liste des mouvements valides du joueur.
  *
  * @param plateau le plateau de jeu
  */
 public void miseAJourMouvements(Plateau plateau){
  mouvementsValides.clear();
  for (int lig = 0; lig < plateau.lignes; lig++){
   for (int col = 0; col < plateau.colonnes; col++){
    Point mouvementPoint = new Point(col, lig);
    if (plateau.mouvementValide(mouvementPoint, couleur))
     mouvementsValides.add(mouvementPoint);
   }
  }
 }
}