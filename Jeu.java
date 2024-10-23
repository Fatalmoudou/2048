package src;


import javafx.application.Application;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;

public class Jeu {
    private ArrayList<Observateur> obs;
    private int [][] plateau;
    private int nbGagnees;
    private int nbJouees;
    private int objectif; // Objectif du jeu
    // constructeur avec un objectif par défaut
    public Jeu(int row, int col){
        this(row,col,2048);
    }
    public Jeu(int row, int col,int objectif){
        this.obs= new ArrayList<>();
       this.plateau= new int [row][col];
       this.nbGagnees=0;
       this.nbJouees=0;
       this.objectif=objectif;
       nouveau(5);//création du jeu
    }

    public void ajouterObservateur(Observateur o){
        this.obs.add(o);
    }

    public void nouveau(int nbCasesInitialisees){
       //initialiser toutes cases du plateau à 0
        for(int i=0; i<plateau.length;i++){
            for(int j=0; j<plateau[i].length;j++){
                plateau[i][j]=0;
            }
        }
        //à chaque appel de nouveau() on incrémente le nb de partie joué
        nbJouees++;
        //donner une valeur != 0 à nbinitialisées tuiles
        for (int i = 0; i < nbCasesInitialisees; i++) {
            ajouterNouvelleTuile();
        }// On ajoute deux tuiles au début
        this.notifierObservateurs();
    }
    public void notifierObservateurs(){
        for(Observateur o : this.obs) o.reagir();
    }
    public int[][] getPlateau() {
        return plateau;
    }

    public int getNbGagnees() {
        return nbGagnees;
    }

    public int getNbJouees() {
        return nbJouees;
    }
    public int getObjectif() {
        return objectif;
    }

    // Setter pour l'objectif
    public void setObjectif(int objectif) {
        this.objectif = objectif;
    }
    public void incrementerNbGagnees() {
        nbGagnees++;
        notifierObservateurs();
    }
    // Clic sur une case pour essayer de fusionner avec ses voisines
    public void clicSurCase(int row, int col) {
        if (plateau[row][col] == 0) return;

        boolean fusion = false;

        // Fusion avec la case en haut
        if (row > 0 && plateau[row][col] != 0 && plateau[row][col] == plateau[row - 1][col]) {
            plateau[row][col] *= 2;
            plateau[row - 1][col] = 0;
            fusion = true;
        }

        // Fusion avec la case en bas
        if (row < plateau.length - 1 && plateau[row][col] != 0 && plateau[row][col] == plateau[row + 1][col]) {
            plateau[row][col] *= 2;
            plateau[row + 1][col] = 0;
            fusion = true;
        }

        // Fusion avec la case à gauche
        if (col > 0 && plateau[row][col] != 0 && plateau[row][col] == plateau[row][col - 1]) {
            plateau[row][col] *= 2;
            plateau[row][col - 1] = 0;
            fusion = true;
        }

        // Fusion avec la case à droite
        if (col < plateau[0].length - 1 && plateau[row][col] != 0 && plateau[row][col] == plateau[row][col + 1]) {
            plateau[row][col] *= 2;
            plateau[row][col + 1] = 0;
            fusion = true;
        }

        if (fusion) {
            if (plateau[row][col] == objectif) {
                System.out.println("Vous avez gagné !");
                this.nbGagnees++;
            }
            //remplacer la case qui a fusionnée par une nouvelle tuile
            ajouterNouvelleTuile();
            notifierObservateurs();
        }
        // verifier si on gagne ou pas
        verifierFinDePartie();
    }
    private void ajouterNouvelleTuile() {
        ArrayList<int[]> casesVides = new ArrayList<>();
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 0) {
                    // rajouter un int dans cette case
                    casesVides.add(new int[]{i, j});
                }
            }
        }
            //on choisit la position de la tuile au hasard
        if (!casesVides.isEmpty()) {
            Random rand = new Random();
            int[] position = casesVides.get(rand.nextInt(casesVides.size()));
            plateau[position[0]][position[1]] = rand.nextDouble() < 0.9 ? 2 : 4;
        }
    }

    private void verifierFinDePartie() {
        if (!peutBouger()) {
            System.out.println("Jeu terminé. Vous avez perdu !");
            this.nouveau(5);
        }
    }
    private boolean peutBouger() {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {

                // Vérifier les fusions possibles avec les cases voisines (voisine de haut)
                if (i > 0 && plateau[i][j] != 0 && plateau[i][j] == plateau[i - 1][j]) {
                    return true; // Fusion possible avec la case au-dessus
                }
                //voisine de bas
                if (i < plateau.length - 1 && plateau[i][j] != 0 && plateau[i][j] == plateau[i + 1][j]) {
                    return true; // Fusion possible avec la case en-dessous
                }
                //voisine de gauche
                if (j > 0 && plateau[i][j] != 0 && plateau[i][j] == plateau[i][j - 1]) {
                    return true; // Fusion possible avec la case à gauche
                }
                //voisine de droite
                if (j < plateau[i].length - 1 && plateau[i][j] != 0 && plateau[i][j] == plateau[i][j + 1]) {
                    return true; // Fusion possible avec la case à droite
                }
            }
        }
        // Si aucune case vide ou fusion possible n'a été trouvée, le joueur perd
        return false;
    }
}
