---

# Projet Othello - README

## A. Analyse

Cette section constitue le cœur de notre exploration, détaillant la modélisation et les principaux algorithmes développés pour notre joueur artificiel dans le contexte du jeu d'Othello. Cette partie cruciale du travail pratique vise à fournir une compréhension approfondie de notre approche algorithmique. Nous présentons ici une modélisation précise des structures de données adoptées pour représenter le plateau de jeu, les états du jeu, et les informations pertinentes à la prise de décision de l'IA. Cette modélisation s'accompagne d'une explication rationnelle des choix effectués, soulignant leur efficacité et leur pertinence.

## a. Packages

Dans la structuration de notre projet, trois packages jouent des rôles distincts, chacun contribuant de manière significative à la cohérence et à l'efficacité de l'application :

### 1. composants_principaux

Ce package central englobe toutes les classes qui forment l'épine dorsale du projet. Il héberge également le dossier d'images des disques, ajoutant une dimension visuelle à l'expérience utilisateur. Parmi les classes clés présentes, on retrouve :

- **Bouton** : Gère les interactions avec les boutons de l'interface utilisateur.
- **Commande** : Représente les commandes possibles dans le jeu.
- **Controleur** : Agit comme le contrôleur principal, coordonnant les interactions entre les différents composants.
- **Couleur** : Enumération représentant les couleurs des disques dans le jeu.
- **Disque** : Représente un disque avec sa couleur associée.
- **Jeu** : Encapsule la logique du jeu, y compris les règles et les états actuels.
- **Joueur** : Représente un joueur avec ses caractéristiques (IA ou humain).
- **Manager** : Gère la coordination entre les différents composants et la logique du jeu.
- **Plateau** : Représente le plateau de jeu avec toutes ses cases et les disques.
- **Vue** : Gère l'interface utilisateur et affiche le plateau de jeu.

### 2. gestionnaires

Ce dossier dédié rassemble les classes responsables de la gestion des interactions avec la souris. Les classes telles que **GestionnaireAbandon**, **GestionnaireAnnuler**, **GestionnaireDisque**, **GestionnaireNouvellePartie**, et **GestionnaireRefaire** orchestrent les différentes actions utilisateur de manière fluide et intuitive.

### 3. strategies

Au sein de ce package, chaque classe incarne une stratégie spécifique utilisée dans notre application. Parmi celles-ci, on compte :

- **AlphaBeta** : Implémente l'algorithme Alpha-Beta pour l'optimisation de la recherche.
- **ComparateurScoreBlanc** : Compare les scores pour les disques blancs.
- **Difficulte** : Enumération représentant les niveaux de difficulté pour les joueurs IA.
- **Minimax** : Implémente l'algorithme Minimax pour la prise de décision.
- **PlateauComparateurBlanc** : Compare les plateaux de jeu pour les disques blancs.
- **Strategie** : Interface définissant le contrat pour les différentes stratégies.
- **StrategieAleatoire** : Implémente une stratégie aléatoire pour les mouvements.
- **Utilite** : Classe utilitaire fournissant des méthodes d'évaluation et de calcul de l'utilité.

## b. Classes

L'architecture orientée objet de notre application Othello repose sur un ensemble précis de classes, chacune étant assignée à un rôle spécifique dans le fonctionnement global du jeu. Toutes ces classes sont répertoriées de manière détaillée dans l'annexe. Elles sont organisées en groupes en fonction de leurs rôles fonctionnels :

### 1. Composants Principaux

- **Bouton** : Gère les interactions avec les boutons de l'interface utilisateur.
- **Commande** : Représente les commandes possibles dans le jeu.
- **Controleur** : Agit comme le contrôleur principal, coordonnant les interactions entre les différents composants.
- **Couleur** : Enumération représentant les couleurs des disques dans le jeu.
- **Disque** : Représente un disque avec sa couleur associée.
- **Jeu** : Encapsule la logique du jeu, y compris les règles et les états actuels.
- **Joueur** : Représente un joueur avec ses caractéristiques (IA ou humain).
- **Manager** : Gère la coordination entre les différents composants et la logique du jeu.
- **Plateau** : Représente le plateau de jeu avec toutes ses cases et les disques.
- **Vue** : Gère l'interface utilisateur et affiche le plateau de jeu.

### 2. Gestionnaires

- **GestionnaireAbandon** : Gère l'action d'abandon d'une partie.
- **GestionnaireAnnuler** : Gère l'action d'annulation d'un coup.
- **GestionnaireDisque** : Gère les actions liées à la pose de disques sur le plateau.
- **GestionnaireNouvellePartie** : Gère le lancement d'une nouvelle partie.
- **GestionnaireRefaire** : Gère l'action de refaire un coup annulé.

### 3. Stratégies

- **AlphaBeta** : Implémente l'algorithme Alpha-Beta pour l'optimisation de la recherche.
- **ComparateurScoreBlanc** : Compare les scores pour les disques blancs.
- **Difficulte** : Enumération représentant les niveaux de difficulté pour les joueurs IA.
- **Minimax** : Implémente l'algorithme Minimax pour la prise de décision.
- **PlateauComparateurBlanc** : Compare les plateaux de jeu pour les disques blancs.
- **Strategie** : Interface définissant le contrat pour les différentes stratégies.
- **StrategieAleatoire** : Implémente une stratégie aléatoire pour les mouvements.
- **Utilite** : Classe utilitaire fournissant des méthodes d'évaluation et de calcul de l'utilité.

## c. Algorithmes développés

Notre application Othello met en œuvre plusieurs algorithmes essentiels pour permettre une expérience de jeu robuste et intelligente. Les principaux algorithmes développés sont les suivants :

### 1. Algorithme Minimax

L'algorithme Minimax constitue le cœur de la prise de décision de l'IA dans notre jeu Othello. Il explore l'arbre des possibilités en évaluant chaque coup potentiel et en anticipant les réponses de l'adversaire. La profondeur de recherche est paramétrable pour ajuster la complexité de l'algorithme en fonction des performances souhaitées.

### 2. Algorithme Alpha-Beta

Pour améliorer l'efficacité de la recherche dans l'arbre des possibilités, nous avons implémenté l'algorithme Alpha-Beta. Il permet une élagage plus rapide des branches inutiles, réduisant ainsi le nombre total de nœuds évalués sans compromettre la qualité de la décision.

### 3. Stratégies de Recherche

Notre application propose différentes stratégies de recherche, influant sur la fonction d'évaluation utilisée par l'IA. Ces stratégies comprennent :

- **Positionnel** : Prend en compte les poids statiques du tableau, évaluant la différence entre les poids associés aux deux joueurs.
- **Absolu** : Tient compte de la différence du nombre total de pions entre les deux joueurs.
- **Mobilité** : Vise à maximiser le nombre de coups possibles tout en minimisant les options de l'adversaire, avec une attention particulière pour les coins.
- **Mixte** : Varie la stratégie en fonction des phases du jeu (début, milieu, fin), utilisant des approches positionnelles, de mobilité et absolues selon le contexte.

### 4. Améliorations de l'Algorithme Minimax

Nous avons intégré des améliorations telles que l'algorithme Alpha-Beta pour optimiser les performances de l'algorithme Minimax, réduisant ainsi la complexité en fonction de la profondeur de recherche.

Chaque algorithme est soigneusement intégré dans la logique du jeu, contribuant à la prise de décision de l'IA et à l'expérience globale de jeu. La diversité des stratégies de recherche offre une adaptabilité et une variété dans les comportements de l'IA, rendant le jeu plus dynamique et intéressant pour les joueurs.

---
