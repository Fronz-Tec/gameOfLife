package ch.bbw;

import ch.bbw.controller.Game;
import ch.bbw.model.Cell;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {


        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();

        Cell[][] cell = new Cell[20][20];

        for (int x = 0; x < cell.length; x++) {
            for(int y = 0; y < cell[x].length; y++){
                cell[x][y] = new Cell();
                cell[x][y].setNewState(new Random().nextBoolean());
                cell[x][y].updateState();
                Rectangle rectangle = new Rectangle(20,20,20,20);
                rectangle.setStroke(Color.GRAY);
                rectangle.setFill(Color.WHITE);
                gridPane.add(rectangle,x,y,1,1);
            }
        }

        Game game = new Game(cell);

        game.showGrid(gridPane);
        game.calculateGeneration();
        game.showGrid(gridPane);


        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.show();
    }
}
