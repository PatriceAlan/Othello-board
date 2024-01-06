package composants_principaux;
/** \brief The main classes for our Othello Program */

import javax.swing.JButton;
import java.awt.Point;

/**
 * \brief
 * An extension of JButton. Button knows its Position on the GUI.
 * @author Rodney Shaghoulian
 */
public class Boutton extends JButton{
	public int xPos;
	public int yPos;
	

	public Boutton(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Creation d'un boutton
     */
	public Point creationPoint(){
		return new Point(xPos, yPos);
	}
}