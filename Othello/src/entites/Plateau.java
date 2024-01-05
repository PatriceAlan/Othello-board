package entites;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Plateau {

    public final int lignes;
    public final int colonnes;
    public Disque[][] othellier;
    public Joueur joueurNoir;
    public Joueur joueurBlanc;
    public Couleur tourJoueur;
    public boolean jeuTermine;
    public Couleur gagnant;
    public int tour;
    public Vue vue;

    /**
     * Constructeur par défaut
     */
    public Plateau(int lignes, int colonnes) {
        this.colonnes = lignes;
        this.lignes = colonnes;
        othellier = new Disque[lignes][colonnes];
        vue = new Vue(this);

        initialiserPlateau();

        joueurNoir = new Joueur(this, Couleur.NOIR, false);
        joueurBlanc = new Joueur(this, Couleur.BLANC, false);
        tourJoueur = Couleur.NOIR;
        jeuTermine = false;
        gagnant = null;
        tour = 1;
    }

    public Vue getVue() {
        return vue;
    }

    /**
     * Constructeur de copie
     */
    public Plateau(Plateau autrePlateau) {
        this.colonnes = autrePlateau.colonnes;
        this.lignes = autrePlateau.lignes;
        othellier = new Disque[lignes][colonnes];

        for (int ligne = 0; ligne < lignes; ligne++) {
            for (int colonne = 0; colonne < colonnes; colonne++) {
                othellier[ligne][colonne] = new Disque(autrePlateau.othellier[ligne][colonne]);
            }
        }

        joueurNoir = new Joueur(autrePlateau.joueurNoir);
        joueurBlanc = new Joueur(autrePlateau.joueurBlanc);
        tourJoueur = autrePlateau.tourJoueur;
        jeuTermine = autrePlateau.jeuTermine;
        gagnant = autrePlateau.gagnant;
        tour = autrePlateau.tour;
    }

    /**
     * Initialise le plateau avec les disques de départ
     */
    public void initialiserPlateau() {
        for (int ligne = 0; ligne < lignes; ligne++) {
            for (int colonne = 0; colonne < colonnes; colonne++) {
                othellier[ligne][colonne] = new Disque(Couleur.VIDE);
            }
        }

        int centreRondBas = lignes / 2 - 1;
        int centreRondHaut = lignes / 2;
        othellier[centreRondBas][centreRondBas].changerCouleur(Couleur.NOIR);
        othellier[centreRondHaut][centreRondHaut].changerCouleur(Couleur.NOIR);
        othellier[centreRondBas][centreRondHaut].changerCouleur(Couleur.BLANC);
        othellier[centreRondHaut][centreRondBas].changerCouleur(Couleur.BLANC);
    }

    /**
     * Retourne vrai si la position donnée est valide
     */
    public boolean positionValide(Point position) {
        return position.x >= 0 && position.x < lignes && position.y >= 0 && position.y < colonnes;
    }

    /**
     * Retourne la couleur du disque à la position donnée
     */
    public Couleur couleurDisque(Point position) {
        return othellier[position.x][position.y].couleur;
    }

    /**
     * Retourne la couleur adverse
     */
    public Couleur couleurAdverse(Couleur couleur) {
        if (couleur == Couleur.NOIR) {
            return Couleur.BLANC;
        } else if (couleur == Couleur.BLANC) {
            return Couleur.NOIR;
        }
        return null;
    }

    /**
     * Met à jour le tour du joueur
     */
    public void miseAjourTour() {
        if (tourJoueur == Couleur.BLANC && !joueurNoir.coupsPossibles.isEmpty())
            tourJoueur = Couleur.NOIR;
        else if (tourJoueur == Couleur.NOIR && !joueurBlanc.coupsPossibles.isEmpty())
            tourJoueur = Couleur.BLANC;
        tour = joueurNoir.score + joueurBlanc.score - 3;
    }

    /**
     * Met à jour le statut du jeu
     */
    public void miseAJourStatutJeu() {
        if (joueurNoir.coupsPossibles.isEmpty() && joueurBlanc.coupsPossibles.isEmpty()) {
            jeuTermine = true;
            if (joueurNoir.score > joueurBlanc.score) {
                gagnant = Couleur.NOIR;
            } else if (joueurBlanc.score > joueurNoir.score) {
                gagnant = Couleur.BLANC;
            } else {
                gagnant = Couleur.VIDE;
            }
        }
    }

    /**
     * Met à jour le tableau
     */
    public void miseAjourPlateau(){
        joueurNoir.miseAJourCoupsPossibles(this);
        joueurBlanc.miseAJourCoupsPossibles(this);
        miseAjourTour();
        miseAJourStatutJeu();
    }

    /**
     * Retourne vrai si le mouvement est valide
     */
    public boolean mouvementValide(Point position, Couleur couleur) {
        if (position == null || couleur == null || !positionValide(position) || couleur == Couleur.VIDE || othellier[position.x][position.y].couleur != Couleur.VIDE)
            return false;

        if (ValideDansToutesLesDirections(position, couleur, 1, 0)) { // Droite
                return true;
            } else if (ValideDansToutesLesDirections(position, couleur, -1, 0)) { // Gauche
                return true;
            } else if (ValideDansToutesLesDirections(position, couleur, 0, 1)) { // Haut
                return true;
            } else if (ValideDansToutesLesDirections(position, couleur, 0, -1)) { // Bas
                return true;
            } else if (ValideDansToutesLesDirections(position, couleur, 1, 1)) { // Haut droite
                return true;
            } else if (ValideDansToutesLesDirections(position, couleur, 1, -1)) { // Haut gauche
                return true;
            } else // Bas gauche
            if (ValideDansToutesLesDirections(position, couleur, -1, 1)) { // Bas droit
                return true;
            } else return ValideDansToutesLesDirections(position, couleur, -1, -1);
    }

    /**
     * Retourne vrai si le mouvement est valide dans toutes les directions
     */
    public boolean ValideDansToutesLesDirections(Point position, Couleur couleur, int xDirection, int yDirection) {
        Point pos = new Point(position);
        Couleur couleurAdverse = couleurAdverse(couleur);
        pos.x += xDirection;
        pos.y += yDirection;
        if (!positionValide(pos) || couleurDisque(pos) != couleurAdverse)
            return false;
        pos.x += xDirection;
        pos.y += yDirection;
        while (positionValide(pos)) {
            if (couleurDisque(pos) == Couleur.VIDE)
                return false;
            if (couleurDisque(pos) == couleur)
                return true;
            pos.x += xDirection;
            pos.y += yDirection;
        }
        return false;
    }

    /**
     * Retire le disque à la position donnée
     */
    public void retirerDisque(Point position) {
      Couleur ancienneCouleur = othellier[position.x][position.y].couleur;
        othellier[position.x][position.y].changerCouleur(Couleur.VIDE);

        if (ancienneCouleur == Couleur.NOIR) {
            joueurNoir.score--;
        } else if (ancienneCouleur == Couleur.BLANC) {
            joueurBlanc.score--;
        }
    }

    /**
     * Tourne le disque à la position donnée
     */
    public void tournerDisque(Point position, Couleur nouvelleCouleur) {
       Couleur ancienneCouleur = othellier[position.x][position.y].couleur;
    othellier[position.x][position.y].changerCouleur(nouvelleCouleur);
        if (ancienneCouleur == Couleur.NOIR) {
            joueurNoir.score--;
        } else if (ancienneCouleur == Couleur.BLANC) {
            joueurBlanc.score--;
        }
    }

    /**
     * Retourne les disques retournés
     */
    public ArrayList<Point> tournerCaptures(Point position, Couleur couleur) {
        ArrayList<Point> disquesRetournes = new ArrayList<Point>();
        ArrayList<Point> disquesRetournesDansDirection = new ArrayList<Point>();

        if(!positionValide(position))
            return disquesRetournes;
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, 1, 0); // Droite
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, -1, 0); // Gauche
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, 0, 1); // Haut
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, 0, -1); // Bas
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, 1, 1); // Haut droite
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, 1, -1); // Haut gauche
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, -1, 1); // Bas droit
        disquesRetournes.addAll(disquesRetournesDansDirection);
        disquesRetournesDansDirection = tournerDisquesDansDirection(position, couleur, -1, -1); // Bas gauche
        disquesRetournes.addAll(disquesRetournesDansDirection);

        return disquesRetournes;
    }

    /**
     * Retourne les disques retournés dans la direction donnée
     */
    public ArrayList<Point> tournerDisquesDansDirection(Point position, Couleur couleur, int xDirection, int yDirection) {
        ArrayList<Point> disquesRetournes = new ArrayList<Point>();
        Point pos = new Point(position);
        Couleur couleurAdverse = couleurAdverse(couleur);
        pos.x += xDirection;
        pos.y += yDirection;
        while (positionValide(pos) && couleurDisque(pos) == couleurAdverse) {
            disquesRetournes.add(new Point(pos));
            pos.x += xDirection;
            pos.y += yDirection;
        }
        return disquesRetournes;
    }

    /**
     * Place le disque à la position donnée
     */
    public void placerDisque(Point pos, Couleur couleur){
        tournerDisque(pos, couleur);
        tournerCaptures(pos, couleur);
        miseAjourPlateau();
    }

    public void jouerCoupJoueur(Point coup) {
        Scanner scanner = new Scanner(System.in);

        while (!mouvementValide(coup, tourJoueur)) {
            vue.afficherMessage("Coup invalide. Veuillez réessayer.");
            // Demandez au joueur de saisir un nouveau coup
            coup = saisirCoup(scanner);
        }

        placerDisque(coup, tourJoueur);
        miseAjourPlateau();  // Assurez-vous d'appeler cette méthode après avoir placé le disque
    }

    private Point saisirCoup(Scanner scanner) {
        Point coup = new Point();

        // Affiche les coups possibles pour le joueur actuel
        vue.afficherMessage("Coups possibles : " + joueurActuel().coupsPossibles);

        vue.afficherMessage("Entrez votre coup : ");
        String input = scanner.nextLine().toUpperCase();

        // Assurez-vous que l'entrée est au format attendu (par exemple, A4)
        if (input.length() == 2 && Character.isLetter(input.charAt(0)) && Character.isDigit(input.charAt(1))) {
            int col = input.charAt(0) - 'A';
            int row = input.charAt(1) - '1';

            coup.setLocation(row, col);

            // Assurez-vous que le coup est parmi les coups possibles
            if (!joueurActuel().coupsPossibles.contains(coup)) {
                vue.afficherMessage("Coup invalide. Veuillez réessayer.");
                // Récupérez un nouveau coup récursivement
                coup = saisirCoup(scanner);
            }
        } else {
            vue.afficherMessage("Format de coup incorrect. Veuillez utiliser le format (par exemple, A4).");
            // Récupérez un nouveau coup récursivement
            coup = saisirCoup(scanner);
        }

        return coup;
    }




    /**
     * Retourne le joueur actuel
     */
    public Joueur joueurActuel(){
        if(tourJoueur == Couleur.NOIR)
            return joueurNoir;
        else
            return joueurBlanc;

    }

    /** Compte le nombre de cases X occupées par une couleur donnée
     */
    public int xCasesOccupees(Couleur couleur){
        int casesOccupees = 0;
        if (couleurDisque(new Point(1,1)) == couleur)
            casesOccupees++;
        if (couleurDisque(new Point(1, lignes - 2)) == couleur)
            casesOccupees++;
        if (couleurDisque(new Point(lignes - 2, 1)) == couleur)
            casesOccupees++;
        if (couleurDisque(new Point(lignes - 2, lignes - 2)) == couleur)
            casesOccupees++;

        return casesOccupees;
    }

    /** Compte le nombre de mauvaises cases X occupées par une couleur donnée
     */
    public int mauvaisesXCasesOccupees(Couleur couleur){
        int mCases = 0;
        if (couleurDisque(new Point(0,1)) == couleur)
            mCases++;
        if (couleurDisque(new Point(1,0)) == couleur)
            mCases++;
        if (couleurDisque(new Point(0, lignes - 2)) == couleur)
            mCases++;
        if (couleurDisque(new Point(1, lignes - 1)) == couleur)
            mCases++;
        if (couleurDisque(new Point(lignes - 2, 0)) == couleur)
            mCases++;
        if (couleurDisque(new Point(lignes - 1, 1)) == couleur)
            mCases++;
        if (couleurDisque(new Point(lignes - 2, lignes - 1)) == couleur)
            mCases++;
        if (couleurDisque(new Point(lignes - 1, lignes - 2)) == couleur)
            mCases++;

        return mCases;
    }

    /** Compte le nombre de cases C occupées par une couleur donnée
     */
    public int cCasesOccupees(Couleur couleur){
        int cCases = 0;

        if (couleurDisque(new Point(0,1)) == couleur)
            cCases++;
        if (couleurDisque(new Point(1,0)) == couleur)
            cCases++;
        if (couleurDisque(new Point(0, lignes - 2)) == couleur)
            cCases++;
        if (couleurDisque(new Point(1, lignes - 1)) == couleur)
            cCases++;

        if (couleurDisque(new Point(colonnes - 2, 0)) == couleur)
            cCases++;
        if (couleurDisque(new Point(colonnes - 1, 1)) == couleur)
            cCases++;
        if (couleurDisque(new Point(colonnes - 2, lignes - 1)) == couleur)
            cCases++;
        if (couleurDisque(new Point(colonnes - 1, lignes - 2)) == couleur)
            cCases++;

        return cCases;
    }

    /** Compte le nombre de mauvaises cases C occupées par une couleur donnée
     */
    public int mauvaisesCCasesOccupees(Couleur couleur){
        int mCCases = 0;

        if (couleurDisque(new Point(0,1)) == couleur && couleurDisque(new Point(0,0)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(1,0)) == couleur && couleurDisque(new Point(0,0)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(0, lignes - 2)) == couleur && couleurDisque(new Point(0, lignes - 1)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(1, lignes - 1)) == couleur && couleurDisque(new Point(0, lignes - 1)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(colonnes - 2, 0)) == couleur && couleurDisque(new Point(colonnes - 1, 0)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(colonnes - 1, 1)) == couleur && couleurDisque(new Point(colonnes - 1, 0)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(colonnes - 2, lignes - 1)) == couleur && couleurDisque(new Point(colonnes - 1, lignes - 1)) == Couleur.VIDE)
            mCCases++;
        if (couleurDisque(new Point(colonnes - 1, lignes - 2)) == couleur && couleurDisque(new Point(colonnes - 1, lignes - 1)) == Couleur.VIDE)
            mCCases++;

        return mCCases;
    }

    /** Compte le nombre de coins occupés par une couleur donnée
     */
    public int coinsOccupes(Couleur couleur){
        int coins = 0;
        if (couleurDisque(new Point(0,0)) == couleur)
            coins++;
        if (couleurDisque(new Point(0, lignes - 1)) == couleur)
            coins++;
        if (couleurDisque(new Point(lignes - 1, 0)) == couleur)
            coins++;
        if (couleurDisque(new Point(lignes - 1, lignes - 1)) == couleur)
            coins++;

        return coins;
    }

/** Affiche l'othellier
*/
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int ligne = lignes - 1; ligne >= 0; ligne--){
            for(int colonne = 0; colonne < colonnes; colonne++){
                if(othellier[ligne][colonne].couleur == Couleur.NOIR)
                    sb.append('●');
                else if (othellier[ligne][colonne].couleur == Couleur.BLANC)
                    sb.append('○');
                else
                    sb.append("-");
                sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
