package composants_principaux;

import javax.swing.JButton;
import java.awt.Point;


public class Boutton extends JButton{
	public int xPos;
	public int yPos;
	

	public Boutton(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	

	public Point creationPoint(){
		return new Point(xPos, yPos);
	}
}