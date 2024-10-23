package src;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static javafx.application.Application.launch;

public class Main extends Application {
    private Jeu jeu;
    private BorderPane root;
    public void start(Stage primaryStage){
        jeu = new Jeu(4, 4);  // Plateau par défaut de 4x4
        root = new BorderPane();

        // Création des vues
        VueMenu menu = new VueMenu(jeu, this);
        VuePlateau plateau = new VuePlateau(jeu);
        VueStats stats = new VueStats(jeu);

        root.setTop(menu);
        root.setCenter(plateau);
        root.setBottom(stats);

        Scene scene = new Scene(root, 450, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void newJeu(int taille){
        root.getChildren().clear();

        // Réinitialiser le modèle du jeu avec la nouvelle taille
        jeu = new Jeu(taille,taille);

        // Recréer les vues avec la nouvelle configuration
        VueMenu menu = new VueMenu(jeu, this);
        VuePlateau plateau = new VuePlateau(jeu);
        VueStats stats = new VueStats(jeu);

        // Ajouter les nouvelles vues dans le panneau principal
        root.setTop(menu);
        root.setCenter(plateau);
        root.setBottom(stats);
    }
    public static void main(String[] args){
        launch(args);
    }

}
