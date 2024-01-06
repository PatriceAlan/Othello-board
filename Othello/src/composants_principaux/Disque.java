package composants_principaux;

/**
 * La classe Disque représente un disque dans le jeu.
 * Elle contient une couleur qui peut être BLANC, NOIR ou VIDE.
 */
public class Disque {

 // La couleur du disque
 public Couleur couleur;

 /**
  * Constructeur de la classe Disque.
  * Initialise la couleur du disque.
  *
  * @param couleur la couleur du disque
  */
 public Disque(Couleur couleur){
  this.couleur = couleur;
 }

 /**
  * Constructeur de copie de la classe Disque.
  * Crée un nouveau disque avec la même couleur que le disque donné.
  *
  * @param autreDisque le disque à copier
  */
 public Disque(Disque autreDisque){
  couleur = autreDisque.couleur;
 }

 /**
  * Change la couleur du disque.
  *
  * @param couleur la nouvelle couleur du disque
  */
 public void changerCouleur(Couleur couleur){
  this.couleur = couleur;
 }
}