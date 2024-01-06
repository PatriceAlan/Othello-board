package composants_principaux;

/**
 * \brief
 * A representation of the standard Disk, which is BLACK or WHITE
 * @author Rodney Shaghoulian
 *
 */
public class Disque {
	/* Data */
	public Couleur couleur;		///< An enumerated type. "WHITE, BLACK, or NONE"

	/**
	 * Constructor. Initializes color and position
	 * @param couleur		The Color (WHITE, BLACK or NONE) of the Disk
	 * @param position	The Euclidean (x,y) position of the Disk
	 */
	public Disque(Couleur couleur){
		this.couleur = couleur;
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherDisque		The other Disk to create a deep copy of
	 */
	public Disque(Disque otherDisque){
		couleur = otherDisque.couleur;
	}
	
	
	/**
	 * Changes color (and icon image) of current Disk
	 * @param couleur		The new color of the Disk
	 */
	public void changeColor(Couleur couleur){
		this.couleur = couleur;
	}
}
