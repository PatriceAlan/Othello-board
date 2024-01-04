import java.awt.*;
import java.util.ArrayList;

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

    public Plateau() {
        this.colonnes = 8;
        this.lignes = 8;
        othellier = new Disque[lignes][colonnes];

        initialiserPlateau();

        joueurNoir = new Joueur(this, Couleur.NOIR, false);
        joueurBlanc = new Joueur(this, Couleur.BLANC, false);
        tourJoueur = Couleur.NOIR;
        jeuTermine = false;
        gagnant = null;
        tour = 1;
    }

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

    public boolean positionValide(Point position) {
        return position.x >= 0 && position.x < lignes && position.y >= 0 && position.y < colonnes;
    }

    public Couleur couleurDisque(Point position) {
        return othellier[position.x][position.y].couleur;
    }

    public Couleur couleurAdverse(Couleur couleur) {
        if (couleur == Couleur.NOIR) {
            return Couleur.BLANC;
        } else if (couleur == Couleur.BLANC) {
            return Couleur.NOIR;
        }
        return null;
    }

    public void miseAjourTour() {
        if (tourJoueur == Couleur.BLANC && !joueurNoir.coupsPossibles.isEmpty())
            tourJoueur = Couleur.NOIR;
        else if (tourJoueur == Couleur.NOIR && !joueurBlanc.coupsPossibles.isEmpty())
            tourJoueur = Couleur.BLANC;
        tour = joueurNoir.score + joueurBlanc.score - 3;
    }

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

    public void miseAjourTableau(){
        joueurNoir.miseAJourCoupsPossibles(this);
        joueurBlanc.miseAJourCoupsPossibles(this);
        miseAjourTour();
        miseAJourStatutJeu();
    }

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

    public void retirerDisque(Point position) {
      Couleur ancienneCouleur = othellier[position.x][position.y].couleur;
        othellier[position.x][position.y].changerCouleur(Couleur.VIDE);

        if (ancienneCouleur == Couleur.NOIR) {
            joueurNoir.score--;
        } else if (ancienneCouleur == Couleur.BLANC) {
            joueurBlanc.score--;
        }
    }

    public void tournerDisque(Point position, Couleur nouvelleCouleur) {
       Couleur ancienneCouleur = othellier[position.x][position.y].couleur;
    othellier[position.x][position.y].changerCouleur(nouvelleCouleur);
        if (ancienneCouleur == Couleur.NOIR) {
            joueurNoir.score--;
        } else if (ancienneCouleur == Couleur.BLANC) {
            joueurBlanc.score--;
        }
    }

    public ArrayList<Point> tournerDisques(Point position, Couleur couleur, int xDirection, int yDirection) {
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

    public ArrayList<Point> tournerDisquesDansDirection(Point position, Couleur couleur, int xDirection, int yDirection) {
        ArrayList<Point> disquesRetournes = new ArrayList<Point>();
        ArrayList<Point> disquesRetournesDansDirection = new ArrayList<Point>();
        Point pos = new Point(position);
        Couleur couleurAdverse = couleurAdverse(couleur);
        pos.x += xDirection;
        pos.y += yDirection;
        if (!positionValide(pos) || couleurDisque(pos) != couleurAdverse)
            return disquesRetournes;
        disquesRetournesDansDirection.add(pos);
        pos.x += xDirection;
        pos.y += yDirection;
        while (positionValide(pos)) {
            if (couleurDisque(pos) == Couleur.VIDE)
                return disquesRetournes;
            if (couleurDisque(pos) == couleur)
                return disquesRetournesDansDirection;
            disquesRetournesDansDirection.add(pos);
            pos.x += xDirection;
            pos.y += yDirection;
        }
        return disquesRetournes;
    }
}
