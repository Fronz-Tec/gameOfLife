package ch.bbw;

import ch.bbw.controller.Game;
import ch.bbw.lib.file.Export;
import ch.bbw.lib.file.Import;
import ch.bbw.model.Cell;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
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
            for (int y = 0; y < cell[x].length; y++) {
                cell[x][y] = new Cell();
                cell[x][y].setNewState(new Random().nextBoolean());
                cell[x][y].updateState();
                Rectangle rectangle = new Rectangle(20, 20, 20, 20);
                rectangle.setStroke(Color.GRAY);
                rectangle.setFill(Color.WHITE);
                gridPane.add(rectangle, x, y, 1, 1);
            }
        }

        Game game = new Game(cell,gridPane);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);

        //TODO 2 more buttons import Export
        //Export.exportIsAliveState(cell);
        //cell = Import.importIsAliveStates("");

        //TODO 1 Button Random
        //Action
//        for (int x = 0; x < cell.length; x++) {
//            for (int y = 0; y < cell[x].length; y++) {
//                cell[x][y].setNewState(new Random().nextBoolean());
//                cell[x][y].updateState();
//            }
//        }

        FlowPane flowPane = new FlowPane();
        Button startButton = new Button("Start");
        startButton.setOnAction(game.start());

        Button endButton = new Button("Stop");
        endButton.setOnAction(game.stop());

        Button cellAgeButton = new Button("Show Cell Age");
        cellAgeButton.setOnAction(game.showCellAge());

        Slider slider = new Slider(0.1, 5, 1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                game.setGenerationSpeed(newValue);
            }
        });

        flowPane.setVgap(20);
        flowPane.setHgap(20);
        flowPane.getChildren().add(startButton);
        flowPane.getChildren().add(endButton);
        flowPane.getChildren().add(cellAgeButton);
        flowPane.getChildren().add(new Label("Speed (0.1 - 1)"));
        flowPane.getChildren().add(slider);

        borderPane.setBottom(flowPane);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.show();

        game.showGrid();
    }
}