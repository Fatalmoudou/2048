package src;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class VuePlateau extends GridPane implements Observateur {
    private Jeu jeu;
    private Button[][] cases;

    public VuePlateau(Jeu jeu){
        this.jeu=jeu;
        this.jeu.ajouterObservateur(this);
        int rows =jeu.getPlateau().length;
        int cols = jeu.getPlateau()[0].length;
        cases=new Button[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cases[i][j] = new Button();
                cases[i][j].setPrefSize(100, 100);  // Taille des cases
                final int row = i;
                final int col = j;
                cases[i][j].setOnAction(event -> jeu.clicSurCase(row, col));//appel de clicSurCase quand on appuit sur le bouton
                this.add(cases[i][j], j, i);//ajouter une nouvelle case
            }
        }
    }
    //permet d'afficher les valeurs sur les tuiles
    @Override
    public void reagir() {
        int[][] plateau = jeu.getPlateau();
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                cases[i][j].setText(String.valueOf(plateau[i][j]));
            }
        }
    }
}
