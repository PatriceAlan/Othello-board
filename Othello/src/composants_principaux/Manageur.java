package composants_principaux;

import java.util.Stack;

/**
 * La classe Manageur gère les commandes du jeu.
 * Elle contient deux piles pour gérer les commandes à annuler et à refaire.
 */
public class Manageur {
 // La pile des commandes à annuler
 public Stack<Commande> annuler = new Stack<Commande>();
 // La pile des commandes à refaire
 public Stack<Commande> refaire = new Stack<Commande>();

 /**
  * Exécute une commande et l'ajoute à la pile des commandes à annuler.
  * Vide la pile des commandes à refaire.
  *
  * @param commande la commande à exécuter
  */
 public void executionCommande(Commande commande) {
  commande.execution();
  annuler.push(commande);
  refaire.clear();
 }

 /**
  * Vérifie si la pile des commandes à annuler est vide.
  *
  * @return vrai si la pile des commandes à annuler n'est pas vide, faux sinon
  */
 public boolean annulerDisponible() {
  return !annuler.empty();
 }

 /**
  * Annule la dernière commande et l'ajoute à la pile des commandes à refaire.
  */
 public void annuler() {
  if (annulerDisponible()){
   Commande commande = annuler.pop();
   commande.annuler();
   refaire.push(commande);
  }
 }

 /**
  * Vérifie si la pile des commandes à refaire est vide.
  *
  * @return vrai si la pile des commandes à refaire n'est pas vide, faux sinon
  */
 public boolean refaireDisponible() {
  return !refaire.empty();
 }

 /**
  * Refait la dernière commande annulée et l'ajoute à la pile des commandes à annuler.
  */
 public void refaire() {
  if (refaireDisponible()){
   Commande commande = refaire.pop();
   commande.execution();
   annuler.push(commande);
  }
 }
}