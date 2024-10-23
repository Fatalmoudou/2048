package src;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

//classe qui permet d'afficher lesstats i.e le nb de partis et le nb de parti ganée
public class VueStats extends Pane implements Observateur{
    private Label stats;
    private Jeu jeu;
    public VueStats (Jeu jeu){
        this.jeu=jeu;
        this.jeu.ajouterObservateur(this);
        this.stats=new Label();
        this.getChildren().add(stats);
        reagir(); // initialisation de l'affichage des stats
    }
    @Override
    public void reagir(){
        String st ="objectif à atteindre:";
        st+=jeu.getObjectif();
        st+="   Parties jouées/ gagnées : ";
        st += jeu.getNbGagnees() + "/"+ jeu.getNbJouees();
        this.stats.setText(st);

        this.stats.setText(st);
    }
}
