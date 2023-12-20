# Projet Design Pattern - Pacman
Thomas Landais - Projet universitaire dans le cadre du M1 informatique à l'université d'Angers

![Description de l'image](/pacman.png)


## Description

Ce projet implémente une version interactive du jeu classique Pacman avec une interface graphique. Le jeu suit les règles traditionnelles de Pacman, avec des agents Pacman et fantômes évoluant dans un labyrinthe.
Objectifs

Le projet vise à appliquer les concepts de design patterns pour créer un jeu Pacman modulable et extensible. L'accent est mis sur la mise en place d'une architecture robuste et l'utilisation de stratégies pour les agents.
Architecture et Design Patterns

## MVC (Modèle-Vue-Contrôleur)

Modèle : Gère l'état du jeu, les agents, et la logique.
Vue : Affiche l'état du jeu à l'utilisateur.
Contrôleur : Gère les interactions entre la vue et le modèle.

## Observateur

Utilisé pour mettre à jour la vue chaque fois que l'état du modèle change.

## Stratégie

Permet d'implémenter différentes stratégies pour les agents Pacman et fantômes.

## Factory

Utilisé pour créer des instances d'agents (Pacman et fantômes).

## Singleton

Appliqué pour la gestion de l'instance unique du jeu.

## Etat 

Gestion des états de la vue commande grâce au design pattern état.

## Caractéristiques Principales

Jeu Pacman avec interface graphique.
Agents Pacman et fantômes avec des stratégies définissables.
Algorithme A* pour les déplacements des fantômes.

## Algorithme A* pour les Fantômes

L'algorithme A* est utilisé pour calculer les déplacements des fantômes, les dirigeant de manière intelligente vers le Pacman. Cette approche améliore le défi et la jouabilité en rendant les fantômes plus stratégiques.

## Amélioration du jeu

Gestion du nombre de vies du joueur.

## Amélioration de l'interface graphique

Maintenant, pacman ouvre et ferme la bouche, et on a un affichage du nombre de vies restantes sous forme de pacman en haut à gauche de la fenêtre

## Comment Jouer

Lancez le jeu en exécutant le fichier Main.java.
Utilisez les commandes à l'écran pour démarrer, mettre en pause, et redémarrer le jeu.
Choisissez un labyrinthe et une stratégie pour les agents Pacman.
Pour jouer avec le Pacman par vous-même, séléctionner player au démarrage. Déplacement avec z-q-s-d

## Compilation et Exécution

    Requis : Java JDK 11 ou supérieur.
    Compilation : javac Main.java
    Exécution : java Main
