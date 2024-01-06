package strategies;

import java.awt.Point;
import java.util.ArrayList;

import composants_principaux.Plateau;
import composants_principaux.Couleur;
import composants_principaux.Commande;
import composants_principaux.Controleur;

/**
 * La classe StrategieAleatoire étend la classe Strategie.
 * Elle implémente une stratégie de jeu où le mouvement est choisi aléatoirement parmi les mouvements valides.
 */
public class StrategieAleatoire extends Strategie {

 /**
  * Constructeur de la classe StrategieAleatoire.
  * Initialise la stratégie avec le contrôleur donné.
  *
  * @param controleur le contrôleur du jeu
  */
 public StrategieAleatoire(Controleur controleur){
  super(controleur);
 }

 /**
  * Effectue un mouvement sur le plateau.
  * Le mouvement est choisi aléatoirement parmi les mouvements valides.
  *
  * @param plateau le plateau de jeu
  * @return le plateau après le mouvement
  */
 public Plateau mouvement(Plateau plateau){
  ArrayList<Point> mouvementsValides;
  if (plateau.tourJoueur == Couleur.NOIR)
   mouvementsValides = plateau.joueurNoir.mouvementsValides;
  else
   mouvementsValides = plateau.joueurBlanc.mouvementsValides;
  if (mouvementsValides.isEmpty())
   return null;
  Point move = choisirMouvementAleatoire(mouvementsValides);
  Commande commande = new Commande(plateau, controleur.plateau.tourJoueur, move);
  manageur.executionCommande(commande);
  vue.miseAjourVue();
  return plateau;
 }

 /**
  * Choisit un mouvement aléatoire parmi les mouvements valides.
  *
  * @param mouvementsValides la liste des mouvements valides
  * @return un mouvement choisi aléatoirement
  */
 public Point choisirMouvementAleatoire(ArrayList<Point> mouvementsValides){
  int nombreMouvements = mouvementsValides.size();
  int num = (int) (Math.random() * nombreMouvements);
  return mouvementsValides.get(num);
 }
}