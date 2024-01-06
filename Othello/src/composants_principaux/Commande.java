package composants_principaux;

import java.awt.Point;
import java.util.ArrayList;

/**
 * La classe Commande représente une commande dans le jeu.
 * Elle contient un plateau, une couleur, une destination et une liste de points capturés.
 */
public class Commande {
 // Le plateau de jeu
 public Plateau plateau;
 // La couleur du joueur
 public Couleur couleur;
 // La destination de la commande
 public Point destination;
 // La liste des points capturés
 public ArrayList<Point> pointsCaptures;

 /**
  * Constructeur de la classe Commande.
  * Initialise le plateau, la couleur, la destination et la liste des points capturés.
  *
  * @param plateau le plateau de jeu
  * @param couleur la couleur du joueur
  * @param position la position de la commande
  */
 public Commande(Plateau plateau, Couleur couleur, Point position){
  this.plateau = plateau;
  this.couleur = couleur;
  this.destination = new Point(position);

  pointsCaptures = new ArrayList<Point>();
 }

 /**
  * Exécute la commande.
  * Met à jour le modèle en retournant le disque à la destination et en capturant les points.
  */
 public void execution() {

  plateau.retournerDisque(destination, couleur);
  pointsCaptures = plateau.retournerCaptures(destination, couleur);
  plateau.miseAJourTableau();
 }

 /**
  * Annule la commande.
  * Retire le disque de la destination et retourne les disques capturés.
  */
 public void annuler(){

  Couleur couleurAdverse = plateau.getCouleurAdverse(couleur);
  plateau.retirerDisque(destination);
  for (Point point : pointsCaptures){
   plateau.retournerDisque(point, couleurAdverse);
  }
  plateau.miseAJourTableau();
 }
}