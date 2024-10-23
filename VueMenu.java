package src;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.Optional;

public class VueMenu extends MenuBar implements Observateur{
    private Jeu jeu;
    private Main mainApp;
    public VueMenu(Jeu jeu, Main mainApp) {
        this.jeu=jeu;
        this.mainApp=mainApp;
        Menu menu = new Menu("Menu");
        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        nouveau.setOnAction(event->jeu.nouveau(5));
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        quitter.setOnAction(event-> Platform.exit());
        MenuItem taille = new MenuItem("Taille");
        taille.setOnAction(e->changerTaillePlateau());
        MenuItem objectif= new MenuItem("Objectif");
        objectif.setOnAction(e->changerObjectif());
        menu.getItems().addAll(nouveau, quitter,taille,objectif);
        this.getMenus().add(menu);
    }
    //méthode appelée quand on clique sur Taille dans le menu
    private void changerTaillePlateau() {
        // affiche la boite de dialogue avec la taille courante
        TextInputDialog dialog = new TextInputDialog("4");
        //titre de la boite
        dialog.setTitle("Taille du plateau");
        //libellé
        dialog.setHeaderText("Définir la taille du plateau");
        dialog.setContentText("Entrez la taille souhaitée :");

        // Récupération de l'entrée de l'utilisateur
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tailleStr -> {
            try {
                //cast de string à int
                int taille = Integer.parseInt(tailleStr);
                if (taille >= 2 && taille <= 10) { // Limiter la taille du plateau (exemple: entre 2 et 10)
                    //appeler la fonction newJeu de la calsse main pour réinitialiser le jeu
                    mainApp.newJeu(taille);
                } else {
                    System.out.println("Taille invalide, entrez une valeur entre 2 et 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        });
    }
    //méthode appelée quand on clique sur Objectif du Menu jeu
    private void changerObjectif() {
        //on a d'abord la valeur du objectif courant
        TextInputDialog dialog = new TextInputDialog(String.valueOf(jeu.getObjectif()));
        //titre de la boite de dialogue
        dialog.setTitle("Définir l'objectif");
        //libellé
        dialog.setHeaderText("Définir l'objectif à atteindre");
        dialog.setContentText("Entrez l'objectif:");

        // Récupération de l'objectif de l'utilisateur
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(objectifStr -> {
            try {
                int objectif = Integer.parseInt(objectifStr);
                if (objectif > 0) { // Valider un objectif positif
                    jeu.setObjectif(objectif);  // Mettre à jour l'objectif dans le modèle
                    //appeler la méthode nouveau de la classe jeu
                    this.jeu.nouveau(5);
                } else {
                    System.out.println("Objectif invalide, entrez un nombre positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        });
    }
    @Override
    public void reagir(){

    }
}
