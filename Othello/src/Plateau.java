/**
 * La classe Plateau représente le plateau de jeu du jeu Othello.
 * Il contient une matrice 8x8 pour représenter l'othellier.
 */
public class Plateau {


    private char[][] othellier;
    private final char pionNoir = '\u25CB';
    private final char pionBlanc = '\u25CF';

    /**
     * Constructeur de la classe Plateau.
     * Initialise l'othellier avec des cases vides et place les pions de départ.
     */
    public Plateau(){
        othellier = new char[8][8];
        initialiserPlateau();
    }

    /**
     * Méthode privée pour initialiser l'othellier avec des cases vides
     * et placer les pions de départ.
     */
    private void initialiserPlateau(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                othellier[i][j] = ' ';
            }
        }
        othellier[3][3] = pionBlanc;
        othellier[3][4] = pionNoir;
        othellier[4][3] = pionNoir;
        othellier[4][4] = pionBlanc;
    }

    /**
     * Méthode pour afficher le plateau de jeu.
     */
    public void afficherPlateau(){

        System.out.println("Blanc : "+pionBlanc);
        System.out.println("Noir: "+pionNoir);

        System.out.println("\n    A   B   C   D   E   F   G   H");
        for(int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print("+---");
            }
            System.out.println("+");

            System.out.print("  ");
            for (int j = 0; j < 8; j++) {
                System.out.print("| " + othellier[i][j] + " ");
            }
            System.out.println("|");
        }

        System.out.print("  ");
        for (int j = 0; j < 8; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    /**
     * Méthode principale pour tester la classe Plateau.
     * Crée un objet Plateau et affiche le plateau de jeu.
     */
    public static void main(String[] args) {
        
        Plateau plateau = new Plateau();
        plateau.afficherPlateau();
    }
    
}
