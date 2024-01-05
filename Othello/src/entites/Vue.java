package entites;

import java.awt.*;
import java.util.Scanner;

public class Vue {

    private Plateau plateau;

    public Vue(Plateau plateau) {
        this.plateau = plateau;
    }

    public void afficherPlateau() {
        System.out.println("  A B C D E F G H");
        for (int ligne = plateau.lignes - 1; ligne >= 0; ligne--) {
            System.out.print(ligne + 1 + "|");
            for (int colonne = 0; colonne < plateau.colonnes; colonne++) {
                char symbole = '_';
                if (plateau.othellier[ligne][colonne].couleur == Couleur.NOIR) {
                    symbole = '●';
                } else if (plateau.othellier[ligne][colonne].couleur == Couleur.BLANC) {
                    symbole = '○';
                }
                System.out.print(symbole + "|");
            }
            System.out.println();
        }
        System.out.println("  ---------------");
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public Point obtenirCoupJoueur() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez votre coup (par exemple, A4) :");

        String choix = scanner.next().toUpperCase();

        if (choix.length() != 2) {
            System.out.println("Saisie invalide. Utilisez le format (par exemple, A4).");
            return obtenirCoupJoueur();
        }

        char colonneChar = choix.charAt(0);
        int ligne = Character.getNumericValue(choix.charAt(1)) - 1;

        if (ligne < 0 || ligne >= plateau.lignes || colonneChar < 'A' || colonneChar > 'A' + plateau.colonnes) {
            System.out.println("Coup invalide. Assurez-vous d'utiliser le format correct (par exemple, A4).");
            return obtenirCoupJoueur();
        }

        int colonne = colonneChar - 'A';
        return new Point(colonne, ligne);
    }

    public void afficherResultatPartie() {
        if (plateau.jeuTermine) {
            if (plateau.gagnant == Couleur.BLANC) {
                afficherMessage("Victoire du joueur Blanc !");
            } else if (plateau.gagnant == Couleur.NOIR) {
                afficherMessage("Victoire du joueur Noir !");
            } else {
                afficherMessage("Match nul !");
            }
        }
    }
}
