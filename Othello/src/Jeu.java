import entites.Controleur;
import strategies.Difficulte;

import java.util.Scanner;

public class Jeu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans le jeu Othello!");

        while (true) {
            System.out.println("\nMenu :");
            System.out.println("1. Nouvelle partie");
            System.out.println("2. Choisir la difficulté");
            System.out.println("3. Quitter");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    jouerPartie();
                    break;
                case 2:
                    choisirDifficulte();
                    break;
                case 3:
                    System.out.println("Merci d'avoir joué! Au revoir.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
            }
        }
    }

    private static void jouerPartie() {
        // Vous pouvez réutiliser le code existant pour créer et jouer une partie
        Controleur controleur = new Controleur(8, 8, Difficulte.FACILE);
        controleur.jouerPartie();
    }

    private static void choisirDifficulte() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez la difficulté :");
        System.out.println("1. Facile");
        System.out.println("2. Difficile");

        int choix = scanner.nextInt();
        Difficulte difficulte = null;

        switch (choix) {
            case 1:
                difficulte = Difficulte.FACILE;
                break;
            case 2:
                difficulte = Difficulte.DIFFICILE;
                break;
            default:
                System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
                choisirDifficulte(); // Redemande le choix en cas d'entrée incorrecte
                return;
        }

        // Créez le contrôleur avec le niveau de difficulté choisi
        Controleur controleur = new Controleur(8, 8, difficulte);
        controleur.jouerPartie();
    }

}
