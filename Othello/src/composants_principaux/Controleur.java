package composants_principaux;

import gestionnaires.*;
import strategies.Minimax;
import strategies.AlphaBeta;
import strategies.Difficulte;
import strategies.StrategieAleatoire;

/**
 * La classe Controleur gère le jeu, y compris l'initialisation, la réinitialisation et le tour de l'ordinateur.
 */
public class Controleur {
 // Le plateau de jeu
 public Plateau plateau;
 // Le gestionnaire de commandes
 public Manageur manageur;

 // La vue du jeu
 public Vue vue;
 // Le nombre de victoires du joueur blanc
 public int victoiresBlanc = 0;
 // Le nombre de victoires du joueur noir
 public int victoiresNoir = 0;
 // Le nombre de lignes du plateau
 public int lignes;
 // Le nombre de colonnes du plateau
 public int colonnes;

 // La difficulté du jeu
 public Difficulte difficulte;

 /**
  * Constructeur de la classe Controleur.
  * Initialise les variables et la vue.
  *
  * @param lignes le nombre de lignes du plateau
  * @param colonnes le nombre de colonnes du plateau
  * @param difficulte la difficulté du jeu
  */
 public Controleur(int lignes, int colonnes, Difficulte difficulte) {
  this.lignes = lignes;
  this.colonnes = colonnes;
  this.difficulte = difficulte;
  initialisationVariables();
  vue = new Vue(plateau, this);
  gestionnaireSouris();
 }

 /**
  * Réinitialise le jeu.
  */
 public void reinitialiser(){
  initialisationVariables();
  vue.initialisation(plateau, this);
  gestionnaireSouris();
 }

 public void lancerPartieIAvsIA(Difficulte difficulteIA1, Difficulte difficulteIA2) {
  GestionnaireIAvsIA gestionnaireIAGame = new GestionnaireIAvsIA(this, difficulteIA1, difficulteIA2);
  gestionnaireIAGame.execute();
 }


 private void jouerCoupIA(Difficulte difficulteIA) {
  if (difficulteIA == Difficulte.FACILE)
   ordiFacile();
  else if (difficulteIA == Difficulte.MOYEN)
   ordiMoyen();
  else if (difficulteIA == Difficulte.DIFFICILE)
   ordiDifficile();
 }



 /**
  * Réinitialise le jeu avec une nouvelle difficulté.
  *
  * @param difficulte la nouvelle difficulté du jeu
  */
 public void reinitialiser(Difficulte difficulte){
  this.difficulte = difficulte;
  initialisationVariables();
  vue.initialisation(plateau, this);
  gestionnaireSouris();
 }

 /**
  * Initialise les variables du jeu.
  */
 public void initialisationVariables(){
  plateau = new Plateau(lignes, colonnes);
  manageur = new Manageur();
 }

 /**
  * Gère les événements de la souris.
  */
 public void gestionnaireSouris(){
  for (int i = 0; i < plateau.lignes; i++){
   for (int j = 0; j < plateau.colonnes; j++){
    vue.gestionnaireSouris(new GestionnaireDisque(this), vue.bouton[i][j]);
   }
  }
  vue.gestionnaireSouris(new GestionnaireAnnuler(this), vue.boutonAnnuler);
  vue.gestionnaireSouris(new GestionnaireRefaire(this), vue.boutonRefaire);
  vue.gestionnaireSouris(new GestionnaireAbandon(this), vue.abandonBlanc);
  vue.gestionnaireSouris(new GestionnaireAbandon(this), vue.abandonNoir);
  vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.FACILE),   vue.nouvellePartieFacile);
  vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.MOYEN), vue.nouvellePartieMoyenne);
  vue.gestionnaireSouris(new GestionnaireNouvellePartie(this, Difficulte.DIFFICILE),   vue.nouvellePartieDifficile);
  vue.gestionnaireSouris(new GestionnaireIAvsIAMouseAdapter(this, Difficulte.MOYEN, Difficulte.DIFFICILE), vue.boutonJeuIA);

 }

 /**
  * Gère le tour de l'ordinateur en fonction de la difficulté du jeu.
  */
 public void tourOrdi(){
  if (difficulte == Difficulte.FACILE)
   ordiFacile();
  else if (difficulte == Difficulte.MOYEN)
   ordiMoyen();
  else if (difficulte == Difficulte.DIFFICILE)
   ordiDifficile();
 }



 /**
  * Gère le tour de l'ordinateur en mode facile.
  */
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

 /**
  * Gère le tour de l'ordinateur en mode moyen.
  */
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

 /**
  * Gère le tour de l'ordinateur en mode difficile.
  */
 public void ordiDifficile(){
  while (plateau.tourJoueur == Couleur.BLANC && !plateau.jeuTermine){
   AlphaBeta alphaBeta = new AlphaBeta(this);
   plateau = alphaBeta.mouvement(plateau);
  }
 }
}