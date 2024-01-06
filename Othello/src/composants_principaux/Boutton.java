package composants_principaux;

import javax.swing.JButton;
import java.awt.Point;

/**
 * La classe Boutton étend JButton.
 * Elle représente un bouton avec des coordonnées x et y.
 */
public class Boutton extends JButton{
 // Position x du bouton
 public int xPos;
 // Position y du bouton
 public int yPos;

 /**
  * Constructeur de la classe Boutton.
  * Initialise les positions x et y du bouton.
  *
  * @param xPos la position x du bouton
  * @param yPos la position y du bouton
  */
 public Boutton(int xPos, int yPos){
  this.xPos = xPos;
  this.yPos = yPos;
 }

 /**
  * Crée un point avec les coordonnées x et y du bouton.
  *
  * @return un nouveau point avec les coordonnées x et y du bouton
  */
 public Point creationPoint(){
  return new Point(xPos, yPos);
 }
}