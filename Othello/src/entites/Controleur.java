package entites;

import strategies.AlphaBeta;
import strategies.Difficulte;
import strategies.Minimax;

import java.awt.*;

public class Controleur {

    private Plateau plateau;
    private Vue vue;
    private int lignes;
    private int colonnes;
    private Difficulte difficulte;

    public Controleur(int lignes, int colonnes, Difficulte difficulte) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.difficulte = difficulte;
        initialiserVariables();
    }

    public void initialiserVariables() {
        plateau = new Plateau(lignes, colonnes);
        vue = new Vue(plateau);
    }

    public void jouerPartie() {
        while (!plateau.jeuTermine) {
            vue.afficherPlateau();
            jouerCoup();
        }
        vue.afficherResultatPartie();
    }

    public void jouerCoup() {
        while (!plateau.jeuTermine) {
            vue.afficherPlateau();  // Afficher le plateau avant chaque tour
            vue.afficherMessage("Tour du joueur " + plateau.tourJoueur + ".");

            if (plateau.tourJoueur == Couleur.NOIR) {
                Point coup = vue.obtenirCoupJoueur();
                plateau.jouerCoupJoueur(coup);
            } else if (plateau.tourJoueur == Couleur.BLANC) {
                tourOrdi();
            }
        }

        vue.afficherPlateau();  // Afficher le plateau à la fin de la partie
        vue.afficherResultatPartie();
    }


    private void jouerCoupJoueur() {
        Point coup;
        do {
            vue.afficherMessage("Tour du joueur Noir.");
            coup = vue.obtenirCoupJoueur();
        } while (!plateau.mouvementValide(coup, Couleur.NOIR));

        plateau.placerDisque(coup, Couleur.NOIR);
        vue.afficherPlateau();  // Ajoutez cet appel pour afficher le plateau après chaque coup
        vue.afficherMessage("Coup du joueur Noir : " + convertirCoordonnees(coup));
        plateau.miseAjourPlateau();  // Assurez-vous d'appeler cette méthode après avoir placé le disque
    }


    private void tourOrdi() {
        if (difficulte == Difficulte.FACILE)
            ordiFacile();
        else if (difficulte == Difficulte.DIFFICILE)
            ordiDifficile();
    }

    private void ordiFacile() {
        Minimax minimax = new Minimax(this);
        try {
            Thread.sleep(300);
        } catch (InterruptedException interruptedException) {
            System.out.println("Erreur repos");
        }
        plateau = minimax.mouvement(plateau);
        vue.afficherMessage("Coup de l'ordinateur Blanc.");
    }

    private void ordiDifficile() {
        AlphaBeta alphaBeta = new AlphaBeta(this);
        plateau = alphaBeta.mouvement(plateau);
        vue.afficherMessage("Coup de l'ordinateur Blanc.");
    }

    private String convertirCoordonnees(Point point) {
        char colonneChar = (char) ('A' + point.x);
        int ligne = point.y + 1;
        return "" + colonneChar + ligne;
    }
}
