package entites;

import java.awt.*;
import java.util.ArrayList;

public class Commande {

    public Plateau plateau;
    public Couleur couleur;
    public Point destination;
    public ArrayList<Point> pointsCaptures;


    /**
     * Constructeur de la classe classes Commande
     */
    public Commande(Plateau plateau, Couleur couleur, Point destination) {
        this.plateau = plateau;
        this.couleur = couleur;
        this.destination = new Point(destination);
        pointsCaptures = new ArrayList<Point>();
    }

    /**
     * Methode qui execute la commande
     */
    public void execution(){
        plateau.tournerDisque(destination, couleur);
        pointsCaptures = plateau.tournerCaptures(destination, couleur);
        plateau.miseAjourPlateau();
    }
}
