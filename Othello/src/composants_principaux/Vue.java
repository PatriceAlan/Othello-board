package composants_principaux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;


/**
 * La classe Vue représente la vue du jeu.
 * Elle contient des éléments d'interface utilisateur tels que le plateau de jeu, les boutons, les champs de texte, etc.
 */
public class Vue {
	public Plateau plateau;
	public Controleur controleur;
	public JFrame frame;
	public JPanel panel;
	public Bouton[][] bouton;

	public JButton boutonJeuIA;
	
	public int hauteurFrame;
	public int largeurFrame;
	public int largeurPlateau;
	public int largeurCase;
	
	public JButton boutonAnnuler;
	public JButton boutonRefaire;

	public JTextField nomBlanc;
	public JTextField nomNoir;
	
	public JTextField victoiresBlanc;
	public JTextField victoiresNoir;
	
	public JTextField disquesBlanc;
	public JTextField disquesNoir;
	
	public JButton abandonBlanc;
	public JButton abandonNoir;

	public JButton nouvellePartieFacile;
	public JButton nouvellePartieMoyenne;
	public JButton nouvellePartieDifficile;

	public ImageIcon iconeNoir;
	public ImageIcon iconeBlanc;
	
	public JTextField texteDifficulte;
	
	java.awt.Color marronFonce;

	java.awt.Color marronClair;


	/**
	 * Constructeur de la classe Vue.
	 * Initialise la vue avec le plateau et le contrôleur donnés.
	 *
	 * @param plateau le plateau de jeu
	 * @param controleur le contrôleur du jeu
	 */
	public Vue(Plateau plateau, Controleur controleur){
		bouton = new Bouton[plateau.lignes][plateau.colonnes];
		hauteurFrame = 739;
		largeurFrame = 917;
		largeurPlateau = 600;
		largeurCase = largeurPlateau / plateau.lignes;




		marronFonce = new Color(102, 51, 0);


		marronClair = new Color(153, 102, 51);



		frame = new JFrame("Othello - PatriceAlan");
		iconeNoir = new ImageIcon(getClass().getResource("images/disque_noir.png"));
		iconeBlanc = new ImageIcon(getClass().getResource("images/disque_blanc.png"));

		initialisation(plateau, controleur);
	}


