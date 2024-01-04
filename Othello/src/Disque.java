public class Disque {

    public Couleur couleur;

    public Disque(Couleur couleur) {
        this.couleur = couleur;
    }

    public Disque(Disque autreDisque){
        couleur = autreDisque.couleur;
    }

    public void changerCouleur(Couleur couleur){
        this.couleur = couleur;
    }
}
