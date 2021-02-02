package ch.bbw.view;

import ch.bbw.controller.Game;
import ch.bbw.lib.file.Export;
import ch.bbw.lib.file.Import;
import ch.bbw.model.Cell;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class Grid extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        Cell[][] cell = new Cell[20][20];
        Game game = new Game(cell,gridPane);

        randomGridInit(gridPane, cell);
        initializeUI(primaryStage, gridPane, game);
        game.showGrid();
    }

    public void run() {
        launch();
    }

    private void randomGridInit(GridPane gridPane, Cell[][] cell) {
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
    }

    private void initializeUI(Stage primaryStage, GridPane gridPane, Game game) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);

        FlowPane flowPane = new FlowPane();
        Button startButton = new Button("Start");
        startButton.setOnAction(game.start());

        Button endButton = new Button("Stop");
        endButton.setOnAction(game.stop());

        Button cellAgeButton = new Button("Show Cell Age");
        cellAgeButton.setOnAction(game.showCellAge());

        Button randomButton = new Button("Generate Random");
        randomButton.setOnAction(game.random());

        Button importButton = new Button("Import");
        importButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if(file != null){
                game.importFile(Import.importIsAliveStates(file.getPath()));
            }
        });

        Button exportButton = new Button("Export");
        exportButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            if(file != null){
                Export.exportIsAliveState(game.getGrid(), file.getAbsoluteFile());
            }
        });


        Slider slider = new Slider(0.1, 5, 1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> game.setGenerationSpeed(newValue));

        flowPane.setVgap(20);
        flowPane.setHgap(20);
        flowPane.getChildren().add(startButton);
        flowPane.getChildren().add(endButton);
        flowPane.getChildren().add(cellAgeButton);
        flowPane.getChildren().add(randomButton);
        flowPane.getChildren().add(importButton);
        flowPane.getChildren().add(exportButton);
        flowPane.getChildren().add(new Label("Speed (0.1 - 1)"));
        flowPane.getChildren().add(slider);

        borderPane.setBottom(flowPane);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Conway's Game Of Life");
        primaryStage.show();
    }
}
