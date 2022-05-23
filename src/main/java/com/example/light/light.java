package com.example.light;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class light extends Application {

    int compteur = 0;
    boolean mode = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws  Exception
    {
        //Titre de la fenêtre
        stage.setTitle("Éteins les lumières");

        //Construction du quadrillage carrés 5 par 5
        GridPane boite = new GridPane();
        boite.setPadding(new Insets(15,00,15,0));
        boite.setVgap(5);
        boite.setHgap(5);
        boite.setAlignment(Pos.CENTER);

        Rectangle[][] carre = new Rectangle[5][5];

        TextField dep = new TextField("Nb clics");
        dep.setEditable(false);

        //Construction du quadrillage carrés 5 par 5
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++) {
                Rectangle square = new Rectangle(100,100, Color.DARKGREEN);
                square.setArcHeight(20);
                square.setArcWidth(20);

                //Evenement d'un clic
                int finalX = x;
                int finalY = y;
                square.setOnMouseClicked(mouseEvent -> {
                    compteur++;
                    dep.setText(String.format("%d", compteur));
                    if(mode){
                        pressed(carre, finalX, finalY);
                    } else {
                        config(carre, finalX, finalY);
                    }
                });
                carre[x][y]=square;
                boite.add(carre[x][y],x,y);
            }
        }

        //Créations de VBox pour ranger les boutons
        HBox boite_boutons = new HBox(10);

        //Bouton jouer
        Button b_jouer = new Button();
        Image play = new Image("file:src/main/resources/com/example/light/play.png");
        ImageView playView = new ImageView(play);
        playView.setPreserveRatio(true);
        playView.setFitHeight(25);
        b_jouer.setGraphic(playView);
        b_jouer.setStyle("-fx-background-color:  ORANGE");

        //Event jouer
        b_jouer.setOnMouseClicked(mouseEvent -> {
            compteur = 0;
            mode = true;
        });

        //Bouton Configurer
        Button b_config = new Button();
        Image param = new Image("file:src/main/resources/com/example/light/param.png");
        ImageView paramView = new ImageView(param);
        paramView.setPreserveRatio(true);
        paramView.setFitHeight(25);
        b_config.setGraphic(paramView);
        b_config.setStyle("-fx-background-color:  ORANGE");

        //Event config
        b_config.setOnMouseClicked(MouseEvent -> {
            compteur = 0;
            mode = false;
        });

        //Bouton Aléatoire
        Button b_aleat = new Button();
        Image aleat = new Image("file:src/main/resources/com/example/light/aleat.png");
        ImageView aleatView = new ImageView(aleat);
        aleatView.setPreserveRatio(true);
        aleatView.setFitHeight(25);
        b_aleat.setGraphic(aleatView);
        b_aleat.setStyle("-fx-background-color:  ORANGE");

        //Event aléatoire
        Random rand = new Random();

        b_aleat.setOnMouseClicked(MouseEvent -> {
            compteur = 0;
            //Réinitialisation du pannel éteins
            for(int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    carre[x][y].setFill(Color.DARKGREEN);
                }
            }
            int nombreC = 0;
            while(nombreC < 8){
                int x = rand.nextInt(4);
                int y = rand.nextInt(4);
                if(carre[x][y].getFill().equals(Color.DARKGREEN)){
                    carre[x][y].setFill(Color.LIGHTGREEN);
                    nombreC++;
                }
            }
        });

        //Bouton reset
        Button b_reset = new Button();
        Image reset = new Image("file:src/main/resources/com/example/light/reset.png");
        ImageView resetView = new ImageView(reset);
        resetView.setPreserveRatio(true);
        resetView.setFitHeight(25);
        b_reset.setGraphic(resetView);
        b_reset.setStyle("-fx-background-color:  ORANGE");

        //Event Reset
        b_reset.setOnMouseClicked(MouseEvent -> {
            for(int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    carre[x][y].setFill(Color.DARKGREEN);
                }
            }
        });

        //Bouton Quitter
        Button b_quitter = new Button();
        Image quit = new Image("file:src/main/resources/com/example/light/quit.png");
        ImageView quitView = new ImageView(quit);
        quitView.setPreserveRatio(true);
        quitView.setFitHeight(25);
        b_quitter.setGraphic(quitView);
        b_quitter.setStyle("-fx-background-color:  ORANGE");

        //Event quitter
        b_quitter.setOnMouseClicked(MouseEvent -> {
            b_quitter.setStyle("-fx-background-color:  YELLOW");
            stage.close();
        });

        //Afficheur de clics
        dep.setMaxHeight(35);
        dep.setMaxWidth(100);
        dep.setAlignment(Pos.CENTER);

        //Cadre Principal du jeu
        VBox cadre = new VBox();

        //Bouton check
        Button b_check = new Button();
        Image check = new Image("file:src/main/resources/com/example/light/check.png");
        ImageView checkView = new ImageView(check);
        checkView.setPreserveRatio(true);
        checkView.setFitHeight(25);
        b_check.setGraphic(checkView);
        b_check.setStyle("-fx-background-color:  ORANGE");

        //Event check
        b_check.setOnMouseClicked(MouseEvent -> {
            //JEU
            if(isWin(carre)){
                Text fin = new Text("TU AS GAGNÉ FELICITATION :)");
                fin.setTranslateX(300);
                fin.setTranslateY(300);
                FadeTransition trans = new FadeTransition(Duration.seconds(5), fin);
                trans.setFromValue(1.0);
                trans.setToValue(0.0);
                trans.play();
                cadre.getChildren().addAll(fin);
            }
        });

        //Ajout des boutons au Vbox
        boite_boutons.getChildren().addAll(b_jouer, dep, b_config, b_aleat, b_reset, b_quitter,b_check);
        boite_boutons.setAlignment(Pos.CENTER);

        //CADRE
        cadre.setPadding(new Insets(10));
        cadre.getChildren().addAll(boite, boite_boutons);

        //Affichage
        stage.setScene(new Scene(cadre, 550, 650));
        stage.show();
    }

    //Méthode gagner
    public boolean isWin(Rectangle[][] carre){
        if(mode){
            for(int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    if (carre[x][y].getFill().equals(Color.LIGHTGREEN))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    //Méthode inverseCouleur
    public void reverseColor(Rectangle carre){
        if(carre.getFill().equals(Color.DARKGREEN)){
            carre.setFill(Color.LIGHTGREEN);
        } else {
            carre.setFill(Color.DARKGREEN);
        }
    }

    //Méthode appuye croix
    public void pressed(Rectangle[][] carre, int x, int y){
        if((x > 0 && x < 4) && (y > 0 && y < 4)){
            reverseColor(carre[x][y]);
            reverseColor(carre[x + 1][y]);
            reverseColor(carre[x - 1][y]);
            reverseColor(carre[x][y + 1]);
            reverseColor(carre[x][y - 1]);
        } else {
            if((y <= 0) && (x > 0 && x < 4)){
                reverseColor(carre[x][y]);
                reverseColor(carre[x + 1][y]);
                reverseColor(carre[x - 1][y]);
                reverseColor(carre[x][y + 1]);
            }

            if((y >= 4) && (x > 0 && x < 4)){
                reverseColor(carre[x][y]);
                reverseColor(carre[x + 1][y]);
                reverseColor(carre[x - 1][y]);
                reverseColor(carre[x][y - 1]);
            }

            if((x <= 0) && (y > 0 && y < 4)){
                reverseColor(carre[x][y]);
                reverseColor(carre[x + 1][y]);
                reverseColor(carre[x][y + 1]);
                reverseColor(carre[x][y - 1]);
            }

            if((x >= 4) && (y > 0 && y < 4)){
                reverseColor(carre[x][y]);
                reverseColor(carre[x - 1][y]);
                reverseColor(carre[x][y + 1]);
                reverseColor(carre[x][y - 1]);
            }

            if(x >= 4 && y <=0){
                reverseColor(carre[x][y]);
                reverseColor(carre[x - 1][y]);
                reverseColor(carre[x][y + 1]);
            }

            if(y >= 4 && x <=0){
                reverseColor(carre[x][y]);
                reverseColor(carre[x + 1][y]);
                reverseColor(carre[x][y - 1]);
            }

            if(x <= 0 && y <=0){
                reverseColor(carre[x][y]);
                reverseColor(carre[x + 1][y]);
                reverseColor(carre[x][y + 1]);
            }

            if(x >= 4 && y >= 4){
                reverseColor(carre[x][y]);
                reverseColor(carre[x][y - 1]);
                reverseColor(carre[x - 1][y]);
            }
        }
    }

    //Méthode pour le bouton config
    public void config(Rectangle[][] carre, int x, int y){
        reverseColor(carre[x][y]);
    }
}