	/**
	 * Initialise la vue avec le plateau et le contrôleur donnés.
	 *
	 * @param plateau le plateau de jeu
	 * @param controleur le contrôleur du jeu
	 */
	public void initialisation(Plateau plateau, Controleur controleur){
		this.plateau = plateau;
		this.controleur = controleur;
		

		panel = new JPanel(null);
		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				bouton[lig][col] = new Bouton(col, lig);
			}
		}

		frame.setSize(largeurFrame, hauteurFrame);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				

				Disque disqueActuel = plateau.othellier[lig][col];
				Bouton boutonActuel = bouton[lig][col];
				if (disqueActuel.couleur == Couleur.NOIR)
					boutonActuel.setIcon(iconeNoir);
				else if (disqueActuel.couleur == Couleur.BLANC)
					boutonActuel.setIcon(iconeBlanc);

				

				int xPositionGUI = getXPositionGUI(col);
				int yPositionGUI = getYPositionGUI(lig);
				boutonActuel.setBounds(xPositionGUI, yPositionGUI, largeurCase, largeurCase);
				
				setFondecran(boutonActuel);
				panel.add(boutonActuel);
			}
		}

		boutonJeuIA = new JButton("IA vs IA");
		boutonJeuIA.setBounds(610, 200, 150, 40);
		panel.add(boutonJeuIA);

		creationBoutonsInterface(panel);
		miseAjourVue();
		
		frame.setContentPane(panel);
		frame.setVisible(true);
	}


	/**
	 * Met en évidence les mouvements valides pour le joueur actuel.
	 */
	public void eclairerMouvements(){
		Joueur currentJoueur = plateau.getJoueurActuel();
		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				Point pointActuel = new Point(col, lig);
				Bouton boutonActuel = bouton[lig][col];
				if (currentJoueur.mouvementsValides.contains(pointActuel))
					boutonActuel.setBackground(marronClair);
				else
					boutonActuel.setBackground(marronFonce);
			}
		}
	}


	/**
	 * Crée les boutons de l'interface utilisateur.
	 *
	 * @param panel le panneau sur lequel ajouter les boutons
	 */
	public void creationBoutonsInterface(JPanel panel){
		creationNoms(panel);
		creationVictoires(panel);
		creationNombreDisques(panel);
		creationBoutonAnnuler(panel);
		creationBoutonRefaire(panel);
		cerationBoutonAbandon(panel);
		creationNouveauBoutonNouvellePartie(panel);
		creationTexteDifficulte(panel);
	}


	public void creationNoms(JPanel panel){
		nomNoir = new JTextField("Joueur Noir");
		nomNoir.setBounds(650, 20, 200, 40);
		nomNoir.setBackground(null);
		nomNoir.setBorder(null);
		nomNoir.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nomNoir);
		nomBlanc = new JTextField("Joueur Blanc");
		nomBlanc.setBounds(650, 540, 200, 40);
		nomBlanc.setBackground(marronFonce);
		nomBlanc.setBorder(null);
		nomBlanc.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nomBlanc);
	}


	public void creationVictoires(JPanel panel){
		victoiresBlanc = new JTextField("Blanc # Victoires: " + controleur.victoiresBlanc);
		victoiresBlanc.setBounds(610, 500, 100, 40);
		victoiresBlanc.setEditable(false);
		victoiresBlanc.setBorder(null);
		panel.add(victoiresBlanc);
		victoiresNoir = new JTextField("Noir # Victoires: " + controleur.victoiresNoir);
		victoiresNoir.setBounds(610, 60, 100, 40);
		victoiresNoir.setBorder(null);
		victoiresNoir.setEditable(false);
		panel.add(victoiresNoir);
	}
	

	public void creationNombreDisques(JPanel panel){
		disquesBlanc = new JTextField("Disques Blancs: " + plateau.joueurBlanc.score);
		disquesBlanc.setBounds(610, 470, 100, 40);
		disquesBlanc.setEditable(false);
		disquesBlanc.setBorder(null);
		panel.add(disquesBlanc);
		disquesNoir = new JTextField("Disques Noirs: " + plateau.joueurNoir.score);
		disquesNoir.setBounds(610, 90, 100, 40);
		disquesNoir.setBorder(null);
		disquesNoir.setEditable(false);
		panel.add(disquesNoir);
	}
	

	public void creationBoutonAnnuler(JPanel panel){
		boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.setBounds(630, 250, 100, 40);
		panel.add(boutonAnnuler);
	}
	

	public void creationBoutonRefaire(JPanel panel){
		boutonRefaire = new JButton("Refaire");
		boutonRefaire.setBounds(630, 310, 100, 40);
		panel.add(boutonRefaire);
	}
	

	public void cerationBoutonAbandon(JPanel panel){
		abandonBlanc = new JButton("Blanc abandonne");
		abandonBlanc.setBounds(720, 485, 150, 30);
		panel.add(abandonBlanc);
		abandonNoir = new JButton("Noir abandonne");
		abandonNoir.setBounds(720, 85, 150, 30);
		panel.add(abandonNoir);
	}
	

	public void creationNouveauBoutonNouvellePartie(JPanel panel){
		nouvellePartieFacile = new JButton("Nouvelle partie - FACILE");
		nouvellePartieFacile.setBounds(10, 660, 180, 30);
		panel.add(nouvellePartieFacile);
		nouvellePartieMoyenne = new JButton("Nouvelle partie - Moyenne");
		nouvellePartieMoyenne.setBounds(210, 660, 180, 30);
		panel.add(nouvellePartieMoyenne);
		nouvellePartieDifficile = new JButton("Nouvelle partie - Difficile");
		nouvellePartieDifficile.setBounds(410, 660, 180, 30);
		panel.add(nouvellePartieDifficile);
	}


	public void creationTexteDifficulte(JPanel panel){
		texteDifficulte = new JTextField("Difficulté: " + controleur.difficulte);
		texteDifficulte.setBounds(257, 600, 100, 40);
		texteDifficulte.setEditable(false);
		texteDifficulte.setBorder(null);
		panel.add(texteDifficulte);
	}


	/**
	 * Met à jour la vue en fonction de l'état actuel du jeu.
	 */
	public void miseAjourVue(){
		miseAJourVisuelTours();
		miseAJourJeuTermine();
		miseAJourIcones();
		miseAjourDisques();
		eclairerMouvements();
	}
	

	public void miseAJourVisuelTours(){
		if (plateau.tourJoueur == Couleur.BLANC){
			nomNoir.setBackground(null);
			nomBlanc.setBackground(marronClair);
			abandonBlanc.setEnabled(true);
			abandonNoir.setEnabled(false);
		}
		else{
			nomNoir.setBackground(marronClair);
			nomBlanc.setBackground(null);
			abandonBlanc.setEnabled(false);
			abandonNoir.setEnabled(true);
		}
	}
	

	public void miseAJourJeuTermine(){
		if (plateau.jeuTermine){
			if (plateau.gagnant == Couleur.BLANC){
				javax.swing.JOptionPane.showMessageDialog(null, "Victoire des blancs!");
				controleur.victoiresBlanc++;
			}
			else if (plateau.gagnant == Couleur.NOIR){
				javax.swing.JOptionPane.showMessageDialog(null, "Victoire des noirs!");
				controleur.victoiresNoir++;
			}
			else
				javax.swing.JOptionPane.showMessageDialog(null, "Match nul!");
			activerBouttons(false);
		}
	}
	

	public void miseAJourIcones(){
		for (int lig = 0; lig < plateau.lignes; lig++){
			for (int col = 0; col < plateau.colonnes; col++){
				Disque disqueActuel = plateau.othellier[lig][col];
				Bouton boutonActuel = bouton[lig][col];
				if (disqueActuel.couleur == Couleur.NOIR)
					boutonActuel.setIcon(iconeNoir);
				else if (disqueActuel.couleur == Couleur.BLANC)
					boutonActuel.setIcon(iconeBlanc);
				else
					boutonActuel.setIcon(null);
			}
		}
	}
	

	public void miseAjourDisques(){
		disquesBlanc.setText("Disques Blancs: " + plateau.joueurBlanc.score);
		disquesNoir.setText("Disques Noirs: " + plateau.joueurNoir.score);
	}


	/**
	 * Active ou désactive les boutons de l'interface utilisateur.
	 *
	 * @param activer vrai pour activer les boutons, faux pour les désactiver
	 */
	public void activerBouttons(Boolean activer){
		boutonRefaire.setEnabled(activer);
		boutonAnnuler.setEnabled(activer);
		abandonNoir.setEnabled(activer);
		abandonBlanc.setEnabled(activer);
	}
	

	public void setFondecran(Bouton bouton){
		bouton.setBackground(marronFonce);
	}
	

	public int getXPositionGUI(int x){
		return x * largeurCase;
	}
	

	public int getYPositionGUI(int y){
		int yOffset = largeurCase;
		return largeurPlateau - (y * largeurCase) - yOffset;
	}
	

	public Bouton getBoutton(Point point){
		return bouton[point.y][point.x];
	}
	

	public void setIcone(ImageIcon image, Bouton bouton){
		bouton.setIcon(image);
	}


	/**
	 * Ajoute un gestionnaire de souris à un bouton.
	 *
	 * @param mouseListener le gestionnaire de souris à ajouter
	 * @param boutton le bouton auquel ajoute le gestionnaire de souris
	 */
	public void gestionnaireSouris(MouseListener mouseListener, JButton boutton){
		boutton.addMouseListener(mouseListener);
	}
}
